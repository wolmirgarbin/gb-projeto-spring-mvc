package br.com.viasoft.portaldef.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.viasoft.enumeration.FormatDate;
import br.com.viasoft.enumeration.TiposArquivos;
import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Eventos;
import br.com.viasoft.portaldef.entities.OutrosEnvolvidos;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.PessoaEmpresa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.entities.UsuarioEmpresa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TipoDocumento;
import br.com.viasoft.portaldef.repositories.DocumentosRepository;
import br.com.viasoft.portaldef.repositories.EventosRepository;
import br.com.viasoft.portaldef.repositories.PessoaRepository;
import br.com.viasoft.portaldef.repositories.UsuarioEmpresaRepository;
import br.com.viasoft.portaldef.repositories.UsuarioRepository;
import br.com.viasoft.portaldef.repositories.custom.DfeRepositoryCustom;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.OutrosEnvolvidosService;
import br.com.viasoft.portaldef.service.PessoaEmpresaService;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;
import br.com.viasoft.portaldef.service.UtcService;
import br.com.viasoft.portaldef.web.to.ResultUploadTO;
import br.com.viasoft.to.FilePatternTO;
import br.com.viasoft.util.CteUtil;
import br.com.viasoft.util.DateUtil;
import br.com.viasoft.util.FileUtil;
import br.com.viasoft.util.NfeUtil;
import br.com.viasoft.util.NumberUtil;
import br.com.viasoft.util.TipoDaNotaUtil;
import br.com.viasoft.xmls.ArquivoCteTO;
import br.com.viasoft.xmls.ArquivoEventosTO;
import br.com.viasoft.xmls.ArquivoNfeTO;
import br.com.viasoft.xmls.PessoaNotaTO;

@Service
public class ProcessarArquivosServiceImpl implements ProcessarArquivosService {

	private static final long serialVersionUID = -2660405333155251991L;

	@Autowired
	private DocumentosRepository documentosRepository;

	@Autowired
	private DfeRepositoryCustom dfeRepositoryCustom;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EventosRepository eventosRepository;

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private PessoaEmpresaService pessoaEmpresaService;

	@Autowired
	private UsuarioEmpresaRepository usuarioEmpresaRepository;

	@Autowired
	private UtcService utcService;
	
	@Autowired
	private OutrosEnvolvidosService outrosEnvolvidosService;



	@Override
	public List<File> carregaListaArquivos(String pathName) throws FileNotFoundException {
		return FileUtil.getFiles(pathName);
	}


	@Override
	public boolean processArquivos(List<FilePatternTO> arquivosEmDisco, List<Empresa> empresa) {
		if( arquivosEmDisco == null || arquivosEmDisco.size() == 0 )
			return false;

		boolean toReturn = true;

		final List<FilePatternTO> listNfes = new ArrayList<FilePatternTO>();
		final List<FilePatternTO> listEventosNfe = new ArrayList<FilePatternTO>();
		final List<FilePatternTO> listCte = new ArrayList<FilePatternTO>();
		final List<FilePatternTO> listEventosCte = new ArrayList<FilePatternTO>();
		final List<FilePatternTO> listPDFs = new ArrayList<FilePatternTO>();

		for (final FilePatternTO file : arquivosEmDisco) {
			final TiposArquivos tipo = obtemTipo(file);

			if( tipo.equals(TiposArquivos.PDF) ) {
				listPDFs.add(file);
			} else if( tipo.equals(TiposArquivos.NFE) ){
				listNfes.add(file);
			} else if( tipo.equals(TiposArquivos.EVENTO_NFE) ) {
				listEventosNfe.add(file);
			} else if( tipo.equals(TiposArquivos.CTE) ) {
				listCte.add(file);
			} else if( tipo.equals(TiposArquivos.EVENTO_CTE) ) {
				listEventosCte.add(file);
			}
		}

		arquivosEmDisco = null;

		// processa os arquivos em ordem para evitar problemas ao gravar e poder processar todos contidos na pasta
		for (final FilePatternTO file : listNfes) {
			processarArquivosNfe(file, empresa);
		}
		for (final FilePatternTO file : listCte) {
			processarArquivosCte(file, empresa);
		}
		for (final FilePatternTO file : listEventosNfe) {
			toReturn = processarArquivosEventosNfe(file);
		}
		for (final FilePatternTO file : listEventosCte) {
			toReturn = processarArquivosEventosCte(file);
		}

		// sempre por ultimo grava os arquivos PDFs
		for (final FilePatternTO file : listPDFs) {
			processarArquivosPDF(file);
		}

		return toReturn;
	}



	@Override
	@Transactional
	public ResultUploadTO processArquivosUpload(List<MultipartFile> arquivosEmDisco, List<Empresa> empresa) {
		if( arquivosEmDisco == null || arquivosEmDisco.size() == 0 )
			return new ResultUploadTO("405", "Arquivos não enviados para o servidor");

		final List<FilePatternTO> listProcess = new ArrayList<FilePatternTO>();
		String nome = null;
		for( final MultipartFile m : arquivosEmDisco ) {
			try {
				nome = m.getOriginalFilename();
				listProcess.add( new FilePatternTO(nome, nome.substring(nome.lastIndexOf(".") + 1, nome.length()), m.getBytes()) );
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		if( processArquivos(listProcess, empresa) )
			return new ResultUploadTO("200", "Arquivos enviados com sucesso");
		else
			return new ResultUploadTO("202", "Enviar mais tarde");
	}


	@Override
	@Deprecated
	@Transactional(propagation=Propagation.REQUIRED)
	public void processArquivosDisk(List<File> arquivosEmDisco) {
		if( arquivosEmDisco == null || arquivosEmDisco.size() == 0 )
			return;

		final List<FilePatternTO> listProcess = new ArrayList<FilePatternTO>();
		for( final File file : arquivosEmDisco ) {
			try {
				listProcess.add( new FilePatternTO(file.getName(), FileUtil.getExtencao(file), FileUtil.read(file)) );
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		processArquivos(listProcess, null);

		for(final File file : arquivosEmDisco) {
			if( file.isFile() ) {
				file.delete();
			}
		}
	}


	private void processarArquivosNfe(FilePatternTO file, List<Empresa> empresa) {
		final ArquivoNfeTO to = NfeUtil.converteFileToArquivo(file);

		// dependendo do ambiente verifica o tipo de ambiente da nota
		if( ConfigureApp.getInstance().getNotasApenasDeProducao() ) {
			if( ! to.getNfeTpAmb().equals("1") ) {
				return;
			}
		}

		// verifica se o xml enviado pertence para alguma empresa
		final Empresa empresaRelacionadaParaUTC = empresaService.possuiVinculo(to.getEmit().getIdentificacao(), to.getDest().getIdentificacao(), to.getEnvolvidos());

		if( empresaRelacionadaParaUTC != null ) {

			final Pessoa emitente = savarPessoaCompleto(to.getEmit(), empresa);

			final Pessoa destinatario = savarPessoaCompleto(to.getDest(), empresa);

			// gravar os documentos fiscais
			final Documentos docNfe = salveDocumento(file);

			// gravar dados de dfe
			Dfe dfe = dfeRepositoryCustom.findOne(to.getNfeChave());

			if( dfe == null ) {
				dfe = new Dfe();

				dfe.setChave( to.getNfeChave() );
				dfe.setEmitente(emitente);
				dfe.setDestinatario(destinatario);
				//dfe.setDocDanf(docDanf); - este campo é adicionado ao carregar a danf
				dfe.setDocNfe(docNfe);
				dfe.setDtEmissao(DateUtil.toDate(to.getNfeDtEmissao(), FormatDate.YEAR_MONTH_DAY));
				dfe.setDtSaida(DateUtil.toDate(to.getNfeDtSaida(), FormatDate.YEAR_MONTH_DAY));
				dfe.setNatop(to.getNfeNatOp());
				dfe.setNumero(Integer.valueOf(to.getNfeNumeroNnf()));
				dfe.setRetDtAutoriza(DateUtil.toDate(to.getRetDtAutoriza(), FormatDate.YEAR_MONTH_DAY));
				dfe.setRetMotivo(to.getRetMotivo());
				dfe.setRetProtocolo(to.getRetProtocolo());
				dfe.setRetStat(Integer.valueOf(to.getRetStat()));
				dfe.setSerie(to.getNfeSerie());
				dfe.setTipoAmb(Integer.valueOf(to.getNfeTpAmb()));
				dfe.setTipoEmissao(Integer.valueOf(to.getNfeTipoNf()));
				dfe.setVnf(Double.valueOf(to.getNfeVnf()));
				dfe.setNfeOuCte( TipoDocumento.NFE );

				dfe.setDtHrCarregado(utcService.dateForUTCEmpresa( empresaRelacionadaParaUTC ));

				dfe = dfeRepositoryCustom.merge(dfe);
				
				savarPessoaFazerVinculoDfe(to.getEnvolvidos(), dfe, empresa, emitente, destinatario);
			}
		}
	}


	private void processarArquivosCte(FilePatternTO file, List<Empresa> empresa) {
		final ArquivoCteTO to = CteUtil.converteFileToArquivo(file);

		// dependendo do ambiente verifica o tipo de ambiente da nota
		if( ConfigureApp.getInstance().getNotasApenasDeProducao() ) {
			if( ! to.getCteTpAmb().equals("1") ) {
				return;
			}
		}

		// verifica se o xml enviado pertence para alguma empresa
		final Empresa empresaRealcionadaParaUTC = empresaService.possuiVinculo(to.getEmit().getIdentificacao(), to.getDest().getIdentificacao(), to.getEnvolvidos());
		if( empresaRealcionadaParaUTC != null ) {

			final Pessoa emitente = savarPessoaCompleto(to.getEmit(), empresa);

			final Pessoa destinatario = savarPessoaCompleto(to.getDest(), empresa);
			
			// gravar os documentos fiscais
			final Documentos docNfe = salveDocumento(file);

			// gravar dados de dfe
			Dfe dfe = dfeRepositoryCustom.findOne(to.getCteChave());

			if( dfe == null ) {
				dfe = new Dfe();

				dfe.setChave( to.getCteChave() );
				dfe.setEmitente(emitente);
				dfe.setDestinatario(destinatario);
				//dfe.setDocDanf(docDanf); - este campo é adicionado ao carregar a danf
				dfe.setDocNfe(docNfe);
				dfe.setDtEmissao(DateUtil.toDate(to.getCteDtHrEmissao(), FormatDate.DATE_SEMI_COMPLETE));
				dfe.setDtSaida(DateUtil.toDate(to.getCteDtSaida(), FormatDate.YEAR_MONTH_DAY));
				dfe.setNatop(to.getCteNatOp());
				dfe.setNumero(Integer.valueOf(to.getCteNumeroNnf()));
				dfe.setRetDtAutoriza(DateUtil.toDate(to.getRetDtAutoriza(), FormatDate.YEAR_MONTH_DAY));
				dfe.setRetMotivo(to.getRetMotivo());
				dfe.setRetProtocolo(to.getRetProtocolo());
				dfe.setRetStat(Integer.valueOf(to.getRetStat()));
				dfe.setSerie(to.getCteSerie());
				dfe.setTipoAmb(Integer.valueOf(to.getCteTpAmb()));
				dfe.setTipoEmissao(Integer.valueOf(to.getCteTipoCte()));
				dfe.setVnf(Double.valueOf(to.getCteVnf()));
				dfe.setNfeOuCte( TipoDocumento.CTE );

				dfe.setDtHrCarregado( utcService.dateForUTCEmpresa(empresaRealcionadaParaUTC));

				dfe = dfeRepositoryCustom.merge(dfe);
				
				savarPessoaFazerVinculoDfe(to.getEnvolvidos(), dfe, empresa, emitente, destinatario);
			}
		}
	}


	/**
	 * O arquivo de nfe referente pode não ter sido processado, neste caso o arquivo de danf será carregado na segunda vez que for verificado o diretorio
	 *
	 * @param file
	 */
	private void processarArquivosPDF(FilePatternTO file) {
		if( file == null )
			return;

		// pega a cheve do nome do arquivo
		final String name = NumberUtil.apenasNumeros( file.getNome() );

		// verifica no banco se existe a nfe correspondente
		final Dfe dfe = dfeRepositoryCustom.findOne( "NFe"+ name, "CTe"+ name );

		if( dfe != null ) {
			// se existe o arquivo de nfe salva o documento
			final Documentos danf = salveDocumento(file);

			// vincula o documento a nfe
			dfeRepositoryCustom.updateDanf(danf, dfe);
		}
	}


	/**
	 * Processa as cartas de correções e cancelamentos de NFe
	 *
	 * @param file
	 */
	private boolean processarArquivosEventosNfe(FilePatternTO file) {

		// converter - para pegar os dados e a chave
		final ArquivoEventosTO to = NfeUtil.converteFileToEvento(file);

		final Documentos doc = documentosRepository.findByNome(file.getNome());
		if( doc == null ) {

			// Busca a dfe pela chave
			final Dfe dfe = dfeRepositoryCustom.findOne( "NFe"+ to.getEveChave() );

			if( dfe != null ) {
				// salva o documento
				final Documentos cartaCorrecaoDoc = salveDocumento(file);

				// adapter para obj persistente
				//Eventos evento = eventosRepository.findOneBySequencialAndChave(to.getEveSequencial(), to.getEveChave());
				//if( evento == null ) {
					final Eventos evento = new Eventos();
					evento.setChave( to.getEveChave() );
					evento.setDescricao( to.getEveDescricao() );
					evento.setDfe(dfe);
					evento.setDocumento(cartaCorrecaoDoc);
					evento.setDtEvento( DateUtil.toDate(to.getEveDataHora(), FormatDate.DATE_COMPLETE) );
					evento.setSequencial( to.getEveSequencial() );
					evento.setTipo( to.getEveTipo() );
					evento.setDtHrCarregado(new Date());

					evento.setRetDtAutoriza( DateUtil.toDate(to.getRetDataHoraAutoriza(), FormatDate.DATE_COMPLETE) );
					evento.setRetMotivo( to.getRetMotivo() );
					evento.setRetProtocolo( to.getRetProtocolo() );
					evento.setRetStat( Integer.valueOf( to.getRetStat() ) );

					// sava o evento
					eventosRepository.save(evento);
				//}
				return true;
			}
		}
		return false;
	}



	private boolean processarArquivosEventosCte(FilePatternTO file) {

		// converter - para pegar os dados e a chave
		final ArquivoEventosTO to = CteUtil.converteFileToEvento(file);

		final Documentos doc = documentosRepository.findByNome(file.getNome());
		if( doc == null ) {

			// Busca a dfe pela chave
			final Dfe dfe = dfeRepositoryCustom.findOne( "CTe"+ to.getEveChave() );

			if( dfe != null ) {
				// salva o documento
				final Documentos cartaCorrecaoDoc = salveDocumento(file);

				// adapter para obj persistente
				//Eventos evento = eventosRepository.findOneBySequencialAndChave(to.getEveSequencial(), to.getEveChave());
				//if( evento == null ) {
					final Eventos evento = new Eventos();
					evento.setChave( to.getEveChave() );
					evento.setDescricao( to.getEveDescricao() );
					evento.setDfe(dfe);
					evento.setDocumento(cartaCorrecaoDoc);
					//evento.setDtEvento(  );
					//evento.setSequencial( to.getEveSequencial() );
					evento.setTipo( to.getEveTipo() );
					evento.setDtHrCarregado(new Date());

					evento.setRetDtAutoriza( DateUtil.toDate(to.getRetDataHoraAutoriza(), FormatDate.DATE_SEMI_COMPLETE) );
					evento.setRetMotivo( to.getRetMotivo() );
					evento.setRetProtocolo( to.getRetProtocolo() );
					evento.setRetStat( Integer.valueOf( to.getRetStat() ) );

					// sava o evento
					eventosRepository.save(evento);
					return true;
				//}
			}
		}
		return false;
	}


	/**
	 * Salvar o registro de documento apartir do file nfe/danf
	 *
	 * @param file
	 * @return
	 */
	private Documentos salveDocumento(FilePatternTO file) {

		Documentos doc = documentosRepository.findByNome(file.getNome());

		// se encontrar no banco o arquivo gravado retorna este
		if( doc != null )
			return doc;

		// sempre que não estiver cadastrado no bd o nome do arqruivo grava e retorna o arquivo gravado
		doc = new Documentos();
		doc.setNome( file.getNome() );
		doc.setExtencao( file.getTipo() );
		doc.setArquivo( file.getArquivo() );

		return documentosRepository.save(doc);
	}


	@Override
	public TiposArquivos obtemTipo(FilePatternTO to) {
		if( to == null )
			return TiposArquivos.OUTROS;

		if( to.getNome().endsWith(".pdf") ) {
			// sempre que tem extenção de Pdf sempre baseia-se para salvar no banco pelo nome e trata como DANFE ou DACTE
			return TiposArquivos.PDF;

		} else if( to.getNome().endsWith(".xml") ) {
			// sempre que termina com .xml verificamos qual o tipo de arquivo pelo conteudo
			return TipoDaNotaUtil.verifica(to);
		}

		return TiposArquivos.OUTROS;
	}



	private Pessoa savarPessoaCompleto( PessoaNotaTO to, List<Empresa> empresa ) {
		// verifica se as duas pessoas estão cadastradas pela identificacao
		Pessoa pessoa = pessoaRepository.findByIdentificacao( to.getIdentificacao() );
		// se não está cadastrada ...
		if( pessoa == null ) {
			// ... cadastra a pessoa referente ao destinatario ...
			pessoa = cadastrarPessoa(to);
			// ... cadastra um usuário para a pessoa ...
			cadastrarUsuario(pessoa);
			// ... vincula a empresa se for informada.
			vinculaPessoaEmpresa(pessoa, empresa);
		} else {
			// ... ou apenas atualiza o email da pessoa sempre que estiver null no banco e encontrar na Nota
			atualizaEmailCasoNull(pessoa, to.getEmail());
		}
		return pessoa;
	}


	private Usuario cadastrarUsuario(Pessoa pessoa) {
		// cadastra o usuário
		Usuario usuario = new Usuario();
		usuario.setPessoa(pessoa);
		usuario.setMudouSenha(SimNao.N);
		usuario.setAtivo(SimNao.S);
		usuario.setAjuda(SimNao.S);
		usuario.setUsuario(pessoa.getIdentificacao());
		usuario.setSenha("*****"); // sempre grava com 5 asteristcos por causa do Hibernate validator.

		// se o cliente possui uma empresa cadastrada como empresa, grava o usuário como administrador
		final Empresa empresa = empresaService.findOneByIdentificacao(pessoa.getIdentificacao());
		if( empresa != null ) {
			usuario.setRole(Roles.ROLE_ADMINISTRADOR);
			usuario.setEmpresaBase(empresa);
		} else
			usuario.setRole(Roles.ROLE_CLIENTE);

		usuario = usuarioRepository.save(usuario);

		if( empresa != null ) {
			final UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
			usuarioEmpresa.setPadrao(SimNao.N);
			usuarioEmpresa.setUsuario(usuario);
			usuarioEmpresa.setEmpresa(empresa);
			usuarioEmpresaRepository.save(usuarioEmpresa);
		}

		return usuario;
	}


	private void vinculaPessoaEmpresa(Pessoa pessoa, List<Empresa> empresa) {
		// grava o vinculo com a empresa e pessoa e o respectivo e-mail de envio
		if( empresa != null ) {
			PessoaEmpresa pessoaEmpresa = null;
			for (final Empresa emp : empresa) {
				pessoaEmpresa = new PessoaEmpresa();
				pessoaEmpresa.setPessoa(pessoa);
				pessoaEmpresa.setEmpresa(emp);
				pessoaEmpresaService.save(pessoaEmpresa);
			}
		}
	}


	private void atualizaEmailCasoNull(Pessoa pessoa, String email) {
		// verifica conta de email não esta cadastrada e encontrou alguma nota com o email preenchido atualiza cadastro
		if( StringUtils.isEmpty(pessoa.getEmailPrincipal()) && StringUtils.isNotEmpty(email) ) {
			pessoa.setEmailPrincipal(email);
			pessoaRepository.save(pessoa);
		}
	}



	private Pessoa cadastrarPessoa(PessoaNotaTO to) {
		// se não existir destinatario, criar destinatario e usuario
		final Pessoa pessoa = new Pessoa();
		pessoa.setFantasia(to.getFantasia());
		pessoa.setIdentificacao(to.getIdentificacao());
		pessoa.setIe(to.getIE());
		pessoa.setNome(to.getNome());
		pessoa.setEmailPrincipal(to.getEmail());
		return pessoaRepository.save(pessoa);
	}
	
	
	
	/**
	 * salva a lista de envolvidos
	 * 
	 * @param envolvidos
	 * @param dfe
	 * @param empresa
	 */
	private void savarPessoaFazerVinculoDfe(List<PessoaNotaTO> envolvidos, Dfe dfe, List<Empresa> empresa, Pessoa emitente, Pessoa destinatario) {
		
		// caso a lista de envolvidos sejá nulla ou o dfe retorna direto, não tem como fazer vinculo
		if( envolvidos == null || dfe == null || emitente == null || destinatario == null )
			return;
		
		Pessoa pessoa = null;
		for (PessoaNotaTO pnTo : envolvidos) {
			if( !emitente.getIdentificacao().equals( pnTo.getIdentificacao() )  &&  !destinatario.getIdentificacao().equals( pnTo.getIdentificacao() ) ) {
				pessoa = savarPessoaCompleto(pnTo, empresa);
				
				if( pessoa != null ) {
					// faz o vinculo na tabela de envolvidos entre pessoa e dfe
					OutrosEnvolvidos outrosEnvolvidos = new OutrosEnvolvidos();
					outrosEnvolvidos.setDfe(dfe);
					outrosEnvolvidos.setPessoa(pessoa);
					outrosEnvolvidos.setTipo(pnTo.getTipo());
					
					outrosEnvolvidosService.save(outrosEnvolvidos);
				}
			}
		}
	}
	
	
	
}

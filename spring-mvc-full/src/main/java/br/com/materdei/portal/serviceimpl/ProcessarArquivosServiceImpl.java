package br.com.viasoft.portaldef.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.viasoft.enumeration.FormatDate;
import br.com.viasoft.nfe.ArquivoNfeTO;
import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.entities.Usuario;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.enumerations.TiposArquivos;
import br.com.viasoft.portaldef.repositories.DocumentosRepository;
import br.com.viasoft.portaldef.repositories.PessoaRepository;
import br.com.viasoft.portaldef.repositories.UsuarioRepository;
import br.com.viasoft.portaldef.repositories.custom.DfeRepositoryCustom;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;
import br.com.viasoft.util.DateUtil;
import br.com.viasoft.util.FileUtil;
import br.com.viasoft.util.NfeUtil;

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
	
	
	
	
	
	@Override
	public List<File> carregaListaArquivos(String pathName) throws FileNotFoundException {
		return FileUtil.getFiles(pathName);
	}
	
	
	@Override
	@Transactional
	public void processArquivos(List<File> arquivosEmDisco) {
		if( arquivosEmDisco == null || arquivosEmDisco.size() == 0 )
			return;
		
		final List<File> listNfes = new ArrayList<File>();
		final List<File> listDanfs = new ArrayList<File>();
		final List<File> listOthers = new ArrayList<File>();
		
		for (final File file : arquivosEmDisco) {
			final TiposArquivos tipo = obtemTipo(file.getName());
			
			if( tipo.equals(TiposArquivos.NFE) ){
				listNfes.add(file);
			} else if( tipo.equals(TiposArquivos.DANFE) ) {
				listDanfs.add(file);
			} else {
				listOthers.add(file);
			}
		}
		
		// processa os arquivos em ordem
		for (final File file : listNfes) {
			processarArquivosNfe(file);
		}
		for (final File file : listDanfs) {
			processarArquivosDanf(file);
		}
		for(final File file : listOthers) {
			if( file.isFile() ) {
				file.delete();
			}
		}
	}
	
	
	
	private void processarArquivosNfe(File file) {
		final ArquivoNfeTO to = NfeUtil.converteFileToArquivo(file);
		
		// verifica se as duas pessoas estão cadastradas pela identificacao
		Pessoa emitente = pessoaRepository.findByIdentificacao( to.getEmitIdentificacao() );
		if( emitente == null ) {
			// se não existir emitente, criar emitente e usuario
			emitente = new Pessoa();
			emitente.setAtivo(SimNao.S);
			emitente.setEmail(to.getEmitEmail());
			emitente.setFantasia(to.getEmitFantasia());
			emitente.setIdentificacao(to.getEmitIdentificacao());
			emitente.setIe(to.getEmitIE());
			emitente.setNome(to.getEmitNome());
			emitente = pessoaRepository.save(emitente);
			
			final Usuario usuario = new Usuario();
			usuario.setPessoa(emitente);
			usuario.setMudouSenha(SimNao.N);
			usuario.setAtivo(SimNao.S);
			usuario.setRole(Roles.ROLE_CLIENTE);
			usuario.setUsuario(emitente.getIdentificacao());
			usuario.setSenha("*****");
			usuarioRepository.save(usuario);
		} else {
			// verifica conta de email não esta cadastrada e encontrou alguma nota com o email preenchido atualiza cadastro
			if( StringUtils.isEmpty(emitente.getEmail()) && StringUtils.isNotEmpty(to.getEmitEmail()) ) {
				emitente.setEmail(to.getEmitEmail());
				pessoaRepository.save(emitente);
			}
		}
		
		// destinatario
		Pessoa destinatario = pessoaRepository.findByIdentificacao( to.getDestIdentificacao() );
		if( destinatario == null ) {
			// se não existir destinatario, criar destinatario e usuario
			destinatario = new Pessoa();
			destinatario.setAtivo(SimNao.S);
			destinatario.setEmail(to.getDestEmail());
			destinatario.setFantasia(to.getDestFantasia());
			destinatario.setIdentificacao(to.getDestIdentificacao());
			destinatario.setIe(to.getDestIE());
			destinatario.setNome(to.getDestNome());
			destinatario = pessoaRepository.save(destinatario);
			
			final Usuario usuario = new Usuario();
			usuario.setPessoa(destinatario);
			usuario.setMudouSenha(SimNao.N);
			usuario.setAtivo(SimNao.S);
			usuario.setRole(Roles.ROLE_CLIENTE);
			usuario.setUsuario(destinatario.getIdentificacao());
			usuario.setSenha("*****");
			usuarioRepository.save(usuario);
		} else {
			// verifica conta de email não esta cadastrada e encontrou alguma nota com o email preenchido atualiza cadastro
			if( StringUtils.isEmpty(destinatario.getEmail()) && StringUtils.isNotEmpty(to.getDestEmail()) ) {
				destinatario.setEmail(to.getDestEmail());
				pessoaRepository.save(destinatario);
			}
		}
		
		// gravar os documentos fiscais
		final Documentos docNfe = salveDocumento(file);
		
		// gravar dados de dfe
		Dfe dfe = dfeRepositoryCustom.findOne(file.getName().substring(0, file.getName().lastIndexOf(".")));
		
		if( dfe == null ) {
			dfe = new Dfe();
		
			dfe.setChave( to.getNfeChave() );
			dfe.setCliente(destinatario);
			//dfe.setDocDanf(docDanf); - este campo é adicionado ao carregar a danf
			dfe.setDocNfe(docNfe);
			dfe.setDtEmissao(DateUtil.toDate(to.getNfeDtEmissao(), FormatDate.YEAR_MONTH_DAY));
			dfe.setDtSaida(DateUtil.toDate(to.getNfeDtSaida(), FormatDate.YEAR_MONTH_DAY));
			dfe.setEmitente(emitente);
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
		
			dfe = dfeRepositoryCustom.merge(dfe);
		}
		
		// remover arquivo do file sistem
		file.delete();
	}
	
	/**
	 * O arquivo de nfe referente pode não ter sido processado, neste caso o arquivo de danf será carregado na segunda vez que for verificado o diretorio
	 * 
	 * @param file
	 */
	private void processarArquivosDanf(File file) {
		if( file == null )
			return;
		
		// pega a cheve do nome do arquivo
		final String name = file.getName();
		
		// verifica no banco se existe a nfe correspondente
		final Dfe dfe = dfeRepositoryCustom.findOne(name.substring(0, name.lastIndexOf(".")));
		
		if( dfe != null ) {
			// se existe o arquivo de nfe salva o documento
			final Documentos danf = salveDocumento(file);
			
			// vincula o documento a nfe
			dfeRepositoryCustom.updateDanf(danf, dfe);
			
			// deletar a danf correspondente
			file.delete();
		}
	}
	
	/**
	 * Salvar o registro de documento apartir do file nfe/danf
	 * 
	 * @param file
	 * @return
	 */
	private Documentos salveDocumento(File file) {
		
		Documentos doc = documentosRepository.findByNome(file.getName());
		
		// se encontrar no banco o arquivo gravado retorna este
		if( doc != null ) 
			return doc;
		
		// sempre que não estiver cadastrado no bd o nome do arqruivo grava e retorna o arquivo gravado 
		doc = new Documentos();
		doc.setNome( file.getName() );
		doc.setExtencao( FileUtil.getExtencao(file) );
		doc.setArquivo( FileUtil.read(file) );
		
		return documentosRepository.save(doc);
	}
	
	
	@Override
	public TiposArquivos obtemTipo(String name) {
		if( name == null )
			return TiposArquivos.OUTROS;
		
		if( name.startsWith("NFe") && name.endsWith(".xml") ){
			return TiposArquivos.NFE;
		} else if( name.startsWith("NFe") && name.endsWith(".pdf") ) {
			return TiposArquivos.DANFE;
		}
		return TiposArquivos.OUTROS;
	}
	
}

package br.com.viasoft.portaldef.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.entities.Pessoa;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.DocumentosSevice;
import br.com.viasoft.portaldef.service.MailSendService;
import br.com.viasoft.portaldef.service.PessoaEmpresaService;
import br.com.viasoft.portaldef.service.PessoaService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.to.FilterDfeTO;
import br.com.viasoft.portaldef.web.to.FiltroPessoaTO;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class JsonController extends BaseController {

	@Autowired
	private DfeService dfeService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DocumentosSevice documentosSevice;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private PessoaEmpresaService pessoaEmpresaService;
	
	
	
	
	@RequestMapping(value = "/json/update-email-user", method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ROLE_ADMINISTRADOR")
	public String updateEmailUser(Model model, @RequestParam(value="email", required=true) String email, @RequestParam(value="id", required=true) Long id ) {
		final Pessoa pessoa = pessoaService.findOne(id);
		if( pessoa != null ) {
			pessoa.setEmailPrincipal( StringUtils.isBlank( email ) ? null : email );
			pessoaService.save(pessoa);
			model.addAttribute("sucesso", true);
		} else {
			model.addAttribute("sucesso", false);
		}
		model.addAttribute("salvouNull", StringUtils.isBlank(email));
		quantidadeEmailCad(model);
		return Results.JSON;
	}
	
	
	@RequestMapping(value = "/json/nfe-cliente", method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String dadosNfe(Model model, FilterDfeTO filter) {
		model.addAttribute("notas", dfeService.findByFilter(usuarioService.getUsuario(), filter));
		return Results.JSON;
	}
	
	
	@RequestMapping(value = "/json/list-usuarios", method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String usuarios(Model model, FiltroPessoaTO filtroPessoaTO) {
		model.addAttribute("usuarios", pessoaService.buscar(filtroPessoaTO));
		model.addAttribute("countPessoas", pessoaService.count(filtroPessoaTO));
		return Results.JSON;
	}
	
	
	@RequestMapping(value = "/json/enviar-docs", method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String enviarDocs(Model model, 
			@RequestParam("nota") Long nota, 
			@RequestParam(value="emailPrincipal", required=false, defaultValue="") String emailPrincipal, 
			@RequestParam(required=false, value="cc", defaultValue="") String[] cc ) {
		
		final Dfe dfe = dfeService.findOneAndDocs(nota);
		
		// se for usuario normal
		if( Roles.ROLE_CLIENTE.equals( usuarioService.getUsuario().getRole() ) ) {
			// verifica se pode baixar este documento de Nfe
			if( ! dfeService.permiteDownload(usuarioService.getUsuario().getPessoa().getIdentificacao(), dfe.getDocNfe().getId()) ){
				model.addAttribute("permite", false);
				// se não tem acesso mostra pagina não encontrada
				return Results.JSON;
			}
		}
		model.addAttribute("permite", true);
		
		if( dfe != null ) {
			final Documentos[] documentos = { dfe.getDocNfe(), dfe.getDocDanf() };
			
			String[] para = null;
			
			// sempre que o e-mail principal estiver marcado envia para ele
			if( StringUtils.isNotEmpty(emailPrincipal) ) { 
				para = new String[]{ emailPrincipal };
			} else {
				// ou pega o primeiro item do cc
				if( cc != null && cc.length > 0 ) {
					final String[] temp = cc;
					cc = new String[temp.length - 1];
					
					int index = 0;
					for( final String email : temp ) {
						if( StringUtils.isNotEmpty(email) ) {
							if( index == 0 ) {
								para = new String[]{ email };
							} else {
								cc[index - 1] = email;
							}
							index++;
						}
					}
				}
			}
			
			
			if( para != null && para.length > 0 ) {
				final StringBuilder conteudo = new StringBuilder();
				conteudo.append( "<br>Segue em anexo os arquivos:" );
				conteudo.append( "<br><br>------" );
				for( final Documentos doc : documentos ) {
					if( doc != null ) {
						conteudo.append( "<br>" ).append( doc.getNome() );
					}
				}
				conteudo.append( "<br>" );
				
				mailSendService.enviarDocumentos(para, cc, ConfigureApp.getInstance().getEmailPadrao(), "Documentos fiscais", conteudo.toString(), documentos);
				
				model.addAttribute("mensagem", "Arquivos enviados com sucesso!");
			} else {
				model.addAttribute("mensagem", "Informe um e-mail para enviar");
				model.addAttribute("permite", false);
			}
		} else {
			model.addAttribute("mensagem", "Documento não encontrado!");
		}
		
		return Results.JSON;
	}
}

package br.com.viasoft.portaldef.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.servlet.CaptchaServletUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.CaptchaService;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.DocumentosSevice;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;


@Controller
public class DownloadController extends BaseController {
	
	
	@Autowired
	private DfeService dfeService;
	
	@Autowired
	private DocumentosSevice documentosSevice;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CaptchaService captchaService;

	
	
	
	@RequestMapping(value = "/json/download-arquivos/{id}", method = RequestMethod.GET)
	@ConfigAcessoUsuario(role="ALL")
	public String download(Model model, @PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
		
		// se for usuario normal
		if( Roles.ROLE_CLIENTE.equals( usuarioService.getUsuario().getRole() ) ) {
			// verifica se pode baixar este documento
			if( ! dfeService.permiteDownload(usuarioService.getUsuario().getPessoa().getIdentificacao(), id) ){
				// se não tem acesso mostra pagina não encontrada
				return Results.redirect( URL_PAGINA_NAO_ENCONTRADA );
			}
		}
		
		// seleciona o registro do banco de dados
		final Documentos documento = documentosSevice.findOne(id);
		trataResponse(documento, response);
		return null;
	}
	
	
	@RequestMapping(value = "/download/direto", method = RequestMethod.POST)
	public String downloadDireto(Model model, @RequestParam("codigo") String codigo, @RequestParam("answer") String answer, HttpServletResponse response, HttpServletRequest request) {
		
		if( ! captchaService.isCorrect(answer) )
			return Results.redirect( "/" );
		
		// seleciona o registro do banco de dados
		Documentos documento = documentosSevice.findOne( "NFe"+ codigo +".xml" );
		if( documento == null ) {
			documento = documentosSevice.findOne( "CTe"+ codigo +".xml" );
		}
		
		if( documento == null )
			return Results.redirect( "/" );
		
		trataResponse(documento, response);
		return null;
	}
	
	
	@RequestMapping(value = "/img-captcha", method = RequestMethod.GET)
	public void captcha(Locale locale, Model model, HttpServletResponse response, HttpServletResponse request) {
        CaptchaServletUtil.writeImage(response, captchaService.createCaptcha());
	}
	
	
	
	
	
	private void trataResponse(Documentos documento, HttpServletResponse response){
		
		// ajusta response para retornar para download
		response.setContentType ("application/"+ documento.getExtencao());
		response.setHeader ("Content-Disposition", "attachment; filename="+ documento.getNome());
		
		// retorna o arquivo
		try {
			final InputStream in = new ByteArrayInputStream(documento.getArquivo());
			final ServletOutputStream outs = response.getOutputStream();
			int bit = 256;
			final int i = 0;
			try {
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			}
			try {
				outs.flush();
				outs.close();
				in.close(); 
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} catch(final IOException e) {
			e.printStackTrace();
		}
	}
}

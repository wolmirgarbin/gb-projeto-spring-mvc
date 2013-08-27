package br.com.viasoft.portaldef.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.servlet.CaptchaServletUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.enumerations.Roles;
import br.com.viasoft.portaldef.service.CaptchaService;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.DocumentosSevice;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.to.FilterDownloadGrupoTO;


@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
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
		Documentos documento = documentosSevice.findOne( "NFe"+ codigo +".pdf" );
		if( documento == null ) {
			documento = documentosSevice.findOne( "CTe"+ codigo +".pdf" );
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
	
	
	@RequestMapping(value = "/download/grupo-documentos", method = RequestMethod.GET)
	public String downloadGrupo(Locale locale, Model model, FilterDownloadGrupoTO filterDownloadGrupoTO, HttpServletResponse response) {
		response.setContentType ("application/zip");
 		response.setHeader ("Content-Disposition", "attachment; filename=documentos-fiscais.zip");
		
		final List<Documentos> arquivosBD = dfeService.listDocDownload(filterDownloadGrupoTO);
		
		if( arquivosBD == null || arquivosBD.size() == 0 ) 
			return Results.redirect( URL_PAGINA_NAO_ENCONTRADA );
		
		
		final byte[] buffer = new byte[1024];
		
        try {
            // Zip de saida
            final ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            for( final Documentos doc : arquivosBD ) {
            	
	            // Arquivo a ser zipdo
            	final ZipEntry ze = new ZipEntry( doc.getNome() );
	 
	            // Adciona arquivo no Zip de saida
	            zos.putNextEntry(ze);
	 
	            // Ler o Arquivo que sera Zipado
	            final InputStream in = new ByteArrayInputStream( doc.getArquivo() );
	            int len;
	            while ((len = in.read(buffer)) > 0) {
	                zos.write(buffer, 0, len);
	            }
	            in.close();
            }
            // Fecha Zip e entrada
            zos.closeEntry();
            zos.close();
 
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
		
		return null;
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
			try {
				while ((bit) >= 0) {
					bit = in.read();
					if( bit >= 0 )
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

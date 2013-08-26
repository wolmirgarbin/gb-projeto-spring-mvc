package br.com.viasoft.portaldef.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.service.EmpresaService;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;
import br.com.viasoft.portaldef.service.UsuarioService;
import br.com.viasoft.portaldef.util.Results;
import br.com.viasoft.portaldef.web.anotations.ConfigAcessoUsuario;
import br.com.viasoft.portaldef.web.to.ResultUploadTO;
import br.com.viasoft.util.CriptoUtil;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UploadController extends BaseController {
	
	private static final String CHAVE_ACESSO_WS = "DFE-VIASOFT-BY-GARBO";

	@Autowired
	private ProcessarArquivosService processarArquivosService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/ws", method={RequestMethod.POST})
	private ResultUploadTO carregar(@RequestParam("chave") String chave, @RequestParam(value="identificacao", required=false) String identificacao, HttpServletRequest request) {
		final String chaveOficial = CriptoUtil.criptografar( CHAVE_ACESSO_WS );
		
		if( ! chaveOficial.equals( chave ) ) 
			return new ResultUploadTO("404", "Chave de acesso ao web service invalida ["+ chave +"] entre em contato com suporte.");
		
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		return salvarXml(multipartRequest, identificacao);
	}
	
	
	@RequestMapping(value="/upload-files", method={RequestMethod.POST})
	@ConfigAcessoUsuario(role="ALL")
	private String importarArquivoViaApp(HttpServletRequest request) {
		 final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 salvarXml(multipartRequest, null);
		 return Results.redirect("/");
	}
	
	
	private ResultUploadTO salvarXml(MultipartHttpServletRequest multipartRequest, String identificacao) {
		List<Empresa> empresa = new ArrayList<Empresa>();
		if( usuarioService.isLogado() ) {
			empresa.add(usuarioService.getUsuario().getEmpresaBase());
		} else if( StringUtils.isNotBlank( identificacao ) ) {
			empresa = empresaService.listaEmpresas(identificacao.split(","));
		}
		
		return processarArquivosService.processArquivosUpload(multipartRequest.getFiles("dfes"), empresa);
	}
	
	
	@RequestMapping(value="/ws-help", method={RequestMethod.GET})
	private String help() {
		
		return "help";
	}
}
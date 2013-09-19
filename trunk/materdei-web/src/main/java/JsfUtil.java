package br.com.viasoft.util;

import java.io.IOException;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.viasoft.entity.ConfiguracoesGerais;
import br.com.viasoft.entity.Representante;
import br.com.viasoft.entity.RepresentantePK;
import br.com.viasoft.entity.UsandoDePK;
import br.com.viasoft.entity.Usuario;
import br.com.viasoft.entity.UsuarioEmpresa;

/**
 *
 * @author Gaspar
 */
public class JsfUtil {

    private transient FacesContext facesContext;
    private transient RequestContext requestContext;

    public static void clearSession() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        facesContext.getExternalContext().getSession(true);
    }

    public static HttpSession getSession() {
        final FacesContext contexto = FacesContext.getCurrentInstance();
        return (HttpSession) contexto.getExternalContext().getSession(true);
    }

    public static HttpServletRequest getRequest() {
    	final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
    	return (HttpServletRequest)context.getRequest();
    }

    public static void setAttributeSession(final String name, final Object object) {
        final HttpSession session = getSession();
        session.setAttribute(name, object);
    }

    public static String getRequestParameter(final String parameter) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameter);
    }

    public static Object getAttributeSession(final String name) {
        try {
            final HttpSession session = getSession();
            return session.getAttribute(name);
        } catch (final Exception ex) {
            return null;
        }
    }

    public static void removeAttributeSession(final String name) {
        final HttpSession session = getSession();
        session.removeAttribute(name);
    }

    public static void addMessage(final String message, final FacesMessage.Severity Severity) {
        FacesContext.getCurrentInstance().addMessage("controller", new FacesMessage(Severity, message, message));
    }

    public static ELContext getElContext() {
        return FacesContext.getCurrentInstance().getELContext();
    }

    public static ExpressionFactory getExpressionFactory() {
        return FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
    }

    public static String getParameter(final String parameter) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parameter);
    }

    public static String getRequestURL() {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
    }

    public static String getRealPath() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getServletContext().getRealPath("/");
    }

    public static String getContextPath() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getServletContext().getContextPath();
    }

    public static String getURL() {
        final String context = getContextPath();

        final String url = JsfUtil.getRequestURL();
        return url.substring(0, url.indexOf(context)) + context + "/";
    }

    public static void redirect(final String page) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (final IOException e) {
        }
    }

    protected Map getSessionMap() {
        return this.getExternalContext().getSessionMap();
    }

    protected ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static void addMessageErro(final String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", message));
    }

    protected void addMessageAviso(final String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", message));
    }

    protected void addMessageSucesso(final String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
    }

    protected void addMessageErroFatal(final String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", message));
    }

    protected void executeJS(final String metodoJs) {
        getRequestContext().execute(metodoJs);
    }

    public FacesContext getFacesContext() {
        if (null != facesContext) {
            return facesContext;
        } else {
            return FacesContext.getCurrentInstance();
        }
    }

    public RequestContext getRequestContext() {
        if (this.requestContext != null) {
            return this.requestContext;
        } else {
            return RequestContext.getCurrentInstance();
        }
    }
    //==================================================================================================================
    /**
     * Atribui na sessao o usuario logado
     * @param usuarioLogado
     */
    public static void setUsuarioLogado(final Usuario usuarioLogado) {
        setAttributeSession("usuarioLogado", usuarioLogado);
    }

    /**
     * Recupera o usuario logado da sessao
     * @return
     */
    public static Usuario getUsuarioLogado() {
        if (getAttributeSession("usuarioLogado") != null) {
            return (Usuario) getAttributeSession("usuarioLogado");
        } else {
            return null;
        }
    }

    public static UsuarioEmpresa getUsuarioEmpresaDoUsuarioLogado(){
        final Usuario usuarioLogado = getUsuarioLogado();
        if(usuarioLogado != null && usuarioLogado.getUsuarioEmpresaLogado() != null){
            return usuarioLogado.getUsuarioEmpresaLogado();
        }else{
            return null;
        }
    }

    public static void setUsuarioEmpresaDoUsuarioLogado(final UsuarioEmpresa usuarioEmpresaDoUsuarioLogado){
        final Usuario usuarioLogado = getUsuarioLogado();
        if(usuarioLogado != null){
            usuarioLogado.setUsuarioEmpresaLogado(usuarioEmpresaDoUsuarioLogado);
        }
        setUsuarioLogado(usuarioLogado);
    }

    /**
     * Retorna o id do usuario logado
     * @return
     */
    public static Long getIdUsuarioLogado() {
        final Usuario usuario = getUsuarioLogado();
        if (usuario != null) {
            return usuario.getId();
        } else {
            return null;
        }
    }

    /**
     * Obtem o usuario da sessao e retorna a propriedade Login do mesmo
     * @return
     */
    public static String getUsuarioLogin() {
        final Usuario usuarioLogado = getUsuarioLogado();
        if (usuarioLogado != null) {
            return getUsuarioLogado().getLogin();
        } else {
            return null;
        }
    }

    /**
     * Obtem a empresa (estab) do usuario logado na sessão
     *
     * @return
     */
    public static Integer getIdEmpresaUsuarioLogado() {
        final UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaDoUsuarioLogado();
        if (usuarioEmpresa != null) {
            return usuarioEmpresa.getId().getEmpresa();
        } else {
            return null;
        }
    }

    /**
     * Retorna o id do representante vinculado a empresa do usuario logado
     * @return
     */
    public static RepresentantePK getRepresentantePK() {
        final Representante representante = getRepresentante();
        if (representante != null) {
            return representante.getId();
        } else {
            return null;
        }
    }

    /**
     * Retorna o representante vinculado a empresa do usuario logado
     * @return
     */
    public static Representante getRepresentante() {
        final UsuarioEmpresa usuarioEmpresa = getUsuarioLogado().getUsuarioEmpresaLogado();
        if (usuarioEmpresa != null) {
            return usuarioEmpresa.getRepresentante();
        } else {
            return null;
        }
    }

    /**
     * Retorna o e-mail do representante vinculado a empresa do usuario logado
     * @return
     */
    public static String getEmailRepresentante() {
        final Representante representante = getRepresentante();
        if (representante != null) {
            return representante.getEmail();
        } else {
            return null;
        }
    }

    /**
     * Retorna um id de usando de com a empresa do usuario logado ja definida
     * @param tabela
     * @return
     */
    public static UsandoDePK usandoDe(final String tabela) {
        return new UsandoDePK(getIdEmpresaUsuarioLogado(), tabela);
    }

    protected ServletContext getServletContext() {
        return (ServletContext) this.getExternalContext().getContext();
    }

    protected HttpServletRequest getServletRequest() {
        return (HttpServletRequest) this.getExternalContext().getRequest();
    }

    protected HttpServletResponse getServletResponse() {
        return (HttpServletResponse) this.getExternalContext().getResponse();
    }

    /**
     * Esse método obtem a classe ConfiguracoesGerais, na qual tem as configuraçõesPadroes do sistema
     * @return
     */
    public static ConfiguracoesGerais getConfiguracoesGerais(){
        return (ConfiguracoesGerais) getAttributeSession("configuracoesGerais");
    }

    public static void setConfiguracoesGerais(final ConfiguracoesGerais configuracoesGerais){
        setAttributeSession("configuracoesGerais",configuracoesGerais);
    }

}

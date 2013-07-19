package br.com.viasoft.portaldef.service;

import br.com.viasoft.portaldef.entities.Documentos;



public interface MailSendService {

    /**
     * Envia e-mail conforme valores passados por parametro
     * 
     * @param para
     * @param de
     * @param titulo
     * @param conteudo
     * @throws ServiceException retorna erro caso algum dos parametros seja nulo ou ocorra algum erro ao enviar o e-mail
     */
    void sendMail(final String[] para, final String de, final String titulo, final String conteudo);

    /**
     * Obtem o template do html padrao para envio de e-mails
     * 
     * @param titulo
     * @param conteudo
     * @return
     */
    String getHtmlPadrao(String titulo, String conteudo);
    
    /**
     * Envia os documentos por email
     * 
     * @param para
     * @param de
     * @param titulo
     * @param conteudo
     * @param documentos
     */
    void enviarDocumentos(final String[] para, final String de, final String titulo, final String conteudo, final Documentos[] documentos);

}
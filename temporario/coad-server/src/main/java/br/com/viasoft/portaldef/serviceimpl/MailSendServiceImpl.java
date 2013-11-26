package br.com.viasoft.portaldef.serviceimpl;


import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.service.MailSendService;


@Service
public class MailSendServiceImpl extends AbstractMail implements MailSendService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailSendServiceImpl.class);

    @Autowired
    @Qualifier("javaMailSenderSend")
    private JavaMailSender javaMailSenderSend;



    private void sendMail(final String[] para, final String[] cc, final String de, final String resposta, final String titulo, final String conteudo, final String arquivo, final Documentos[] documentos) {
        try {
        	final MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
                    final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "ISO-8859-1");
                    helper.setSubject(titulo);
                    helper.setTo(para);
                    if( cc != null ) {
                    	helper.setCc(cc);
                    }
                    helper.setText(conteudo, true);
                    helper.setFrom(de);
                    if (StringUtils.isNotBlank(resposta)) {
                        helper.setReplyTo(resposta);
                    }

                    if (StringUtils.isNotBlank(arquivo)) {
                        final String nomeArquivo = arquivo.substring(arquivo.lastIndexOf("\\") + 1, arquivo.length());
                        helper.addAttachment(nomeArquivo,  new File(arquivo));
                    }

                    if( documentos != null ) {
                    	for(final Documentos docAnexo : documentos ) {
                    		if( docAnexo != null ) {
                    			helper.addAttachment(docAnexo.getNome(), new ByteArrayResource(docAnexo.getArquivo()));
                    		}
                    	}
                    }
                }
            };
            javaMailSenderSend.send(preparator);

        } catch (final Exception e) {
        	LOGGER.error("Erro ao enviar e-mail: ", e);
        }
    }


    @Override
	public void sendMailSenha(String[] para, String de, String titulo, String conteudo) {
    	sendMail(para, null, de, ConfigureApp.getInstance().getEmailPadrao(), titulo, getHtmlPadrao(titulo, conteudo), null, null);
    }


    @Override
    public void enviarDocumentos(final String[] para, final String[] cc, final String de, final String titulo, final String conteudo, final Documentos[] documentos) {
    	sendMail(para, cc, de, ConfigureApp.getInstance().getEmailPadrao(), titulo, getHtmlPadrao(titulo, conteudo), null, documentos);
    }


    @Override
    public void sendRelatorioCotaMensal(String[] para, String de, String titulo, String conteudo) {
    	sendMail(para, null, de, ConfigureApp.getInstance().getEmailPadrao(), titulo, getHtmlPadrao(titulo, conteudo), null, null);
    }

}

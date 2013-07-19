package br.com.viasoft.portaldef.serviceimpl;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.enumerations.TiposArquivos;
import br.com.viasoft.portaldef.repositories.DocumentosRepository;
import br.com.viasoft.portaldef.service.MailLoadService;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;


@Service
public class MailLoadServiceImpl extends AbstractMail implements MailLoadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailLoadServiceImpl.class);

    @Autowired
    @Qualifier("javaMailSenderLoad")
    private JavaMailSender javaMailSenderLoad;
    
    @Autowired
    private ProcessarArquivosService processarArquivosService;
    
    @Autowired
    private DocumentosRepository documentosRepository;
    
    
    
    
    
    @Override
    public void loadMail() {
    	
    	final JavaMailSenderImpl jms = (JavaMailSenderImpl)javaMailSenderLoad;
    	final Session session = jms.getSession();
    	
		try {
			//final POP3Store emailStore = (POP3Store) session.getStore("pop3");
			final Store emailStore = session.getStore("pop3");
            emailStore.connect( jms.getHost(), jms.getUsername(), jms.getPassword() );
            final Folder folder = emailStore.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
			
			// ler as mensagens do diretório
			final Message messages[] = folder.getMessages();
			
			final Calendar lerMensagensAte = GregorianCalendar.getInstance();
			lerMensagensAte.add(Calendar.HOUR, ConfigureApp.getInstance().getTempoMaximoMensagem() * (-1) );
			final Date param = lerMensagensAte.getTime();
			
			for (int x = 0; x < messages.length; x++) {	
				
				final Message msg = messages[x];
				
 				try {
					// controle de data, sempre lé apenas as mensagens recebidas menos de 1 hora
		        	final Date sentDate = msg.getSentDate();
		        	
		        	// se a mensagem foi recebida antes do tempo informado para leitura já retorna
		        	//if( sentDate != null && sentDate.after(param) ) {
		        	
			        	final Multipart multipart = (Multipart) msg.getContent();
			        	
			            final byte[] buf = new byte[4096];
			            
			            //Aqui você define o caminho que salvará o arquivo.  
			            final String caminhoBase = ConfigureApp.getInstance().getDiretorioArquivos();
			            
			            Integer count = 0;
			            try {
			            	count = multipart.getCount();
			            } catch(final Exception e) {  }
			            
			            for (int i = 0; i < count; i++) {
			            	
			            	final BodyPart part = multipart.getBodyPart(i);
			            	
			            	if( part.getDisposition() != null && (part.getDisposition().equals(BodyPart.ATTACHMENT)) ) {
			            		
			            		// nome do arquivo em anexo
			                	final String nomeDoArquivo = part.getFileName();
			                	if (nomeDoArquivo != null) {
			                		
			                		// pegar o tipo de arquivo em anexo
			                		final TiposArquivos tipo = processarArquivosService.obtemTipo(nomeDoArquivo);
			                		
			                		// se for arquivo conhecido pelo sistema seguindo os padrões 
			                		if( ! tipo.equals(TiposArquivos.OUTROS) ) {
			                			
			                			// verifica se está cadastrado no banco de dados
			                			final Documentos docBD = documentosRepository.findByNome(nomeDoArquivo);
			                			
			                			if( docBD == null ) {
			                				
			                				// salva na pasta de download
					                		final InputStream is = part.getInputStream();
					                		final FileOutputStream fos = new FileOutputStream(caminhoBase +"/"+ nomeDoArquivo);
					                		int bytesRead;
					                		while ((bytesRead = is.read(buf)) != -1) {
					                			fos.write(buf, 0, bytesRead);
					                		}
					                		fos.close();
					                		is.close();
			                			}
			                		}
			                	}
			            	}
			            }
		           // }
				} catch (final Exception e) {
					LOGGER.error("IOException", e);
				}
			}
			
			folder.close(false);
            emailStore.close();
		} catch (final NoSuchProviderException e1) {
			LOGGER.error("NoSuchProviderException", e1);
		} catch (final MessagingException e) {
			LOGGER.error("MessagingException", e);
		}
    }
    
}
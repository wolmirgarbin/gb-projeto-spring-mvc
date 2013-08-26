package br.com.viasoft.portaldef.serviceimpl;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.repositories.ConfigRepository;
import br.com.viasoft.portaldef.repositories.DocumentosRepository;
import br.com.viasoft.portaldef.repositories.custom.ConfigRepositoryCustom;
import br.com.viasoft.portaldef.service.MailLoadService;
import br.com.viasoft.portaldef.service.ProcessarArquivosService;
import br.com.viasoft.to.FilePatternTO;
import br.com.viasoft.util.FileUtil;


@Service
public class MailLoadServiceImpl extends AbstractMail implements MailLoadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailLoadServiceImpl.class);

    /*
    @Autowired
    @Qualifier("javaMailSenderLoad")
    private JavaMailSender javaMailSenderLoad;
    */
    
    @Autowired
    private ProcessarArquivosService processarArquivosService;
    
    @Autowired
    private ConfigRepositoryCustom configRepositoryCustom;
    
    @Autowired
    private ConfigRepository configRepository;
    
    @Autowired
    private DocumentosRepository documentosRepository;
    
    
    
    
    @Override
	public void loadMail() {
    	try {
    		// seleciona o ultimo e-mail da lista de leitura ( mais tempo sem ser verificado ) e atualiza a data para a atual 
    		final Config config = configRepositoryCustom.getNextMailLeitura();
    		config.setLoadDhLeitura(new Date());
    		configRepository.save(config);
    		
    		// faz a leitura do email
    		receiverMail(config);
    		
    	} catch (final Exception ex) {
    		LOGGER.error("Erro ao processar mensagem", ex);
    	}
    }
    
    
    private void receiverMail(final Config config){
    	if( config == null )
    		return;
    	
    	final List<FilePatternTO> lsAnexo = new ArrayList<FilePatternTO>();
    	
    	Store store = null;
    	Folder folder = null;

    	try {
    		// -- Get hold of the default session --
    		final Properties props = System.getProperties();
    		final Session session = Session.getDefaultInstance(props, null);
    		session.setDebug(SimNao.S.equals( config.getLoadDebug()) );

    		// -- Get hold of a POP3 message store, and connect to it --
    		//store = session.getStore("pop3");
    		store = session.getStore("imap");
    		store.connect(config.getLoadHost(), config.getLoadUsuario(), config.getLoadSenha());

    		// -- Try to get hold of the default folder --
    		folder = store.getDefaultFolder();
    		if (folder == null) 
    			throw new Exception("No default folder");

    		// -- ...and its INBOX --
    		folder = folder.getFolder("INBOX");
    		if (folder == null) 
    			throw new Exception("No POP3 INBOX");

    		// -- Open the folder for read only --
    		folder.open(Folder.READ_ONLY);

    		// -- Get the message wrappers and process them --
    		final Message[] msgs = folder.getMessages();

    		for (int x = 0; x < msgs.length; x++) {
    			final List<FilePatternTO> anexos = loadAnexo(msgs[x]);
    			if( anexos != null && anexos.size() > 0 ) {
    				lsAnexo.addAll( anexos );
    			}
    			
    			// sempre que for maior que 20 arquivos processa para gravar no banco de dados e reinicia a lista
    			if( lsAnexo.size() > ConfigureApp.getInstance().getVaiProcessarQuandoTem() ) {
    				processarArquivosService.processArquivos(lsAnexo, null);
    				lsAnexo.clear();
    			}
    		}
    		
    		processarArquivosService.processArquivos(lsAnexo, null);

    	} catch (final Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		try {
    			if (folder!=null) folder.close(false);
    			if (store!=null) store.close();
    		} catch (final Exception ex2) {
    			ex2.printStackTrace();
    		}
    	}
    }
    
    
    private List<FilePatternTO> loadAnexo(final Message msg){
    	
    	final List<FilePatternTO> lsAnexo = new ArrayList<FilePatternTO>();
    	
    	try {
    		// -- Get the message part (i.e. the message itself) --
    		final Part messagePart = msg;
    		final Object content = messagePart.getContent();

    		// -- or its first body part if it is a multipart message --
    		if (content instanceof Multipart) {
    			//messagePart = ((Multipart)content).getBodyPart(0);
    			
    			final byte[] buf = new byte[4096];
	            
	            int count = 0;
	            try {
	            	count = ((Multipart)content).getCount();
	            } catch(final Exception e) {  }
	            
	            for (int i = 0; i < count; i++) {
	            	
	            	final Part part = ((Multipart)content).getBodyPart(i);
	            	
	            	if( part.getDisposition() != null && (part.getDisposition().equals(BodyPart.ATTACHMENT)) ) {
	            		
	            		// nome do arquivo em anexo
	                	final String nomeDoArquivo = part.getFileName();
	                	if (nomeDoArquivo != null && ( nomeDoArquivo.endsWith(".xml") || nomeDoArquivo.endsWith(".pdf") )) {
	                		
	                		// verifica o nome no banco de dados, se não está salvo ainda, grava o registro.
	                		final Documentos docBD = documentosRepository.findByNome(nomeDoArquivo);
	                		if( docBD == null ) {
	                			lsAnexo.add(new FilePatternTO(nomeDoArquivo, nomeDoArquivo.substring(nomeDoArquivo.lastIndexOf(".") + 1, nomeDoArquivo.length()), FileUtil.read(part.getInputStream())));
	                		}
	                	}
	            	}
	            }
    		}
    	} catch (final Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	return lsAnexo;
    }
    
    /*@Override
    public void loadMail() {
    	
    	final List<FilePatternTO> lsAnexo = new ArrayList<FilePatternTO>(); 
    	
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
			                		
			                		lsAnexo.add(new FilePatternTO(nomeDoArquivo, nomeDoArquivo.substring(nomeDoArquivo.lastIndexOf(".") + 1, nomeDoArquivo.length()), FileUtil.read(part.getInputStream())));
			                		
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
		
		// no final da leitura do e-mail processa os arquivos
		processarArquivosService.processArquivos(lsAnexo, null);
    }*/
 

    /**
     * this method will retrieve and read the emails from the INBOX
     * @param popServer
     * @param popUser
     * @param popPassword
     */
    private void receive(String popServer, String popUser, String popPassword) {

    	Store store=null;
    	Folder folder=null;

    	try {
    		// -- Get hold of the default session --
    		final Properties props = System.getProperties();
    		final Session session = Session.getDefaultInstance(props, null);
    		session.setDebug(true);

    		// -- Get hold of a POP3 message store, and connect to it --
    		//store = session.getStore("pop3");
    		store = session.getStore("imap");
    		store.connect(popServer, popUser, popPassword);

    		// -- Try to get hold of the default folder --
    		folder = store.getDefaultFolder();
    		if (folder == null) 
    			throw new Exception("No default folder");

    		// -- ...and its INBOX --
    		folder = folder.getFolder("INBOX");
    		if (folder == null) 
    			throw new Exception("No POP3 INBOX");

    		// -- Open the folder for read only --
    		folder.open(Folder.READ_ONLY);

    		// -- Get the message wrappers and process them --
    		final Message[] msgs = folder.getMessages();

    		System.out.println("ava au.com.covermore.EmailReader"
    				+" msgs " + msgs.length);

    		for (int msgNum = 0; msgNum < msgs.length; msgNum++)
    		{
    			printMessage(msgs[msgNum]);
    			//processMessage(msgs[msgNum]);
    		}

    	}
    	catch (final Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		// -- Close down nicely --
    		try
    		{
    			if (folder!=null) folder.close(false);
    			if (store!=null) store.close();
    		}
    		catch (final Exception ex2) {ex2.printStackTrace();}
    	}
    }

    /**
     * this method will print the message
     * @param message
     */
    private void printMessage(Message message)
    {
    	try
    	{
    		// Get the header information
    		//String from=((InternetAddress)message.getFrom()[0]).getPersonal();
    		//if (from==null) from=((InternetAddress)message.getFrom()[0]).getAddress();
    		//System.out.println("FROM: "+from);

    		//final String subject=message.getSubject();
    		//System.out.println("SUBJECT: "+subject);

    		//String dateTime = message.getSentDate().toString();
    		//System.out.println("DATE: "+ message.getSentDate());

    		// -- Get the message part (i.e. the message itself) --
    		Part messagePart = message;
    		final Object content = messagePart.getContent();

    		// -- or its first body part if it is a multipart message --
    		if (content instanceof Multipart) {
    			messagePart = ((Multipart)content).getBodyPart(0);
    		}

    		// -- Get the content type --
    		/*final String contentType=messagePart.getContentType();

    		// -- If the content is plain text, we can print it --
    		System.out.println("CONTENT:"+contentType);

    		if (contentType.startsWith("text/plain") || contentType.startsWith("text/html"))
    		{
    			final InputStream is = messagePart.getInputStream();

    			final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    			String thisLine=reader.readLine();

    			while (thisLine!=null)
    			{
    				System.out.println(thisLine);
    				thisLine=reader.readLine();
    			}
    		}

    		System.out.println("-----------------------------");*/
    	} catch (final Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    

    /**
     * this method will process an email message and write
     * relevant contents into the xml file
     * @param message
     */
    private void processMessage(Message message) {
    	try {
    		// -- Get the message part (i.e. the message itself) --
    		Part messagePart=message;
    		final Object content=messagePart.getContent();

    		// -- or its first body part if it is a multipart message --
    		if (content instanceof Multipart) {
    			messagePart=((Multipart)content).getBodyPart(0);
    		}

    		// -- Get the content type --
    		final String contentType=messagePart.getContentType();

    		if (contentType.startsWith("text/plain") || contentType.startsWith("text/html"))
    		{
    			final InputStream is = messagePart.getInputStream();

    			final BufferedReader reader = new BufferedReader(new InputStreamReader(is));


    			// now read the message into a List - each message line is a list item
    			// a List will be easier to manipulate and its indexed
    			// remove blank lines at the same time
    			final List msgList = new LinkedList();

    			String thisLine=reader.readLine();
    			while (thisLine!=null)
    			{
    				if(thisLine.trim().length()>0)
    					msgList.add(thisLine);
    				thisLine=reader.readLine();
    			}

    		}
    	}
    	catch(final Exception x)
    	{
    		x.printStackTrace();
    	}

    }

}
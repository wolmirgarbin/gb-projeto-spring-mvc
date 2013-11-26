package br.com.viasoft.portaldef.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import br.com.viasoft.portaldef.service.DocumentosSevice;
import br.com.viasoft.portaldef.service.MailLoadService;

@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class TestController {

	@Autowired
	private MailLoadService mailLoadService;

	@Autowired
	private DocumentosSevice documentosSevice;



	/*@RequestMapping(value = "/test/load-mail", method = RequestMethod.GET)
	public String testMailLoad(Locale locale, Model model) {
		mailLoadService.loadMail();
		return Results.JSON;
	}*/



	/*@RequestMapping(value = "/test/zip", method = RequestMethod.GET)
	public String testZipar(Locale locale, Model model, HttpServletResponse response) {

		final List<Documentos> arquivosBD = new ArrayList<Documentos>();

		final Documentos findOne = documentosSevice.findOne(67L);
		final Documentos findOne2 = documentosSevice.findOne(68L);

		arquivosBD.add( findOne );
		arquivosBD.add( findOne2 );

		response.setContentType ("application/zip");
 		response.setHeader ("Content-Disposition", "attachment; filename=documentos-fiscais.zip");


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
	}  */


	/*@RequestMapping(value = "/test/files", method = RequestMethod.GET)
	public String testReadWrite(Locale locale, Model model, HttpServletResponse response) {

		final byte[] read = FileUtil.read( new File("C:/downloads/temp/NFe41130479851192000523550010000018721492721354.xml") );

		// ajusta response para retornar para download
		response.setContentType ("application/xml");
		response.setHeader ("Content-Disposition", "attachment; filename=NFe41130479851192000523550010000018721492721354.xml");

		// retorna o arquivo
		try {
			final InputStream in = new ByteArrayInputStream(read);
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

		return null;
	}*/

}
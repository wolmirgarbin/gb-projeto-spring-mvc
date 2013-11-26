package br.com.viasoft.portaldef.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.viasoft.portaldef.entities.Dfe;
import br.com.viasoft.portaldef.enumerations.SimNao;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.DocumentosSevice;
import br.com.viasoft.portaldef.web.to.xml.Documento;
import br.com.viasoft.portaldef.web.to.xml.Nota;

@Controller
@RequestMapping("/rest")
public class PortalRestController {

	private final static String CHAVE = "29f0d85aa23d8b8544a85c134c168020";

	@Autowired
	private DocumentosSevice documentosSevice;

	@Autowired
	private DfeService dfeService;


	@RequestMapping(value = "/{chave}/empresa/{cnpjDestinatario}/cliente/{cnpjEmitente}", method = RequestMethod.POST, produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Nota listBy(@PathVariable("chave") String chave, @PathVariable("cnpjDestinatario") String cnpjDestinatario, @PathVariable("cnpjEmitente") String cnpjEmitente) {
		if( validaChave(chave) )
			return new Nota();

		final List<Dfe> lsDfe = dfeService.findByRestIntegracao(cnpjDestinatario, cnpjEmitente);
		return converteToReturn(lsDfe, cnpjDestinatario);
	}


	@RequestMapping(value = "/{chave}/empresa/{cnpjDestinatario}", method = RequestMethod.POST, produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public Nota listBy(@PathVariable("chave") String chave, @PathVariable("cnpjDestinatario") String cnpjDestinatario) {
		if( validaChave(chave) )
			return new Nota();

		final List<Dfe> lsDfe = dfeService.findByRestIntegracao(cnpjDestinatario);
		return converteToReturn(lsDfe, cnpjDestinatario);
	}


	@RequestMapping(value = "/{chave}/documento/{documento}", method = RequestMethod.POST)
	public void getXml(@PathVariable("chave") String chave, @PathVariable("documento") String documento, HttpServletResponse response) {
		if( validaChave(chave) )
			return;

		final Dfe dfe = dfeService.findOneByChaveJoinNfe(documento);

		if( dfe == null || dfe.getDocNfe() == null )
			return;

		// se for null atribui 0
		if( ! SimNao.S.equals( dfe.getSincronizado() ) ) {
			 dfe.setSincronizado(SimNao.S);
			 dfeService.save(dfe);
		}

		// ajusta response para retornar para download
		response.setContentType ("application/"+ dfe.getDocNfe().getExtencao());
		response.setHeader ("Content-Disposition", "attachment; filename="+ dfe.getDocNfe().getNome());

		// retorna o arquivo
		try {
			final InputStream in = new ByteArrayInputStream(dfe.getDocNfe().getArquivo());
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


	private Nota converteToReturn(List<Dfe> lsDfe, String cnpj) {
		final Nota nota = new Nota();
		nota.setIdentificacao( cnpj );
		nota.setDocumento(new ArrayList<Documento>());
		Documento documento = null;
		for (final Dfe dfe : lsDfe) {
			documento = new Documento();
			documento.setChave( dfe.getChave() );
			documento.setEmissao( dfe.getDtEmissao() );
			documento.setEmitente( dfe.getEmitente().getNome() );
			documento.setNumero( dfe.getNumero() );
			documento.setSerie( dfe.getSerie() );

			nota.getDocumento().add( documento );
		}
		return nota;
	}


	private boolean validaChave(String chave) {
		if( CHAVE.equals(chave) ) {
			return false;
		} else {
			return true;
		}
	}
}
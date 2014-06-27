package com.itcuties.examples.webapps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.jdbc.JDBCCategoryDataset;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;

@ManagedBean
public class DownloadBean implements Serializable {

	private static final long serialVersionUID = 626953318628565053L;
	
	// A PDF to download
	private static final String PDF_URL = "http://download.itcuties.com/jsf2/jsf2-download-pdf/itcuties-logo-concept.pdf";
	
	/**
	 * This method reads PDF from the URL and writes it back as a response. 
	 * @throws IOException 
	 */
	public void downloadPdf() throws IOException {
		// Get the FacesContext
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// Get HTTP response
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
		// Set response headers
		response.reset();	// Reset the response in the first place
        response.setHeader("Content-Type", "application/pdf");	// Set only the content type
        
		// Open response output stream
		OutputStream responseOutputStream = response.getOutputStream();
		
		// Read PDF contents
		URL url = new URL(PDF_URL);
        InputStream pdfInputStream = url.openStream();
        
        // Read PDF contents and write them to the output
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
        	responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }
        
        // Make sure that everything is out
        responseOutputStream.flush();
         
        // Close both streams
        pdfInputStream.close();
        responseOutputStream.close();
        
        // JSF doc: 
        // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated 
        // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
        // as soon as the current phase is completed.
        facesContext.responseComplete();
        
	}
	
	/**
	 * Gerar imagem de um relatório jasper.
	 * 
	 * 
	 * @throws IOException
	 * @throws JRException
	 */
	public void downloadJasper() throws IOException, JRException {
		// Get the FacesContext
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// Get HTTP response
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
		OutputStream out = response.getOutputStream();
		
		response.setContentType("");
		
		/*if (printType==0){
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			
		} else { */
		    int pageIndex = 0;
		    Collection<String> ls = new ArrayList<String>();
		    ls.add("teste");
		    JRDataSource jrds = new JRBeanCollectionDataSource(ls);
		    
		    File fr = new File("C:\\report1.jasper");
		    FileInputStream stream = new FileInputStream(fr);
		    
		    
		    //JasperRunManager.runReportToPdfStream(stream, out, null, jrds);
		    response.reset();	// Reset the response in the first place
	        response.setHeader("Content-Type", "image/jpg");
	        response.setHeader("Content-Disposition", "attachment; filename=certificado.jpg");
		    
		    JasperPrint jasperPrint = JasperFillManager.fillReport(stream, null, jrds);
		    
            BufferedImage pageImage = new BufferedImage(jasperPrint.getPageWidth() + 1, jasperPrint.getPageHeight() + 1, BufferedImage.TYPE_INT_RGB);
            JRGraphics2DExporter exporter = new JRGraphics2DExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, pageImage.getGraphics());
            exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(pageIndex));
            exporter.exportReport();
            ImageIO.write(pageImage, "jpeg", out);
        //}
		out.close();
	}
	
	public static void main(String[] args) throws Exception {
		File f = new File("C:/testeimage.png");
		if( !f.exists() )
			f.createNewFile();
		
		OutputStream out = new FileOutputStream(f);
		
		int pageIndex = 0;
	    Collection<String> ls = new ArrayList<String>();
	    JRDataSource jrds = new JRBeanCollectionDataSource(ls);
	    
	    File fr = new File("C:\\report1.jasper");
	    FileInputStream stream = new FileInputStream(fr);
	    
	    
	    JasperRunManager.runReportToPdfStream(stream, out, null, jrds);
	    
	    /*JasperPrint jasperPrint = JasperFillManager.fillReport(stream, null);
        BufferedImage pageImage = new BufferedImage(jasperPrint.getPageWidth() + 1, jasperPrint.getPageHeight() + 1, BufferedImage.TYPE_INT_RGB);
        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, pageImage.getGraphics());
        exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(pageIndex));
        exporter.exportReport();
        ImageIO.write(pageImage, "jpeg", out);*/
	    //}
		out.close();
		stream.close();
	}
	
	
	/*
	 * public static void getInputStreamReportPedido(OutputStream outputStream, Collection<RelatorioPedidoListTO> lista) throws ServiceException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    ServletContext servletContext = (ServletContext) externalContext.getContext();
	    String arquivo = servletContext.getRealPath("WEB-INF/relatorios/pedido.jasper");
	    JRDataSource jrds = new JRBeanCollectionDataSource(lista);
	    
	    HashMap<String,Object> map = new HashMap<String, Object>();
	    map.put("SUBREPORT_DIR", servletContext.getRealPath("WEB-INF/relatorios/") +"\\");
	    
	    FileInputStream reportFileInputStream = null;
	            
	    try {
	    	
	        // Read PDF contents
			reportFileInputStream = new FileInputStream(new File(arquivo));
	        JasperRunManager.runReportToPdfStream(reportFileInputStream, outputStream, map, jrds);
	        
	    } catch (Exception e) {
	    	throw new ServiceException(e.getMessage());
	    } finally {
	    	if( reportFileInputStream != null ) {
	    		try {
					reportFileInputStream.close();
				} catch (IOException e) { }
	    	}
	    }
	}
	 */
	
}

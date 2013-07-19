package br.com.viasoft.portaldef.serviceimpl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamSource;

public class AbstractMail {

    public String getHtmlPadrao(String titulo, String conteudo) {
        final StringBuilder html = new StringBuilder();
        html.append("<?xml version='1.0' encoding='UTF-8' ?>");
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        html.append("<head>");
        html.append("<title>");
        html.append(titulo);
        html.append("</title>");
        html.append("</head>");
        html.append("<body style=\"padding:0; margin:0;\">");
        html.append("<table width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\">");
        html.append("<tr height=\"30%\">");
        html.append("<td width=\"80%\" style=\"background: #990000; color: #FFFFFF; font: bold 25px verdana; padding: 20px 0 20px 50px\" valign=\"middle\">");
        html.append(titulo);
        html.append("</td>");
        html.append("<td width=\"20%\" align=\"center\" valign=\"middle\" style=\"color:#990000; font-size: 25px\">");
        html.append("WEB/MOBILE");
        html.append("</td>");
        html.append("</tr>");
        html.append("<tr height=\"60%\">");
        html.append("<td colspan=\"2\" align=\"center\" style=\"padding: 20px; font-size: 12px; font-family: verdana; text-align: left;\" valign=\"top\">");
        html.append(conteudo);
        html.append("</td>");
        html.append("</tr>");
        html.append("<tr height=\"10%\" style=\"font:bold 12px verdana; background: #990000; color: #FFFFFF;\">");
        html.append("<td style=\"padding: 20px\">");
        
        html.append("</td>");
        html.append("<td align=\"right\" style=\"color:#FFFFFF; font-size: 25px; padding-right: 20px\">");
        html.append("VIASOFT INFORMATICA");
        html.append("</td>");
        html.append("</tr>");
        html.append("</table>");
        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }
	
    
    
    protected InputStreamSource createInputStreamSource(final InputStream inputStream) {
    	return new InputStreamSource() {
			@Override
			public InputStream getInputStream() throws IOException {
				return inputStream;
			}
		}; 
	}
}

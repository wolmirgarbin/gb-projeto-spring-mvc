package br.com.garbo.sites.tag;

public class GarboTag {
	
	public static String escapeHtml(String html) {
		String texto = null;
		if( html != null ) {
			texto = html.replaceAll("<", "& lt;");
			texto = texto.replaceAll(">", "& gt;");
		}
		return texto;
	}
	
	
	public static String ajustaTexto(String texto, Integer quantidade) {
		if( texto == null )
			return "-";
		
		if( texto.length() <= quantidade ) 
			return texto;
		else {
			texto = texto.substring(0, quantidade);
			return texto +"...";
		}
	}
}
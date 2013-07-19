package br.com.garbo.util;

public class NumberUtil {

	public static String apenasNumeros(String texto) {
		if( texto == null )
			return null;
		
		final StringBuffer toReturn = new StringBuffer();
		for(final char c : texto.toCharArray()) {
			if( c >= '0' && c <= '9' ) {
				toReturn.append(c);
			}
		}
		return toReturn.toString();
	}
	
}

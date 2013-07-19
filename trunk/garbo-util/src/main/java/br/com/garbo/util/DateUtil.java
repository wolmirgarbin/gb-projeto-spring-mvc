package br.com.garbo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.garbo.enumeration.FormatDate;

public class DateUtil {
	
	/**
	 * 	Expression: ^\d{1,2}\/\d{1,2}\/\d{4}$
		Description: This regular expressions matches dates of the form XX/XX/YYYY where XX can be 1 or 2 digits long and YYYY is always 4 digits long.
		Matches: 4/1/2001|||12/12/2001|||55/5/3434
		Non-Matches: 1/1/01|||12 Jan 01|||1-1-2001 
	 * @param data
	 * @return
	 */
	public static boolean isFormatoDate(final String data) {
		final Pattern padrao = Pattern.compile("^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$");  
		final Matcher pesquisa = padrao.matcher(data);  
		return pesquisa.matches();
	}

	
	/**
	 * Transforma de acordo com o formato
	 * 
	 * @param data
	 * @return
	 */
	public static Date toDate(String data, FormatDate format) {
		if( data == null ) 
			return null;
		
		if( data.length() != format.getFormat().length() ) 
			return null;
		
		//if( isFormatoDate(data) ) {
			try {
				return new SimpleDateFormat( format.getFormat() ).parse(data);
			} catch (final Exception e) {
				e.printStackTrace();
				return null;
			}
		//}
		//return null;
	}
}
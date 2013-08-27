package br.com.garbo.corp.web.util;

public class Results {

	public static final String JSON = "jsonView";
	private static final String REDIRECT = "redirect:";
	
	
	public static String redirect(final String page, ResultParam... param ){
		return REDIRECT + createUrl(page, param); 
	}
	
	
	public static String createUrl( final String page, ResultParam... param ) {
		final StringBuilder res = new StringBuilder();
		
		if( page.startsWith("/") ) {
			res.append( page );
		} else {
			res.append("/").append( page );
		}
		
		if( param != null && param.length > 0 ) {
			res.append("?");
			
			int i = 0;
			for (final ResultParam resultParam : param) {
				res.append(resultParam.toString());
				i++;
				if( i < param.length ) {
					res.append("&");
				}
			}
		}
		return res.toString();
	}
}

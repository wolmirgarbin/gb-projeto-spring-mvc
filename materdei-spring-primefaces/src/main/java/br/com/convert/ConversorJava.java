package br.com.convert;

public class ConversorJava implements Conversor {

	@Override
	public String textToHTML(String text) {
		
		
		
		return template(text);
	}
	
	
	@Override
	public String template(String html) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("<div class='code-java'>")
					.append("")
			   .append("</div>");
		return retorno.toString();
	}
}

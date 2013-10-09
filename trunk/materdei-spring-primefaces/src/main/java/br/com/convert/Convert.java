package br.com.convert;

public class Convert {

	private StringBuffer html;
	
	public Convert toHTML(String text) {
		this.html = new StringBuffer();
		
		text = CaracteresEspeciais.stringToHTMLString( text );
		
		this.html.append(text);
		
		return this;
	}
	
	
	public String get(){
		return html.toString();
	}
}

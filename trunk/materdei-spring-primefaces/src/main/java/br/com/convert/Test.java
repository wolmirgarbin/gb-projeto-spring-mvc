package br.com.convert;

public class Test {

	public static void main(String[] args) {
		
		String texto = "[title]Titulo da tela qualquer lugar do texto[/title]"+
					   "[p]Qualquer linha é de texto[/p]"+
					   "[java]" +
					   	"import java.util.List;" +
					   	"" +
					   	"public class Teste {" +
					   	"	// comentario em java" +
					   	"}" +
					   "[/java]"+
					   "[html]" +
					   "<html>" +
					   "	<body>Beleza" +
					   " 	</body>" +
					   "<html>" +
					   "[/html]";
		
		System.out.println( new Convert().toHTML( texto ).get() );
		
	}

}

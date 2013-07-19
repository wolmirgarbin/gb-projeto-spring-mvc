package br.com.garbo.util;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import br.com.garbo.nfe.ArquivoNfeTO;

public class NfeUtil {
	
	
	public static ArquivoNfeTO converteFileToArquivo(final File file) {
		final ArquivoNfeTO toReturn = new ArquivoNfeTO();

		try {
			final SAXBuilder builder = new SAXBuilder();
			final Document doc = builder.build(file);
			
			final Element root = doc.getRootElement();
			final Namespace ns = root.getNamespace();
			
			final Element nfe = root.getChild("NFe", ns);
			
			final Element infNFe = nfe.getChild("infNFe", ns);
			toReturn.setNfeChave( infNFe.getAttributeValue("Id") );
			
			final Element ide = infNFe.getChild("ide", ns);
			toReturn.setNfeNatOp( ide.getChildText("natOp", ns) );
			toReturn.setNfeSerie( ide.getChildText("serie", ns) );
			toReturn.setNfeNumeroNnf( ide.getChildText("nNF", ns) );
			toReturn.setNfeDtEmissao( ide.getChildText("dEmi", ns) );
			toReturn.setNfeDtSaida( ide.getChildText("dSaiEnt", ns) );
			toReturn.setNfeTipoNf( ide.getChildText("tpNF", ns) );
			toReturn.setNfeTpAmb( ide.getChildText("tpAmb", ns) );
			
			final Element emit = infNFe.getChild("emit", ns);
			toReturn.setEmitEmail( emit.getChildText("email", ns) );
			toReturn.setEmitFantasia( emit.getChildText("xFant", ns) );
			toReturn.setEmitIdentificacao( emit.getChildText("CNPJ", ns) != null ? emit.getChildText("CNPJ", ns) : emit.getChildText("CPF", ns) );
			toReturn.setEmitIE( emit.getChildText("IE", ns) );
			toReturn.setEmitNome( emit.getChildText("xNome", ns) );
			
			final Element dest = infNFe.getChild("dest", ns);
			toReturn.setDestEmail( dest.getChildText("email", ns) );
			toReturn.setDestFantasia( dest.getChildText("xFant", ns) );
			toReturn.setDestIdentificacao( dest.getChildText("CNPJ", ns) != null ? dest.getChildText("CNPJ", ns) : dest.getChildText("CPF", ns) );
			toReturn.setDestIE( dest.getChildText("IE", ns) );
			toReturn.setDestNome( dest.getChildText("xNome", ns) );
			
			final Element total = infNFe.getChild("total", ns);
			final Element ICMSTot = total.getChild("ICMSTot", ns);
			toReturn.setNfeVnf( ICMSTot.getChildText("vNF", ns) );
			
			
			// retorno da assinatura
			final Element protNFe = root.getChild("protNFe", ns);
			if( protNFe != null ) {
				final Element infProt = protNFe.getChild("infProt", ns);
				if( infProt != null ) {
					toReturn.setRetDtAutoriza( infProt.getChildText("dhRecbto", ns) );
					toReturn.setRetMotivo( infProt.getChildText("xMotivo", ns) );
					toReturn.setRetProtocolo( infProt.getAttributeValue("Id") );
					toReturn.setRetStat( infProt.getChildText("cStat", ns) );
				}
			}
			
		} catch(final Exception e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}

}

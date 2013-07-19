package br.com.viasoft.portaldef.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import br.com.viasoft.portaldef.enumerations.TiposArquivos;

public interface ProcessarArquivosService extends Serializable {

	List<File> carregaListaArquivos(final String pathName) throws FileNotFoundException ;
	
	void processArquivos(List<File> arquivosEmDisco);

	TiposArquivos obtemTipo(String name);
	
}
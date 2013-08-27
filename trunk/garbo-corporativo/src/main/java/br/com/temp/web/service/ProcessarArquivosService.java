package br.com.viasoft.portaldef.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.viasoft.enumeration.TiposArquivos;
import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.web.to.ResultUploadTO;
import br.com.viasoft.to.FilePatternTO;

public interface ProcessarArquivosService extends Serializable { 

	List<File> carregaListaArquivos(final String pathName) throws FileNotFoundException ;
	
	boolean processArquivos(List<FilePatternTO> arquivosEmDisco, List<Empresa> empresa);
	
	ResultUploadTO processArquivosUpload(List<MultipartFile> arquivosEmDisco, List<Empresa> empresa);
	
	void processArquivosDisk(List<File> arquivosEmDisco);
	
	TiposArquivos obtemTipo(FilePatternTO name);
	
}
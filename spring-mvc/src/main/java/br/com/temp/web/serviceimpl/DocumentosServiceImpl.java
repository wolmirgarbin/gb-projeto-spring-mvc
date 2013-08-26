package br.com.viasoft.portaldef.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Documentos;
import br.com.viasoft.portaldef.repositories.DocumentosRepository;
import br.com.viasoft.portaldef.service.DocumentosSevice;

@Service
public class DocumentosServiceImpl implements DocumentosSevice {

	private static final long serialVersionUID = 2658580997391184486L;
	
	@Autowired
	private DocumentosRepository data;
	
	
	@Override
	public Documentos findOne(Long id) {
		return data.findOne(id);
	}
	
	@Override
	public List<Documentos> findByIdIn(Long[] id) {
		return findByIdIn(id);
	}
	
	@Override
	public Documentos findOne(String nome) {
		return data.findByNome(nome);
	}

}
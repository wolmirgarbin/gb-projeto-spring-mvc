package br.com.viasoft.portaldef.service;

import java.util.List;

import javax.mail.Message;

import br.com.viasoft.to.FilePatternTO;

public interface MailLoadService {

	void loadMail();

	List<FilePatternTO> loadAnexo(Message message);

}
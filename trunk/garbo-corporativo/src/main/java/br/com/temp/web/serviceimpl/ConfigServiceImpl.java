package br.com.viasoft.portaldef.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Config;
import br.com.viasoft.portaldef.repositories.ConfigRepository;
import br.com.viasoft.portaldef.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	private static final long serialVersionUID = 1347481260417084073L;
	
	@Autowired
	private ConfigRepository data;
	
	
	@Override
	public Config save(Config config) {
		return data.save(config);
	}
	
	@Override
	public Config find() {
		return data.findOne(1L);
	}
}
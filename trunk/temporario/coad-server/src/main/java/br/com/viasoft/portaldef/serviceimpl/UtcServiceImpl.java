package br.com.viasoft.portaldef.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.entities.Empresa;
import br.com.viasoft.portaldef.entities.Utc;
import br.com.viasoft.portaldef.repositories.UtcRepository;
import br.com.viasoft.portaldef.service.UtcService;

@Service
public class UtcServiceImpl implements UtcService {

	private static final long serialVersionUID = 1347481260417084073L;

	@Autowired
	private UtcRepository data;


	@Override
	public List<Utc> findAll() {
		return data.findAll();
	}


	@Override
	public Date dateForUTC(String utc) {
		final DateTime dt = new DateTime(DateTimeZone.forID( utc ));
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dt.toString());
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return dt.toDate();
	}


	@Override
	public Date dateForUTCEmpresa(Empresa empresa) {
		if( empresa == null )
			return new Date();

		return dateForUTC(empresa.getUtc());
	}

}
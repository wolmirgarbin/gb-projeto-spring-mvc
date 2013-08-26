package br.com.viasoft.portaldef.web.to;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FilterDownloadGrupoTO implements Serializable {

	private static final long serialVersionUID = 8866725474837398264L;

	private Boolean docXml;
	private Boolean docDanfe;
	private Boolean docEventos;
	private String ids;
}

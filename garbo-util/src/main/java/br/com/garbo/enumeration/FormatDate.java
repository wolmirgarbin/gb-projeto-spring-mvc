package br.com.garbo.enumeration;

import lombok.Getter;

public enum FormatDate {

	DAY_MONTH_YEAR("dd/MM/yyyy"), 
	YEAR_MONTH_DAY("yyyy-MM-dd");
	
	@Getter
	private String format;

	private FormatDate(String format) {
		this.format = format;
	}
}
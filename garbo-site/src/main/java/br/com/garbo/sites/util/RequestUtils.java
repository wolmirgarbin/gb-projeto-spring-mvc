package br.com.garbo.sites.util;

import javax.servlet.http.HttpServletRequest;


public class RequestUtils {
	
	public static String path(HttpServletRequest request) {
		return "http://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath() +"/";
	}

}

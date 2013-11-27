package br.com.garbo.sites.cookie;

import javax.servlet.http.Cookie;

public class CookieUtils {

	public static Cookie createCookie(String name, String value) {
		Cookie c = new Cookie(name, value);
		c.setMaxAge(86400); // um dia
		return c;
	}
	
}
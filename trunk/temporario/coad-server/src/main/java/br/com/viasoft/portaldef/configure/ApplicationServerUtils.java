package br.com.viasoft.portaldef.configure;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ApplicationServerUtils {


	public static boolean isDesenv(){
		return "desenvolvimento".equals(ConfigureApp.getInstance().getInstanciaApp());
	}


	public static boolean isProduc(){
		return "producao".equals(ConfigureApp.getInstance().getInstanciaApp());
	}


	public static String getPropertyByNameLocation(final String relativePropertiName){
		String location = "classpath:/conf/"+ (isDesenv() ? "desenv" : "produc");
		if(!location.endsWith("/")){
			location += "/";
		}
		return location+relativePropertiName;
	}

	public static Resource getPropertyResouceByNameLocation(final String relativePropertiName){
		final String location = getPropertyByNameLocation(relativePropertiName);
		return getResourceByNameLocation(location);
	}

	public static Resource getResourceByNameLocation(String location){
		if(StringUtils.isNotEmpty(location)){
			if(location.startsWith("classpath:/")){
				location = location.substring("classpath:".length());
				return new ClassPathResource(location);
			}
			if(location.startsWith("file:/")){
				location = location.substring("file:".length());
				return new FileSystemResource(location);
			}
		}
		return null;
	}

	public final static boolean validateIPAddress( final String  ipAddress ){
		final String[] parts = ipAddress.split( "\\." );
		if ( parts.length != 4 ) {
			return false;
		}
		for ( final String s : parts ) {
			final int i = Integer.parseInt( s );
			if ( (i < 0) || (i > 255) ) {
				return false;
			}
		}
		return true;
	}

	public static String getValidClientRequestIP(final HttpServletRequest request){
		String clientIp = StringUtils.defaultIfEmpty(request.getHeader("x-forwarded-for"),request.getRemoteAddr());
		clientIp = StringUtils.defaultString(clientIp);
		if(clientIp.indexOf(",") != -1){
			clientIp = clientIp.split(",")[0];
		}
		if(validateIPAddress(clientIp)){
			return clientIp;
		}
		return request.getRemoteAddr();
	}

	public static Map<String,String> getApplicationVersion(final ServletContext context)throws Exception{
		final Map<String,String> params = new HashMap<String, String>();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(context.getRealPath("/META-INF/MANIFEST.MF"));
			final Manifest manifest = new Manifest(fis);

			final Attributes mainAttributes = manifest.getMainAttributes();

			final String buildNumber = mainAttributes.getValue("Build-Number");
			if(buildNumber != null){
				params.put("buildNumber", buildNumber);
			}

			final String revisionNumber = mainAttributes.getValue("Svn-Revision");
			if(revisionNumber != null){
				params.put("revisionNumber", revisionNumber);
			}

			params.put("env", ApplicationServerUtils.getPropertyByNameLocation(""));
		}finally{
			if(fis != null){
				fis.close();
			}
		}
		return params;
	}
}

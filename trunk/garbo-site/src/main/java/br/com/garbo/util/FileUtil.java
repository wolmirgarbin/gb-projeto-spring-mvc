package br.com.garbo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class FileUtil {

	/**
	 * Retorna a lista de diretorios do file system
	 * 
	 * @param pathName
	 * @return
	 */
	public static List<File> getFiles(final String pathName) throws FileNotFoundException {
		final List<File> toReturn = new ArrayList<File>();
		final File diretorio = new File(pathName);
		
		if( ! diretorio.exists() ) 
			throw new FileNotFoundException("O caminho não existe");
		
		if( ! diretorio.isDirectory() ) 
			throw new FileNotFoundException("O caminho não é um diretório");
		
		
		for (final File file : diretorio.listFiles()) {
			toReturn.add(file);
		}
		
		return toReturn;
	}
	
	
	/**
	 * Retorna se o diretorio/arquivo existe
	 * 
	 * @param pathName
	 * @return
	 */
	public static boolean exists(final String pathName) {
		final File file = new File(pathName);
		return file.exists();
	}
	
	
	/**
	 * Retorna se é um diretorio ou não
	 * 
	 * @param pathName
	 * @return
	 */
	public static boolean isDirectory(final String pathName) {
		final File file = new File(pathName);
		return file.isDirectory();
	}
	
	
	/**
	 * Transformar arquivo em bytes
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] read(File file) {
		
		if( ! file.isFile() )
			return null;

	    ByteArrayOutputStream ous = null;
	    InputStream ios = null;
	    try {
	        final byte[] buffer = new byte[4096];
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ( (read = ios.read(buffer)) != -1 ) {
	            ous.write(buffer, 0, read);
	        }
	    } catch ( final IOException e) {
	    	return null;
	    } finally { 
	        try {
	             if ( ous != null ) 
	                 ous.close();
	        } catch ( final IOException e) { }

	        try {
	             if ( ios != null ) 
	                  ios.close();
	        } catch ( final IOException e) { }
	    }
	    return ous.toByteArray();
	}
	
	
	public static String getExtencao(File file) {
		if( file == null ) {
			return null;
		}
		final String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1, name.length());
	}
	
}

package br.com.viasoft.util;

import java.util.Random;

public class PassUtil {

	/**
	 * Gera uma senha aleatoria para o ususarios
	 * 
	 * @param len
	 * @return
	 */
    public static String getRandomPass(int len){  
	    final char[] chart ={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};  
	      
	    final char[] senha= new char[len];  
	      
	    final int chartLenght = chart.length;  
	    final Random rdm = new Random();  
	      
	    for (int x=0; x<len; x++)  
	    	senha[x] = chart[rdm.nextInt(chartLenght)];  
	      
	    return new String(senha);  
    }  
	
    
}
package com.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {
	
	private FileUtils(){}
	
	public static String loadAsString(String file){
		StringBuilder sb = new StringBuilder();
		try{
			BufferedReader b = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = b.readLine()) != null){
				sb.append(buffer + '\n');
			}
			b.close();
		}catch(Exception e){
			//TODO
		}
		return sb.toString();
	}
}

package com.wolf.http.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import android.content.pm.PackageManager;


/**
 * 
 * @author wolvesqun
 *
 */
public class WFHttpTool {

	/**
	 *  读取流数据
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(InputStream inputStream) throws IOException
	{
		if(inputStream == null) return null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] bt = new byte[1024];
		int len = -1;
		while((len = inputStream.read(bt)) != -1)
		{
			os.write(bt, 0, len);
		}
		os.close();
		inputStream.close();
		return os.toByteArray();
	}
	
	/**
	 * 是否为图片请求地址
	 * @param URLString
	 * @return
	 */
	public static boolean isImageRequest(String URLString)
	{
		if(URLString == null || URLString.length() == 0) return false;
		
		if(URLString.contains(".jpg") || 
		   URLString.contains(".png") ||
		   URLString.contains(".gif") ||
		   URLString.contains(".jpeg")|| 
		   URLString.contains(".bmp"))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 是否为网页文件请求（css|js）
	 * @param URLString
	 * @return
	 */
	public static boolean isWebFileRequest(String URLString)
	{
		if(URLString == null || URLString.length() == 0) return false;
		
		if(URLString.contains(".css") || 
		   URLString.contains(".js"))
		{
			return true;
		}
		
		return false;
	}
	
	public static String MD5(String string) {
	    byte[] hash = null;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } 
	    catch (Exception e) {  
	    }
	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}
	
	
	
}

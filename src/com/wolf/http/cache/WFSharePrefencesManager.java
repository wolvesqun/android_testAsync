package com.wolf.http.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.wolf.http.util.WFHttpEnvironment;
import com.wolf.http.util.WFHttpTool;

/**
 * 
 * @author wolvesqun
 *
 */
public class WFSharePrefencesManager {
	
	
	
	public static boolean saveObject(Object obj, String key)
	{
		if(obj == null || key == null|| key.length() == 0)
		{
			return false;
		}
		boolean rs = false;
	    // 创建字节输出流  
	    ByteArrayOutputStream baos = null; 
	    ObjectOutputStream oos = null;
	    try {  
	    	 baos = new ByteArrayOutputStream(); 
	        // 创建对象输出流，并封装字节流  
	    	 oos = new ObjectOutputStream(baos);  
	        // 将对象写入字节流  
	        oos.writeObject(obj);  
	        // 将字节流编码成base64的字符窜  
	        String encodeResult = new String(Base64.encode(baos  
	                .toByteArray(), Base64.NO_WRAP)); 
	        String encodeKey = getEncodeKey(key);
	        Editor edit = getEditor(encodeKey);
	        
	        edit.putString(encodeKey, encodeResult);  
	        edit.commit();  
	        rs = true;
	    } catch (Exception e) {
	    	
	    }  
	    finally
	    {

			try {
				if(baos != null) 
				{
					baos.close();
					baos = null;
				}
				if(oos != null)
				{
					oos.close();
					oos = null;
				}
				
			} catch (IOException e) {
				
			}
	    }
		
		return rs;
	}
	
	/**
	 * 保存int
	 * @param value
	 * @param key
	 * @return
	 */
	public static boolean saveInt(int value, String key)
	{
		if(key == null) return false;
		String encodeKey = getEncodeKey(key);
        Editor edit = getEditor(encodeKey);
        edit.putInt(encodeKey, value);
        return true;
	}
	
	public static int readInt(String key, int defaultValue)
	{
		if(key == null) return defaultValue;
		String encodeKey = getEncodeKey(key);
	    SharedPreferences shareP = getPreferences(encodeKey);
	    return shareP.getInt(encodeKey, defaultValue);
	}
	
	public static boolean saveBoolean(boolean value, String key)
	{
		if(key == null) return false;
		String encodeKey = getEncodeKey(key);
        Editor edit = getEditor(encodeKey);
        edit.putBoolean(encodeKey, value);
        return true;
	}
	public static boolean readBoolean(String key, boolean defValue)
	{
		if(key == null) return defValue;
		String encodeKey = getEncodeKey(key);
	    SharedPreferences shareP = getPreferences(encodeKey);
	    return shareP.getBoolean(encodeKey, defValue);
	}
	
	
	public static Object readObject(String key) {  
		Object rsObj = null;  
		String encodeKey = getEncodeKey(key);
	    SharedPreferences shareP = getPreferences(encodeKey);
	    String productBase64 = shareP.getString(encodeKey, null);
	    		
	    if(productBase64 == null) return null;
	    
	    //读取字节  
	    byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.NO_WRAP);  
	      
	    //封装到字节流  
	    ByteArrayInputStream bais = null; 
	    ObjectInputStream bis = null; 
	    try {  
	        //再次封装  
	    	bais = new ByteArrayInputStream(base64); 
	    	bis = new ObjectInputStream(bais);  
	    	rsObj = bis.readObject();  
	    }  catch (Exception e) {  
	         
	    } 
	    finally
	    {
	    	try {
				if(bais != null) 
				{
					bais.close();
					bais = null;
				}
				if(bis != null)
				{
					bis.close();
					bis = null;
				}
				
			} catch (IOException e) {
				
			}
	    }
	    return rsObj;  
	}
	
	private static Editor getEditor(String key)
	{
		return getPreferences(key).edit();
	}
	
	private static SharedPreferences getPreferences(String key)
	{
		SharedPreferences sharedP = WFHttpEnvironment.getContext().getSharedPreferences(key, Activity.MODE_PRIVATE);
		return sharedP;
	}
	
	private static String getEncodeKey(String key)
	{
		return WFHttpTool.MD5("kWF_WFSharePrefencesManager_" + key);
	}
	

}

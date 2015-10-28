package com.wolf.http;

public class WFAsyncHttpUtil {

	public static String convertByteToString(byte[] bt)
	{
		if(bt == null)
		{
			return null;
		}
		return new String(bt);
	}
	
	public static byte[] convertStringToByte(String str)
	{
		if(str == null) return null;
		return str.getBytes();
	}
	
}

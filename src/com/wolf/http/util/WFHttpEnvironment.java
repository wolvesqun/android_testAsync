package com.wolf.http.util;

import android.content.Context;

public class WFHttpEnvironment{

	/*** 公司标识  ***/
	public static final String kWFCompany = "mbalib";
	
	private static Context mContext;
	
	public static void setContext(Context context)
	{
		mContext = context;
	}
	
	public static Context getContext()
	{
		return mContext;
	}

}

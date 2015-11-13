package com.wolf.http.util;

import android.app.Activity;
import android.content.Context;

public class WFHttpEnvironment{

	/*** 公司标识  ***/
	public static final String kWFCompany = "mbalib";
	
	private static Context mContext;
	private static Activity mActivity;
	
	public static Context getContext()
	{
		return mContext;
	}
	
	public static Activity getActivity()
	{
		return mActivity;
	}
	
	public static void setActivity(Activity activity)
	{
		if(mActivity != null) return;
		mActivity = activity;
		mContext = activity.getApplicationContext();
	}

}

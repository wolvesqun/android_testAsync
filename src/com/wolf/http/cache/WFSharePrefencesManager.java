package com.wolf.http.cache;

import java.io.Serializable;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wolf.http.util.WFHttpEnvironment;
import com.wolf.http.util.WFHttpTool;

public class WFSharePrefencesManager {
	
	public boolean save(Object obj, String key)
	{
		if(!(obj instanceof Serializable) || key == null|| key.length() == 0)
		{
			return false;
		}
		key = getEncodeKey(key);
		
		Editor edit = getEditor(key);
		
		return false;
	}
	
	private static Editor getEditor(String key)
	{
		SharedPreferences sharedP = WFHttpEnvironment.getContext().getSharedPreferences(key, Activity.MODE_PRIVATE);
		return sharedP.edit();
	}
	
	
	private static String getEncodeKey(String key)
	{
		return WFHttpTool.MD5("kWF_WFSharePrefencesManager_" + key);
	}

}

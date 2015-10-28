package com.wolf.http;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DownloadManager.Request;

import com.loopj.android.http.RequestParams;
import com.wolf.http.cache.WFHttpCacheManager;


public class WFBaseHttpClient {

	private Map<String, String> headers = new HashMap<String, String>();
	private WFHttpCachePolicy mCachePolicy;
	private String URLString;
	protected WFHttpResponseHandlerInter responseHandler;

	public void handleJSON(byte[] bt, boolean cache)
	{
		String jsonString = WFAsyncHttpUtil.convertByteToString(bt);
		
		try {
			if(responseHandler != null && jsonString != null){
				JSONObject jsonObj = new JSONObject(jsonString);
				responseHandler.onSuccess(jsonObj, cache);
			}
			else {
				handleByte(bt, cache);
			}
			
		}catch (JSONException e) {
			if(responseHandler != null && jsonString != null){
				try {
					JSONArray jsonArray = new JSONArray(jsonString);
					responseHandler.onSuccess(jsonArray, cache);
				} catch (Throwable e1) {
					handleByte(bt, cache);
				}
			}
			else{
				handleByte(bt, cache);
			}
		}
		catch (Throwable e){
			handleByte(bt, cache);
		}
		
	}
	
	public void handleByte(byte[] bt, boolean cache)
	{
		if(responseHandler != null)
		{
			responseHandler.onSuccess(bt, cache);
		}
	}
	
	public void handleFailure(Throwable t)
	{
		if(responseHandler != null)
		{
			responseHandler.onFailure(t);
		}
	}
	
	public void addHeader(String key, String value)
	{
		if(key != null && value != null)
		{
			headers.put(key, value);
		}
		
	}
	
	public void addHeader(Map<String, String> headers)
	{
		if(headers == null) return;
		
		for(Map.Entry<String, String> entry : headers.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			addHeader(key, value);
		}
	}
	
	public void saveCache(String key, byte[] bt)
	{
		if(this.mCachePolicy != WFHttpCachePolicy.WFAsyncCachePolicyType_Default)
		{
			WFHttpCacheManager.save(bt, key);
		}
	}
	
	public boolean handleCache(String URLString, WFHttpCachePolicy cachePolicy)
	{
		if(cachePolicy == WFHttpCachePolicy.WFAsyncCachePolicyType_ReturnCache_DontLoad)
		{
			if(WFHttpCacheManager.isExist(URLString))
			{
				byte[] bt = WFHttpCacheManager.read(URLString);
				handleJSON(bt, true);
				return true;
			}
		}
		else if(cachePolicy == WFHttpCachePolicy.WFAsyncCachePolicyType_ReturnCache_DidLoad)
		{
			if(WFHttpCacheManager.isExist(URLString))
			{
				byte[] bt = WFHttpCacheManager.read(URLString);
				handleJSON(bt, true);
			}
		}
		else if(cachePolicy == WFHttpCachePolicy.WFAsyncCachePolicyType_Reload_IgnoringLocalCache)
		{
			
		}
		return false;
	}
	
	
	public String getURLString() {
		return URLString;
	}

	public void setURLString(String uRLString) {
		URLString = uRLString;
	}

	public WFHttpCachePolicy getPolicy() {
		if(mCachePolicy == null)
		{
			mCachePolicy = WFHttpCachePolicy.WFAsyncCachePolicyType_Default;
		}
		return mCachePolicy;
	}

	public void setPolicy(WFHttpCachePolicy policy) {
		this.mCachePolicy = policy;
	}
}

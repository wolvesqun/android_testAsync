package com.wolf.http;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.wolf.http.cache.WFHttpCacheManager;

/**
 * 请求基类
 * @author wolvesqun
 *
 */
public class WFBaseHttpClient {

	private Map<String, String> mHeaders = new HashMap<String, String>();
	private WFHttpCachePolicy mCachePolicy;
	private String mURLString;
	private CookieStore mCookieStore = null;
	public CookieStore getmCookieStore() {
		return mCookieStore;
	}

	public void setmCookieStore(CookieStore mCookieStore) {
		this.mCookieStore = mCookieStore;
	}

	protected WFHttpResponseHandlerInter mResponseHandlerCallback;

	/**
	 * 处理json请求
	 * @param bt
	 * @param cache
	 */
	public void handleJSON(byte[] bt, boolean cache)
	{
		String jsonString = WFAsyncHttpUtil.convertByteToString(bt);
		
		try {
			if(mResponseHandlerCallback != null && jsonString != null){
				JSONObject jsonObj = new JSONObject(jsonString);
				mResponseHandlerCallback.onSuccess(jsonObj, cache);
			}
			else {
				handleByte(bt, cache);
			}
			
		}catch (JSONException e) {
			if(mResponseHandlerCallback != null && jsonString != null){
				try {
					JSONArray jsonArray = new JSONArray(jsonString);
					mResponseHandlerCallback.onSuccess(jsonArray, cache);
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
		if(mResponseHandlerCallback != null)
		{
			mResponseHandlerCallback.onSuccess(bt, cache);
		}
	}
	
	public void handleFailure(Throwable t)
	{
		if(mResponseHandlerCallback != null)
		{
			mResponseHandlerCallback.onFailure(t);
		}
	}
	
	protected void addHeader(AsyncHttpClient httpClient)
	{
		if(this.mHeaders != null)
		{
			for(Map.Entry<String, String> entry : this.mHeaders.entrySet())
			{
				String key = entry.getKey();
				String value = entry.getValue();
				if(key != null && value != null)
				{
					value = URLDecoder.decode(value);
					value = URLEncoder.encode(value);
					httpClient.addHeader(key, value);
				}
				
			}
		}
	}
	
	protected void addCookieStore(AsyncHttpClient httpClient)
	{
		if(httpClient != null && this.getmCookieStore() != null)
		{
			httpClient.setCookieStore(this.getmCookieStore());
		}
	}
	
	public void addHeader(String key, String value)
	{
		if(key != null && key.length() > 0 && value != null && value.length() > 0)
		{
			mHeaders.put(key, value);
		}
		
	}
	
	public void addHeader(Map<String, String> headers)
	{
		if(headers == null) return;
		
		for(Map.Entry<String, String> entry : headers.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			if(key !=null && value != null)
			{
				addHeader(key, value);
			}
			
			
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
		return mURLString;
	}

	public void setURLString(String uRLString) {
		mURLString = uRLString;
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

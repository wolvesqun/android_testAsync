package com.wolf.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.CookieStore;

import com.loopj.android.http.RequestParams;
import com.wolf.http.util.WFHttpTool;

import android.util.Log;

/**
 * http请求接口
 * @author wolvesqun
 *
 */
public class WFAsyncHttpManager {
	
	private WFAsyncHttpClientResourse httpClientResourse = new WFAsyncHttpClientResourse();
	
	/**
	 * 	GET 请求
	 * @param URLString
	 * @param header 
	 * @param handler callback
	 */
	public static void get(String URLString, Map<String, String> headers, WFHttpCachePolicy cachePolicy, CookieStore cookieStore,final WFHttpResponseHandler handle)
	{
		final WFAsyncHttpClient client = getInstance().getHttpClient();
		client.setPolicy(cachePolicy);
		client.addHeader(headers);
		client.setmCookieStore(cookieStore);
		client.get(URLString, new WFHttpResponseHandler() {

			@Override
			public void onSuccess(byte[] responseByte, boolean cache) {
				handle.onSuccess(responseByte, cache);
				getInstance().releaseHttpClient(client);
			}

			@Override
			public void onSuccess(Object responseJSON, boolean cache) {
				handle.onSuccess(responseJSON, cache);
				getInstance().releaseHttpClient(client);
			}

			@Override
			public void onFailure(Throwable t) {
				handle.onFailure(t);
				getInstance().releaseHttpClient(client);
			}
		});
	}
	public static void get(String URLString, Map<String, String> headers, WFHttpCachePolicy cachePolicy,final WFHttpResponseHandler handle)
	{
		get(URLString, headers, cachePolicy, null, handle);
	}
	
	/**
	 * POST 请求
	 * @param URLString
	 * @param params
	 * @param headers
	 * @param cachePolicy
	 * @param handle
	 */
	public static void post(String URLString, Map<String, String> params, Map<String, String> headers, WFHttpCachePolicy cachePolicy,CookieStore cookieStore, final WFHttpResponseHandler handle)
	{
		final WFAsyncHttpClient client = getInstance().getHttpClient();
		client.setPolicy(cachePolicy);
		client.addHeader(headers);
		client.setmCookieStore(cookieStore);
		client.post(URLString, params, new WFHttpResponseHandler() {

			@Override
			public void onSuccess(byte[] responseByte, boolean cache) {
				handle.onSuccess(responseByte, cache);
				getInstance().releaseHttpClient(client);
			}

			@Override
			public void onSuccess(Object responseJSON, boolean cache) {
				handle.onSuccess(responseJSON, cache);
				getInstance().releaseHttpClient(client);
			}

			@Override
			public void onFailure(Throwable t) {
				handle.onFailure(t);
				getInstance().releaseHttpClient(client);
			}
		});
		
	}
	public static void post(String URLString, Map<String, String> params, Map<String, String> headers, WFHttpCachePolicy cachePolicy, final WFHttpResponseHandler handle)
	{
		post(URLString, params, headers, cachePolicy, null, handle);
	}
	
	private WFAsyncHttpClient getHttpClient()
	{
		WFAsyncHttpClient httpClient = null;
		synchronized(httpClientResourse)
		{
			httpClient = httpClientResourse.getItem();
		}
		return httpClient;
	}
	
	private void releaseHttpClient(WFAsyncHttpClient httpClient)
	{
		synchronized(httpClientResourse)
		{
			httpClientResourse.release(httpClient);
		}
	}
	
	private WFAsyncHttpManager(){}
	
	static WFAsyncHttpManager manager = null;
	private static WFAsyncHttpManager getInstance()
	{
		if(manager == null)
		{
			manager =  new WFAsyncHttpManager();
		}
		return manager;
	}
	
	/**
	 *	WFAsyncHttpClient 资源
	 */
	private class WFAsyncHttpClientResourse 
	{
		private List<WFAsyncHttpClient> freeHttpClientList = new ArrayList<WFAsyncHttpClient>();
		private List<WFAsyncHttpClient> taskHttpClientList = new ArrayList<WFAsyncHttpClient>();
		
		public WFAsyncHttpClientResourse()
		{
			for(int i = 0; i < 10; i ++)
			{
				createItem();
			}
		}
		
		private void createItem()
		{
			WFAsyncHttpClient httpClient = new WFAsyncHttpClient();
			freeHttpClientList.add(httpClient);
		}
		
		public WFAsyncHttpClient getItem()
		{
			if(freeHttpClientList.size() == 0)
			{
				createItem();
			}
			WFAsyncHttpClient httpClient = freeHttpClientList.get(0);
			freeHttpClientList.remove(httpClient);
			taskHttpClientList.add(httpClient);
			Log.i("kWFLog_", "free_空闲**********" + freeHttpClientList.size());
			return httpClient;
		}
		
		public void release(WFAsyncHttpClient httpClient)
		{
			taskHttpClientList.remove(httpClient);
			freeHttpClientList.add(httpClient);
			Log.i("kWFLog_", "task_任务==========" + taskHttpClientList.size());
		}
	}
	
}

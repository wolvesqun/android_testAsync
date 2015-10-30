package com.wolf.testasyncclient;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.wolf.http.WFAsyncHttpManager;
import com.wolf.http.WFHttpCachePolicy;
import com.wolf.http.WFHttpResponseHandler;
import com.wolf.http.cache.WFHttpCacheManager;
import com.wolf.http.util.WFHttpEnvironment;


public class MainActivity extends Activity {

	private String key = "key1fhf.js";
	private String content = "bbbbb";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		WFHttpEnvironment.setContext(this);
		
//		WFHttpCacheManager.save("aaaaa".getBytes(), "asdfasdfad");
		
//		testSave();
//		testRead();
//		testExist();
//		testDelete();
//		testRemove();
//		testRemoveImage();
//		testRemoveWeb();
		
		
		
		
		
//		String URLString = "http://www.mbalib.com/appwiki/article?width=100&offset=0&num=10";
		
//		String URLString = "http://www.dev.mbalib.com/appwiki/test2?pp=********************";
		String URLString = "http://wiki.mbalib.com/client?app=1&action=search&format=json&full=1&term=什么";
		// http://wiki.mbalib.com/client?app=1&action=search&format=json&term=&full=1
		// http://wiki.mbalib.com/client?app=1&action=search&format=json&term=&full=1
		Map<String,String> header = new HashMap<String, String>();
//		header.put("uu", "vv");
		WFAsyncHttpManager.get(URLString, header, WFHttpCachePolicy.WFAsyncCachePolicyType_Default, new WFHttpResponseHandler() {

			@Override
			public void onSuccess(byte[] responseByte, boolean cache) {
				
			}

			@Override
			public void onSuccess(Object responseJSON, boolean cache) {
				
			}

			@Override
			public void onFailure(Throwable t) {
				
			}
		});

		
//		Map<String, String> requestPram = new HashMap<String, String>();
//		requestPram.put("term", "百度");
//		WFAsyncHttpManager.post(URLString, requestPram, null, WFHttpCachePolicy.WFAsyncCachePolicyType_Default, new WFHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(byte[] responseByte, boolean cache) {
//				
//			}
//
//			@Override
//			public void onSuccess(Object responseJSON, boolean cache) {
//				JSONArray jsonArray = (JSONArray) responseJSON;
//				
//				
//				TextView tv = (TextView) findViewById(R.id.textView);
//				if(cache)
//				{
//					tv.setText(jsonArray.toString());
//				}
//				
//			}
//
//			@Override
//			public void onFailure(Throwable t) {
//				
//			}
//			
//		});
	}
	
	public void testSave()
	{
		WFHttpCacheManager.save(content.getBytes(), key);
	}
	
	public void testExist()
	{
		if(WFHttpCacheManager.isExist(key))
		{
//			String str = new String(bt);
			
			TextView tv = (TextView) findViewById(R.id.textView);
			tv.setText("true");
		}
	}
	
	public void testRead()
	{
		byte[] bt = WFHttpCacheManager.read(key);
		if(bt != null)
		{
			String str = new String(bt);
			TextView tv = (TextView) findViewById(R.id.textView);
			tv.setText(str);
		}
	}
	
	public void testRemove()
	{
		WFHttpCacheManager.removeAllCache();
	}
	
	public void testRemoveImage()
	{
		WFHttpCacheManager.removeImageCache();
	}
	
	public void testRemoveWeb()
	{
		WFHttpCacheManager.removeWebCache();
	}
	
	public void testDelete()
	{
		WFHttpCacheManager.deleteCache(key);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

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
		
		
		
		testGET();


	}
	
	public void testGET()
	{
		String URLString = "http://www.weather.com.cn/adat/sk/101010100.html";
		Map<String,String> header = new HashMap<String, String>();
		
		WFAsyncHttpManager.get(URLString, header, WFHttpCachePolicy.WFAsyncCachePolicyType_ReturnCache_DidLoad, new WFHttpResponseHandler() {

			/**
			 * 如果不是json则调用这个方法
			 */
			public void onSuccess(byte[] responseByte, boolean cache) {
				if(cache) // 缓存数据
				{
					
				}
				else 
				{
					
				}
			}

			/**
			 * 如果是json数据，则成功调用这个方法（主线程）
			 */
			public void onSuccess(Object responseJSON, boolean cache) {
				if(cache) // 缓存数据
				{
					
				}
				else 
				{
					
				}
			}

			/**
			 * 网络错误回调
			 */
			public void onFailure(Throwable t) {
				
			}
		});
	}
	
	public void testPOST()
	{
		String URLString = "你的URL";
		Map<String,String> header = new HashMap<String, String>();
		header.put("user-agent", "version---------");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("aa", "bb");
		WFAsyncHttpManager.post(URLString, param, header, WFHttpCachePolicy.WFAsyncCachePolicyType_Default, new WFHttpResponseHandler() {

			@Override
			public void onSuccess(Object responseJSON, boolean cache) {
				
			}

			@Override
			public void onSuccess(byte[] responseByte, boolean cache) {
				
			}

			@Override
			public void onFailure(Throwable t) {
				
			}
		});
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

package com.wolf.testasyncclient;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

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
		
		
		
		
		
		String URLString = "http://www.mbalib.com/appwiki/article?width=100&offset=0&num=10";
		
//		String URLStrig = "http://www.dev.mbalib.com/appwiki/test2";
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("uu", "vv");
//		WFAsyncHttpManager.get(URLStrig, null, null, new WFHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(byte[] responseByte, boolean cache) {
//				Log.i("kWFLog_", new String(responseByte));
//			}
//
//			@Override
//			public void onSuccess(Object responseJSON, boolean cache) {
//				
//			}
//
//			@Override
//			public void onFailure(Throwable t) {
//				
//			}
//		});
		
		WFAsyncHttpManager.post(URLString, null, null, WFHttpCachePolicy.WFAsyncCachePolicyType_ReturnCache_DontLoad, new WFHttpResponseHandler() {

			@Override
			public void onSuccess(byte[] responseByte, boolean cache) {
				
			}

			@Override
			public void onSuccess(Object responseJSON, boolean cache) {
				JSONArray jsonArray = (JSONArray) responseJSON;
				
				
				TextView tv = (TextView) findViewById(R.id.textView);
				if(cache)
				{
					tv.setText(jsonArray.toString());
				}
				
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

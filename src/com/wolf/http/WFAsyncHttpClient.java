package com.wolf.http;

import java.util.Map;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wolf.http.util.WFHttpEnvironment;

public class WFAsyncHttpClient extends WFBaseHttpClient{

	
	
	private AsyncHttpClient httpClient = new AsyncHttpClient();
	
	public void get(final String URLString, WFHttpResponseHandlerInter responsehandler)
	{
		this.responseHandler = responsehandler;
		handleCache(URLString, this.getPolicy());
		
		httpClient.get(URLString, new DataAsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] header, byte[] bt) {
				saveCache(URLString, bt);
				handleJSON(bt, false);
				cancer();
			}
			
			@Override
			public void onFailure(int arg0, Header[] header, byte[] bt, Throwable th) {
				handleFailure(th);
				cancer();
			}
		});
	}
	
	public void post(String URLString, RequestParams params, Map<String, String>headers, WFHttpResponseHandler handle)
	{
		this.responseHandler = handle;
		
		handleCache(URLString, this.getPolicy());
		httpClient.post(URLString, params, new DataAsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] header, byte[] bt) {
				handleJSON(bt, false);
				cancer();
			}
			
			@Override
			public void onFailure(int arg0, Header[] header, byte[] bt, Throwable th) {
				handleFailure(th);
				cancer();
			}
		});
		
	}

	public void cancer()
	{
		this.responseHandler = null;
		if(WFHttpEnvironment.getContext() != null)
		{
			this.httpClient.cancelRequests(WFHttpEnvironment.getContext(), true);
		}
		
	}
	
}

package com.wolf.http;

import java.util.Map;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.wolf.http.util.WFHttpEnvironment;
import com.wolf.http.util.WFHttpTool;

/**
 * 异步请求Client （GET | POST）
 * @author wolvesqun
 *
 */
public class WFAsyncHttpClient extends WFBaseHttpClient{

	
	
	private AsyncHttpClient httpClient = new AsyncHttpClient();
	
	/**
	 * GET 请求
	 * @param URLString 请求字符串
	 * @param responsehandler 回调
	 */
	public void get(final String URLString, WFHttpResponseHandlerInter responseCallback)
	{
		this.mResponseHandlerCallback = responseCallback;
		this.setURLString(URLString);
		if(handleCache(URLString, this.getPolicy())) return;
		setHeader(httpClient);
		httpClient.get(URLString, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int rsCode, Header[] header, String result) {
				saveCache(URLString, result.getBytes());
				handleJSON(result.getBytes(), false);
				cancer();
			}
			
			@Override
			public void onFailure(int rsCode, Header[] header, String result, Throwable t) {
				handleFailure(t);
				cancer();
			}
		});
//		httpClient.get(URLString, new DataAsyncHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(int arg0, Header[] header, byte[] bt) {
//				saveCache(URLString, bt);
//				handleJSON(bt, false);
//				cancer();
//			}
//			
//			@Override
//			public void onFailure(int arg0, Header[] header, byte[] bt, Throwable th) {
//				handleFailure(th);
//				cancer();
//			}
//		});
	}
	
	/**
	 * post 请求
	 * @param URLString
	 * @param params
	 * @param headers
	 * @param handle
	 */
	public void post(final String URLString, Map<String, String> params, WFHttpResponseHandler handle)
	{
		this.mResponseHandlerCallback = handle;
		this.setURLString(URLString);
		if(handleCache(URLString, this.getPolicy())) return;
		setHeader(httpClient);
		httpClient.post(URLString, WFHttpTool.convertMapToRequestParam(params), new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int rsCode, Header[] header, String rs) {
				saveCache(URLString, rs.getBytes());
				handleJSON(rs.getBytes(), false);
				cancer();
			}
			
			@Override
			public void onFailure(int rsCode, Header[] header, String rs, Throwable t) {
				handleFailure(t);
				cancer();
			}
		});
//		httpClient.post(URLString, WFHttpTool.convertMapToRequestParam(params), new DataAsyncHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(int arg0, Header[] header, byte[] bt) {
//				handleJSON(bt, false);
//				cancer();
//			}
//			
//			@Override
//			public void onFailure(int arg0, Header[] header, byte[] bt, Throwable th) {
//				handleFailure(th);
//				cancer();
//			}
//		});
		
	}

	public void cancer()
	{
		this.mResponseHandlerCallback = null;
		if(WFHttpEnvironment.getContext() != null)
		{
			this.httpClient.cancelRequests(WFHttpEnvironment.getContext(), true);
		}
		
	}
	
}

package com.wolf.http;

import org.json.JSONObject;

public abstract class WFHttpResponseHandler implements WFHttpResponseHandlerInter{

	/**
	 *  The method will be call by youself, and the framework didn't invoke
	 */
	public void onSuccess(){}
	
	/**
	 *  The method will be call by youself, and the framework didn't invoke
	 */
	public void onFailure(){}
	
	/**
	 * 	method will be call when the data type is json
	 */
	public void onSuccess(Object responseJSON,  boolean cache){}
	
	/**
	 * method will be call when the data type is not json
	 */
	public void onSuccess(byte[] responseByte, boolean cache){}
	
	/**
	 * 
	 */
	public void onFailure(Throwable t){}
}

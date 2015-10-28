package com.wolf.http;

import org.json.JSONObject;

public abstract class WFHttpResponseHandler implements WFHttpResponseHandlerInter{

	public void onSuccess(){}
	public void onSuccess(byte[] responseByte, boolean cache){}
	public void onSuccess(Object responseJSON,  boolean cache){}
	public void onFailure(Throwable t){}
}

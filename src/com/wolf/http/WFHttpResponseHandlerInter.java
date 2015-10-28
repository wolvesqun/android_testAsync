package com.wolf.http;


public interface WFHttpResponseHandlerInter {

	public void onSuccess(byte[] responseByte, boolean cache);
	public void onSuccess(Object responseJSON,  boolean cache);

	
	public void onFailure(Throwable t);
	
}

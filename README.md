# android_testAsync
 
   注意：本框架是有做缓存策略的，支持四种缓存
   
	 1. WFAsyncCachePolicyType_Default,                     // *** 不提供缓存
	 2. WFAsyncCachePolicyType_ReturnCache_DontLoad,        // *** 返回缓存
	 3. WFAsyncCachePolicyType_ReturnCache_DidLoad,         // *** 返回缓存并且加载
	 4. WFAsyncCachePolicyType_Reload_IgnoringLocalCache,   // *** 忽略本地缓存并加载 （使用在更新缓存）
  
  
一：使用教程：

    1. 添加框架， android-async-http-1.4.5.jar
    2. 设置 Context : 在MainActivity设置	WFHttpEnvironment.setActivity(this);
    3  引入文件，import com.wolf.http.WFAsyncHttpManager;
    
二：请求
  
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

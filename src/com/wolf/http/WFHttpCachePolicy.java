package com.wolf.http;

public enum WFHttpCachePolicy {
    WFAsyncCachePolicyType_Default,                     // *** 不提供缓存
    WFAsyncCachePolicyType_ReturnCache_DontLoad,        // *** 返回缓存
    WFAsyncCachePolicyType_ReturnCache_DidLoad,         // *** 返回缓存并且加载
    WFAsyncCachePolicyType_Reload_IgnoringLocalCache,   // *** 忽略本地缓存并加载 （使用在更新缓存）
}

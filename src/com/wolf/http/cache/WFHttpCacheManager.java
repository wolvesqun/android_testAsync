package com.wolf.http.cache;

import com.wolf.http.util.WFFileManager;
import com.wolf.http.util.WFHttpTool;

public class WFHttpCacheManager {

	public static boolean saveObject(Object obj, String key)
	{
		if(obj == null || key == null || key.length() == 0) return false;
		return WFFileManager.saveObject(obj, key, getBaseFolder(key));
	}
	
	/**
	 * 保存数据
	 * @param bt -》数据
	 * @param key 
	 * @return
	 */
	public static boolean save(byte[] bt, String key)
	{
		if(bt == null || key == null || key.length() == 0) return false;
		return WFFileManager.save(bt, key, getBaseFolder(key));
	}
	
	/**
	 * 删除指定类型所有缓存（删除文件）
	 * @param type
	 */
	private static void removeCache(WFHttpCacheFolderType type)
	{
		String baseDir = getBaseFolder(type);
		WFFileManager.deleteFolder(baseDir);
	}
	
	/**
	 * 删除指定key对应缓存
	 * @param key
	 * @return
	 */
	public static boolean deleteCache(String key)
	{
		if(key == null || key.length() == 0) return false;
		return WFFileManager.deleteFile(key, getBaseFolder(key));
	}
	
	/**
	 * 删除所有缓存
	 */
	public static void  removeAllCache()
	{
		removeCache(WFHttpCacheFolderType.WFHttpCacheFolderType_Base);
	}
	
	/**
	 * 删除所有图片缓存
	 */
	public static void  removeImageCache()
	{
		removeCache(WFHttpCacheFolderType.WFHttpCacheFolderType_Image);
	}
	
	public static void removeDefaultCache()
	{
		removeCache(WFHttpCacheFolderType.WFHttpCacheFolderType_Default);
	}
	
	/**
	 * 删除所有网页文件缓存
	 */
	public static void removeWebCache()
	{
		removeCache(WFHttpCacheFolderType.WFHttpCacheFolderType_Web);
	}
	
	public static byte[] read(String key)
	{
		if(key == null || key.length() == 0) return null;
		return WFFileManager.read(key, getBaseFolder(key));
	}
	
	public static Object readObject(String key)
	{
		if(key == null || key.length() == 0) return null;
		
		return WFFileManager.readObject(key, getBaseFolder(key));
	}
	
	public static boolean isExist(String key)
	{
		if(key == null || key.length() == 0) return false;
		return WFFileManager.isExist(key, getBaseFolder(key));
	}
	
	/**
	 * 根据类型获取文件夹路径
	 * @param type
	 * @return
	 */
	public static String getBaseFolder(WFHttpCacheFolderType type)
	{
		String baseDir = null;
		
		 
		baseDir = "WFAsyncHttpCacheFolder";
		
		if(type == WFHttpCacheFolderType.WFHttpCacheFolderType_Web)
		{
			baseDir = baseDir + "/Web";
		}
		else if(type == WFHttpCacheFolderType.WFHttpCacheFolderType_Image)
		{
			baseDir = baseDir + "/Image";
		}
		else if(type == WFHttpCacheFolderType.WFHttpCacheFolderType_Base)
		{
			
		} 
		else
		{
			baseDir = baseDir + "/Default";
		}
		return baseDir;
	}
	
	public static String getBaseFolder(String key)
	{
		if(WFHttpTool.isImageRequest(key))
		{
			return getBaseFolder(WFHttpCacheFolderType.WFHttpCacheFolderType_Image);
		}
		if(WFHttpTool.isWebFileRequest(key))
		{
			return getBaseFolder(WFHttpCacheFolderType.WFHttpCacheFolderType_Web);
		}
		else
		{
			return getBaseFolder(WFHttpCacheFolderType.WFHttpCacheFolderType_Default);
		}
	}
	
	private enum WFHttpCacheFolderType
	{
		WFHttpCacheFolderType_Base,
		WFHttpCacheFolderType_Default,
		WFHttpCacheFolderType_Image,
		WFHttpCacheFolderType_Web,
	}
}

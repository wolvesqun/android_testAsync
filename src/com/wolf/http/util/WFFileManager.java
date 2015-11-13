package com.wolf.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Base64;


/**
 * 	文件管理器
 * @author wolvesqun
 *
 */
public class WFFileManager {
	
	/**
	 * 保存对象
	 * @param obj
	 * @param key
	 * @param folder
	 * @return
	 */
	public static boolean saveObject(Object obj, String key, String folder)
	{
		boolean rs = false;
		
		if(obj == null || key == null || key.length() == 0 || folder == null || folder.length() == 0) return rs;
		if(!(obj instanceof Serializable)) return false;
				
		String filePath = getFilePath(key, folder);
		
		
		FileOutputStream fos = null;
		ObjectOutputStream os = null;
		try {
			if(WFFileManager.isExist(filePath))
			{
				deleteFile(filePath);
			}
			if(WFFileManager.createFile(filePath))
			{
				fos = new FileOutputStream(filePath);
				os = new ObjectOutputStream(fos);
				os.writeObject(obj);
				os.close();
				rs = true;
			}
		} catch (Throwable e){
			
		}
		finally
		{
			try {
				if(fos != null)
				{
					fos.close();
				}
				if(os != null) os.close();
			} catch (Throwable e) {
			
			}
		}
		return rs;
	}
	
	/**
	 * 保存
	 * @param bt
	 * @param key
	 * @param folder
	 * @return
	 */
	public static boolean save(byte[] bt, String key, String folder)
	{
		boolean rs = false;
		
		if(bt == null || key == null || key.length() == 0 || folder == null || folder.length() == 0) return rs;
				
		String filePath = getFilePath(key, folder);
		
		FileOutputStream fos = null;
		try {
			if(WFFileManager.isExist(filePath))
			{
				deleteFile(filePath);
			}
			if(WFFileManager.createFile(filePath))
			{
				fos = new FileOutputStream(filePath);
				fos.write(Base64.encode(bt, Base64.NO_WRAP));
				rs = true;
			}
		} catch (Throwable e){
			
		}
		finally
		{
			try {
				if(fos != null)
				{
					fos.close();
				}
			} catch (Throwable e) {
			
			}
		}
		return rs;
	}
	
	
	/**
	 * 删除单个文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String key, String folder)
	{
		boolean sdRs = deleteFile(getSDFilePath(key, folder));
		boolean phoneRs = deleteFile(getPhoneFilePath(key, folder));
		if(sdRs || phoneRs)
		{
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 删除目录
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFolder(String folder)
	{
		return deleteDirectory(getBaseFolder(folder));
	}
	
	public static Object readObject(String key, String folder)
	{
		Object obj = null;
		if((obj = readObject(getSDFilePath(key, folder))) == null)
		{
			obj = readObject(getPhoneFilePath(key, folder));
		}
		return obj;
	}
	
	public static byte[] read(String key, String folder)
	{
		byte[] bt = null;		
		if((bt = read(getSDFilePath(key, folder))) == null)
		{
			bt = read(getPhoneFilePath(key, folder));
		}
		return bt;
	}
	
	public static boolean isExist(String key, String folder)
	{
		if(isExist(getSDFilePath(key, folder)) || isExist(getPhoneFilePath(key, folder)))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 根据指定的路径创建文件夹
	 * 
	 * @param filePath 文件夹路径
	 * @return
	 */
	private static boolean createDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return file.mkdirs();
	}
	
	private static boolean createFile(String filePath)
	{
		boolean rs = false;
		File file = new File(filePath);
		if(file.exists())
		{
			rs =  true;
		}
		else
		{
			int end = filePath.lastIndexOf("/");
			String folderPath = filePath.substring(0, end);
			if(createDirectory(folderPath))
			{
				try {
					rs = file.createNewFile();
				} catch (Exception e) {
					
				}
			}
			
		}
		return rs;
	}
	
	
	/**
	 * 根据类型获取文件夹路径
	 * @param type
	 * @return
	 */
	private static String getBaseFolder(String folder)
	{
		if(isSdcardReady())
		{
			return getSDBaseFolder(folder);
		}
		else
		{
			return getPhoneBaseFolder(folder);
		}
		
	}
	
	private static String getSDBaseFolder(String folder)
	{
		String baseDir = Environment.getExternalStorageDirectory().getPath();
		baseDir = baseDir + "/"  + WFHttpEnvironment.kWFCompany + "_" + getAppName() + "/" + folder;
		return baseDir;
	}
	
	private static String getPhoneBaseFolder(String folder)
	{
		String baseDir = Environment.getDataDirectory().getPath();
		baseDir = baseDir + "/" + WFHttpEnvironment.kWFCompany + "_" + getAppName() + "/" + folder;
		return baseDir;
	}
	
    /**
	 * 获取项目名
	 * @return
	 */
	private static String getAppName()
	{
		PackageManager pm = WFHttpEnvironment.getContext().getPackageManager();
		String appName = WFHttpEnvironment.getContext().getApplicationInfo().loadLabel(pm).toString();
		return appName;
	} 
	
	private static String getFilePath(String key, String folder)
	{
		String filePath = getBaseFolder(folder);
		return filePath + "/" + WFHttpTool.MD5(key);
	}
	
	private static String getSDFilePath(String key, String folder)
	{
		String filePath = getBaseFolder(folder);
		return filePath + "/" + WFHttpTool.MD5(key);
	}
	
	private static String getPhoneFilePath(String key, String folder)
	{
		String filePath = getBaseFolder(folder);
		return filePath + "/" + WFHttpTool.MD5(key);
	}

	
	
	
	
	private static byte[] read(String filePath)
	{
		byte[] bt = null;
		FileInputStream is = null;
		try {
			is = new FileInputStream(filePath);
			bt = WFHttpTool.readStream(is);
			if(bt != null)
			{
				bt = Base64.decode(bt, Base64.NO_WRAP);
			}
		} catch (Throwable e) {
			
		}
		return bt;
	}
	
	private static Object readObject(String filepath)
	{
		Object obj = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			ois = new ObjectInputStream(fis);
			obj = ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				if(fis != null) fis.close();
				if(ois != null) ois.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
		return obj;
	}
	
	/**
	 * 删除目录
	 * @param filePath
	 * @return
	 */
	private static boolean deleteDirectory(String forderPath)
	{
		File file = new File(forderPath);
		if(!file.exists() || !file.isDirectory())
		{
			return false;
		}
		
		boolean rs = true;
		
		File[] fileList = file.listFiles();
		for(int i = 0; i < fileList.length; i ++)
		{
			File childFile = fileList[i];
			if(childFile.isFile())
			{
				rs = deleteFile(childFile.getAbsolutePath());
				if(!rs) break;
			}
			else
			{
				rs = deleteDirectory(childFile.getAbsolutePath());
				if(!rs) break;
			}
		}
		
		if(!rs) return false;
		
		if(file.delete())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除单个文件
	 * @param filePath
	 * @return
	 */
	private static boolean deleteFile(String filePath)
	{
		File file = new File(filePath);
		if(file.exists())
		{
			return file.delete();
		}
		return false;
	}
	
	/** 
     * 检查SD卡是否存在 
     * @return 存在返回true，否则返回false 
     */  
    private static boolean isSdcardReady(){  
        boolean sdExixt = Environment.getExternalStorageState().equals(  
                    android.os.Environment.MEDIA_MOUNTED);  
        return sdExixt;  
    } 
    
    private static boolean isExist(String filePath)
	{
		File file = new File(filePath);
		return file.exists();
	}
	
}

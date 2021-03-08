package com.crm.utils;

import java.util.UUID;

/**
 * 这是文件上传的工具类
 * @author thinkpad
 *
 */
public class UploadUtils {
	/**
	 * 解决目录下文件重名的问题
	 * @param fileName
	 * @return
	 */
	public static String getUuidFileName(String fileName) {
		int index=fileName.lastIndexOf("."); //aa.txt
		String extions = fileName.substring(index);   //得到.txt
		return UUID.randomUUID().toString().replace("-", "")+extions;
	}
	/**
	 * 目录分离的方法
	 * @param uuidFileName
	 * @return
	 */
	public static String getPath(String uuidFileName) {
		//这里得到的是16*16=256个目录
		int code1=uuidFileName.hashCode();
		int d1=code1 & 0xf;  //作为一级目录，0xf代表16进制中的15  0111----7 作为1级目录
		int code2=code1 >>> 4;
		int d2=code2 & 0xf;  //作为二级目录   0011----3作为2级目录
		return "/"+d1+"/"+d2;
	}
}

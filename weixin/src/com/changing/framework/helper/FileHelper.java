/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：FileHelper.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-10-28 下午03:59:43
 *@完成时间：2011-10-28 下午03:59:43
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Administrator <p> 功能描述: <p> 使用示例： <p>
 */
public class FileHelper {

	/**
	 * 删除文件夹及文件夹下的文件 delFile("D:\\新建文本文档\\");
	 * 
	 * @param filePath
	 *        文件或者文件夹路径
	 */
	public static boolean delFile(String filePath) {
		File d = new File(filePath);
		if (d.isDirectory()) {
			File f[] = d.listFiles();
			for (int i = 0; i < f.length; i++) {
				if (f[i].isFile()) {
					f[i].delete();
				}
				if (f[i].isDirectory()) {
					delFile(f[i].getPath());
					f[i].delete();

				}
			}
		} else {
			d.delete();
		}
		return d.delete();
	}

	/**
	 * 获得文件名称
	 * 
	 * @param filePath
	 * @return
	 */

	public static String getFileName(String filePath) {
		File f = new File(filePath);
		return f.getName();
	}

	/**
	 * 获得扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getExpandName(String filePath) {
		File f = new File(filePath);
		return f.getName().substring(f.getName().lastIndexOf(".") + 1);
	}

	/**
	 * 移动文件位置
	 * 
	 * @param oldFilePath
	 * @param newFilePath
	 * @return
	 */
	public static boolean moveFile(String oldFilePath, String newFilePath) {
		File oldFile = new File(oldFilePath);
		if (oldFile.exists()) {
			File newFile = new File(newFilePath);
			return oldFile.renameTo(newFile);
		} else {
			LogHelper.logError("文件不存在，无法移动！" + oldFilePath);
			return false;
		}

	}

	/**
	 * 判断文件目录是否存在
	 */
	public static boolean directoryIsExists(String directoryPath) {
		File tempDirectory = new File(directoryPath);
		return tempDirectory.exists();
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean fileIsExists(String filePath) {
		File tempFile = new File(filePath);
		return tempFile.exists();
	}

	/**
	 * 判断文件夹是否存在 不存在则创建
	 * 
	 * @param directoryPath
	 * @return
	 */
	public static boolean creatDirectory(String directoryPath) {
		File tempFile = new File(directoryPath);
		if (tempFile.exists()) {
			return true;
		} else {
			return tempFile.mkdirs();
		}
	}
	/**
	 * 根据文件地址，生成该文件所在目录
	 * @param filePath 文件地址
	 * @return
	 */
	public static boolean creatFileParentDirectory(String filePath) {
		File tempFile = new File(filePath);
		tempFile=tempFile.getParentFile();
		if (tempFile.exists()) {
			return true;
		} else {
			return tempFile.mkdirs();
		}
	}
	/**
	 * 如果文件不存在 则创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			return true;
		} else {
			return file.createNewFile();
		}
	}

	/**
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		return file.delete();
	}

	public ArrayList readDirectory(String DirectoryPath) {
		ArrayList arryaList = new ArrayList();
		File d = new File(DirectoryPath);
		if (d.isDirectory()) {
			File f[] = d.listFiles();
			for (int i = 0; i < f.length; i++) {
				if (f[i].isFile()) {
					arryaList.add(f[i].getName());
				}
				if (f[i].isDirectory()) {
					arryaList.addAll(readDirectory(f[i].getPath()));
				}
			}
		} else {
			System.out.println("请输入文件夹名称！");
		}
		for (int i = 0; i < arryaList.size(); i++) {
			System.out.println(arryaList.get(i));
		}
		return arryaList;
	}

	public static boolean copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			} else {
				LogHelper.logError("文件不存在！" + oldPath);
				//System.out.println("文件不存在！" + oldPath );
				return false;
			}
		} catch (Exception e) {
			LogHelper.logError("文件复制失败！原文件地址：" + oldPath + "  目标文件地址为：" + newPath);
			//System.out.println("文件复制失败！原文件地址：" + oldPath + "  目标文件地址为："+ newPath);
			return false;
		}
		return true;

	}

	public static void main(String s[]) {
		//System.out.println(getExpandName("dsdfsdf.docx"));
		/*
		 * FileHelper fileHelper=new FileHelper(); fileHelper.moveFile("C:\360Downloads\1.txt", "C:\360Downloads\temp\1.txt");
		 */
		String oldFilePath = "C:\\360Downloads" + File.separator + "1.txt";
		String newFilePath = "C:\\360Downloads\\temp\\1.txt";
		File oldFile = new File(oldFilePath);
		System.out.println(oldFile.exists());
		System.out.println(File.separator);
	}
}

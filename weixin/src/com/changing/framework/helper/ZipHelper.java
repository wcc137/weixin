package com.changing.framework.helper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipHelper {
	private static int BUF_SIZE = 2048;
	private static String ZIP_ENCODEING = "GBK";

	public ZipHelper() {
		this(1024 * 10);
	}

	public ZipHelper(int bufSize) {
		BUF_SIZE = bufSize;
	}

	/**
	 * ?缂╂浠舵?浠跺す
	 * 
	 * @param zipFileName  ?缂╂浠跺板锛????浠跺绉版╁??
	 * @param inputFile  ?瑕?缂╃婧?浠跺す
	 * @throws Exception
	 */
	public void zip(String zipFileName, String inputFile) throws Exception {
		zip(zipFileName, new File(inputFile));
	}

	/**
	 * ?缂╂浠跺す
	 * 
	 * @param zipFileName ?缂╁?浠惰矾寰锛????浠跺绉板?╁??
	 * @param inputFile ?瑕?缂╃?浠跺す
	 * @throws Exception
	 */
	public void zip(String zipFileName, File inputFile) throws Exception {
		// ???瀹?缂╂浠跺锛榛璁や负"ZipFile"
		if (zipFileName == null || zipFileName.equals(""))
			zipFileName = "ZipFile";

		// 娣诲".zip"?缂
		if (!zipFileName.endsWith(".zip"))
			zipFileName += ".zip";

		// ?寤烘浠跺す
		String path = Pattern.compile("[\\/]").matcher(zipFileName).replaceAll(
				File.separator);
		int endIndex = path.lastIndexOf(File.separator);
		path = path.substring(0, endIndex);
		File f = new File(path);
		f.mkdirs();
		// 寮濮?缂?
		{
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFileName)));
			zos.setEncoding(ZIP_ENCODEING);
			compress(zos, inputFile, "");
			//System.out.println("zip done");
			zos.close();
		}
	}
	

	/**
	 * 瑙ｅ缂?ip?缂╂浠跺版瀹??褰
	 * 
	 * @param unZipFileName
	 * @param outputDirectory
	 * @throws Exception
	 */
	public void unZip(String unZipFileName, String outputDirectory)
			throws Exception {
		// ?寤鸿??烘浠跺す瀵硅薄
		File outDirFile = new File(outputDirectory);
		outDirFile.mkdirs();
		// ?寮?缂╂浠舵浠跺す
		ZipFile zipFile = new ZipFile(unZipFileName, ZIP_ENCODEING);
		for (Enumeration entries = zipFile.getEntries(); entries
				.hasMoreElements();) {
			ZipEntry ze = (ZipEntry) entries.nextElement();
			File file = new File(outDirFile, ze.getName());
			if (ze.isDirectory()) {// ????褰锛??寤轰?
				file.mkdirs();
				//System.out.println("mkdir " + file.getAbsolutePath());
			} else {
				File parent = file.getParentFile();
				if (parent != null && !parent.exists()) {
					parent.mkdirs();
				}
				//System.out.println("unziping " + ze.getName());
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				InputStream is = zipFile.getInputStream(ze);
				this.inStream2outStream(is, fos);
				fos.close();
				is.close();
			}
		}
		zipFile.close();
	}

	/**
	 * ?缂╀?涓??浠跺す?板凡缁?寮?zip杈?烘? <b>涓寤鸿???存ヨ??ㄨ?ユ规?</b>
	 * 
	 * @param zos
	 * @param f
	 * @param fileName
	 * @throws Exception
	 */
	public void compress(ZipOutputStream zos, File f, String fileName)
			throws Exception {
		//System.out.println("Zipping " + f.getName());
		if (f.isDirectory()) {
			// ?缂╂浠跺す
			File[] fl = f.listFiles();
			if (!fileName.equals("")) {
				zos.putNextEntry(new ZipEntry(fileName + "/"));
			}
			fileName = fileName.length() == 0 ? "" : fileName + "/";
			for (int i = 0; i < fl.length; i++) {
				compress(zos, fl[i], fileName + fl[i].getName());
			}
		} else {
			// ?缂╂浠?
			zos.putNextEntry(new ZipEntry(fileName));
			FileInputStream fis = new FileInputStream(f);
			this.inStream2outStream(fis, zos);
			fis.close();
			zos.closeEntry();
		}
	}

	private void inStream2outStream(InputStream is, OutputStream os)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		int bytesRead = 0;
		for (byte[] buffer = new byte[BUF_SIZE]; ((bytesRead = bis.read(buffer)) != -1);) {
			bos.write(buffer, 0, bytesRead);
		}
	}

	public static void main(String arg[]) {
		ZipHelper t = new ZipHelper();
		try {
			// ?缂╂浠?绗?涓涓?瀛娈垫?锛瑙ｅ缂╄矾寰\\?缂╁?浠剁?瀛,绗?浜涓?瀛娈佃?瑙ｅ??浠惰矾寰
			t.zip("c:\\NEW\\new.zip", "C:\\360Downloads\\?板缓 ????妗?txt");
			// 瑙ｅ缂╂浠?绗?涓涓?瀛娈碉?瑕瑙ｅ???浠剁?板锛绗?浜涓?瀛娈碉?瑙ｅ??浠跺??剧浣缃?>
			// t.unZip("c:\\NEW\\new.zip", "c:\\ds");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

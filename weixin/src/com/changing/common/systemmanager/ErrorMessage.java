package com.changing.common.systemmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class ErrorMessage
{

    private static int intKeyCount = 0;
    private static String[] strErrorMessageList = null;
    private static String[] strKeyList = null;
    private static boolean blnHasInit = false;
    private static String path = null;
	/**
	 * 初始化 
	 *
	 */
    public static void inits () throws Exception
    {
        if(!blnHasInit)
        {
            blnHasInit = true;
            try
            {
                Properties p = new Properties();

                File fileErr = new File(path);
                	
                if(!fileErr.exists())
                {
                    System.out.println("错误：文件：" + fileErr.getAbsolutePath() + " 没有被找到");
                }

                if(fileErr.exists())
                {

                    FileInputStream is = new FileInputStream(fileErr);
                    p.load(is);
                    is.close();

                    Enumeration enuKey = p.propertyNames();
                    Vector vctKey = new Vector();
                    while(enuKey.hasMoreElements())
                    {
                        vctKey.add(enuKey.nextElement());
                    }

                    intKeyCount = vctKey.size();
                    strKeyList = new String[intKeyCount];
                    strErrorMessageList = new String[intKeyCount];

                    for(int i = 0; i < intKeyCount; i++)
                    {
                        String strKey = (String)vctKey.elementAt(i);
                        strKeyList[i] = strKey;
                        strErrorMessageList[i] = p.getProperty(strKey);

                        int index = strErrorMessageList[i].indexOf("#");
                        if(index != -1)
                        {
                            strErrorMessageList[i] = strErrorMessageList[i].
                                substring(0, index).trim();
                        }
                    }
                }
            } catch(Exception e)
            {
                blnHasInit = false;
                throw new Exception("ErrorMessage.properties 配置文件初始化失败"+e.getMessage());
            }
        }
    }
    
	/**
	 * 重新刷新
	 */
	public static void reInits () throws Exception{
		blnHasInit = false;
		inits();
	}

    /**
     * 返回错误代码对应的错误信息
     * @param strErrorCode 错误代码
     * @return 错误信息
     */
    public static String getErrorMessage(String strErrorCode)
    {
        String strErrorMessage = null;
        if(intKeyCount > 0)
        {
            for(int i = 0; i < intKeyCount; i++)
            {
                if(strErrorCode.equalsIgnoreCase(strKeyList[i].trim()))
                {
                	strErrorMessage = UnicodeToUTF8(strErrorMessageList[i]);
                    return strErrorMessage;
                }
            }
        }
        return strErrorMessage;
    }

    /**
     * 返回错误代码对应的错误信息
     * 一般的错误代码定义如下：
     * E001 = [?]编码重复，?号由sParams内容取代
     * @param strErrorCode 错误代码
     * @param sParams 错误信息
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String[] sParams)
    {
        String strErrorMessage = getErrorMessage(strErrorCode);
        StringBuffer sbErrorMsg = new StringBuffer();

        if(strErrorMessage == null)
        {
            return null;
        }

        StringTokenizer st = new StringTokenizer(strErrorMessage, "?");

        if(sParams == null || st.countTokens() - 1 != sParams.length)
        {
            return strErrorMessage;
        }

        //将其中的?号替换对应的sParams
        for(int i = 0; i < sParams.length; i++)
        {
            sbErrorMsg.append(st.nextToken()).append(sParams[i]);
        }
        sbErrorMsg.append(st.nextToken());

        return sbErrorMsg.toString();
    }

    /**
     * 返回错误代码对应的错误信息
     * 一般的错误代码定义如下：
     * E001 = [?]编码重复，?号由sParams内容取代
     * @param strErrorCode 错误代码
     * @param sParams 错误信息
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String sParam)
    {
        String[] sParams = new String[1];
        sParams[0] = sParam;

        return getErrorMessage(strErrorCode, sParams);
    }

    /**
     * 返回错误代码对应的错误信息
     * 一般的错误代码定义如下：
     * E001 = [?]编码重复，?号由sParams内容取代
     * @param strErrorCode 错误代码
     * @param sParams 错误信息
     * @return
     */
    public static String getErrorMessage(String strErrorCode, String sParam1, String sParam2)
    {
        String[] sParams = new String[2];
        sParams[0] = sParam1;
        sParams[1] = sParam2;

        return getErrorMessage(strErrorCode, sParams);
    }
    private static String UnicodeToUTF8(String s)
    {
        try
        {
            if(s == null || s.equals(""))
            {
                return "";
            }
            String newstring = null;
            newstring = new String(s.getBytes("ISO8859_1"), "UTF-8");
            return newstring;
        } catch(UnsupportedEncodingException e)
        {
            return s;
        }
    }
    public static void main(String[] args) throws Exception
    {
       
        
    }

	public static boolean isBlnHasInit() {
		return blnHasInit;
	}

	public static void setBlnHasInit(boolean blnHasInit) {
		ErrorMessage.blnHasInit = blnHasInit;
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		ErrorMessage.path = path;
	}
}

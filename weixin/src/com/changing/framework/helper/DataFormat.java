package com.changing.framework.helper;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;

public class DataFormat
{

    private static DataFormat obj_DataFormat = null;

    private DataFormat()
    {

    }

    /**
     * <p>在lValue前用0补齐，长度由nWidth指定</p>
     * @param
     *   lValue : 需要进行格式化的long型对象
     * @param
     *   nWidth : 指定的补齐长度
     * @return
     *   返回补齐后的数值字符串对象
     */
    public static String formatLong(long lValue, int nWidth)
    {
        String strReturn = "" + lValue;
        int initLength = strReturn.length();
        for(int i = nWidth; i > initLength; i--)
        {
            strReturn = "0" + strReturn;
        }
        return strReturn;
    }

    public static String formatInt(int lValue, int nWidth)
    {
        String strReturn = "" + lValue;
        int initLength = strReturn.length();
        for(int i = nWidth; i > initLength; i--)
        {
            strReturn = "0" + strReturn;
        }
        return strReturn;
    }


    /**
     * <p>将一个long型对象前面补“0”且当bZeroPreffixed为“true”时进行格式金额化</p>
     * <p>例如：formatInt（22,5,true） --> 00,022</p>
     * @param
     *   lValue 需要进行格式化的long型对象.
     * @param
     *   nWidth ：补充的长度
     * @param
     *   bZeroPreffixed 是否进行金额格式化（true：进行格式化；false：不进行格式化），默认格式化位数为3位.
     * @return
     *   返回补齐后的数值字符串对象
     */
    public static String formatInt(long lValue, int nWidth,
                                   boolean bZeroPreffixed)
    {
        return formatInt(lValue, nWidth, bZeroPreffixed, 3);
    }

    /**
     * <p>功能同上，只是对于格式化位数做了特殊指定.</p>
     * @param lValue 需要进行格式化的long型对象.
     * @param nWidth ：补充的长度
     * @param bZeroPreffixed 是否进行金额格式化（true：进行格式化；false：不进行格式化）
     * @param nGroupWidth 指定格式化的位数.
     * @return
     *   返回补齐后的数值字符串对象
     */
    public static String formatInt(long lValue, int nWidth,
                                   boolean bZeroPreffixed, int nGroupWidth)
    {
        StringBuffer sbPattern = new StringBuffer();
        char chStub = bZeroPreffixed ? '0' : '#';
        for(int i = 0; i < nWidth; i++)
        {
            sbPattern.insert(0, chStub);
            if(nGroupWidth > 0 && i > 0 && (i % nGroupWidth) == 0)
            {
                sbPattern.insert(0, ',');
            }
        }
        DecimalFormat dfDouble = new DecimalFormat(sbPattern.toString());
        return dfDouble.format(lValue);
    }

    /**
     * I wrote a wrong version of this Function, for compatability, I have to follow previos rule.
     * that means, "specify nGroupWidth argument one less you want, nFraction one more as you want,
     * specify bZeroPreffixed false if you want no zero preffixed, versa contra."<br>
     * I offer my sorry for my mistake here, Ping Liu
     */
    public static String formatDouble(double dfValue, int nWidth, int nFraction,
                                      boolean bZeroPreffixed, int nGroupWidth)
    {
        DecimalFormat dfDouble = new DecimalFormat();
        if(nGroupWidth > 0)
        {
            dfDouble.setGroupingSize(nGroupWidth + 1);
            dfDouble.setGroupingUsed(true);
        }
        if(bZeroPreffixed)
        {
            dfDouble.setMinimumIntegerDigits(1);
        } else
        {
        // dfDouble.setMinimumIntegerDigits( nWidth - nFraction )
        }
        dfDouble.setMaximumFractionDigits(nFraction - 1);
        dfDouble.setMinimumFractionDigits(nFraction - 1);
        return dfDouble.format(dfValue);
    }

    /**
     * <p>将double型对象转换成String型对象，并且指定加上小数点的小数部分一共转换多少位</p>
     * @param dbValue double value to be formatted.
     * @param nWidth total width of output string
     * @return formatted string.
     */
    public static String formatDouble(int nFraction, double dfValue)
    {
        return formatDouble(dfValue, nFraction + 1, nFraction, false, 0);
    }

    //the following const is to define double format
    public static final int FMT_NUM_NORMAL = 1;
    public static final int FMT_NUM_NODELIMA = 2;
    public static final int FMT_NUM_COMMADELIMA = 3;

    /**
     * <p>获得路径文件的文件名称.</p>
     * @param strFilePath file path to be processed.
     * @return file name.
     */
    public static String getFileName(String strFilePath)
    {
        int nIndex = 0;
        if((nIndex = strFilePath.lastIndexOf('\\')) == -1)
        {
            nIndex = strFilePath.lastIndexOf('/');
        }
        return strFilePath.substring(nIndex + 1, strFilePath.length());
    }

    /**
     * <p>得到随机数字符</p>
     * @return 返回随机数
     */
    public static String getRnd()
    {
        return String.valueOf((long)((10000000000l - 1000000000l + 1) *
                                     Math.random()) + 1000000000l);
    }

    /**
     * <p>将一个格式为"yyyy-mm-dd"的String对象转换成一个Date型对象</p>
     * @param con
     * @exception java.sql.SQLException
     * @return
     */
    static public java.sql.Date getDate(String strDate) throws SQLException
    {
        return java.sql.Date.valueOf(strDate);
    }

    /**
     * <p>将一个格式为 "yyyy-mm-dd" 或 "yyyy-mm-dd hh:mm:ss" 的String型对象转换成Timestamp型对象</p>
     * @param sDt
     * @return
     */
    static public java.sql.Timestamp getDateTime(String sDt)
    {
        try
        {
            return java.sql.Timestamp.valueOf(sDt); //sDt format:yyyy-mm-dd hh:mm:ss.fffffffff
        } catch(IllegalArgumentException iae)
        {
            sDt = sDt + " 00:00:00";
            try
            {
                return java.sql.Timestamp.valueOf(sDt);
            } catch(Exception e)
            {
                return null;
            }
        }

    }

    /**
     * <p>将一个Timestamp型日期对象转换成格式为"yyyy-mm-dd hh:mm:ss.fffffffff"的String型对象</p>
     * @param ts
     * @return
     */
    static public String getDateTimeString(java.sql.Timestamp ts)
    {
        if(null == ts)
        {
            return "";
        }
        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(ts);
        return calendar.get(Calendar.YEAR)
            + "-"
            + (calendar.get(Calendar.MONTH) + 1)
            + "-"
            + calendar.get(Calendar.DATE)
            + " "
            + calendar.get(Calendar.HOUR_OF_DAY)
            + ":"
            + calendar.get(Calendar.MINUTE)
            + ":"
            + calendar.get(Calendar.SECOND);
        //return ts.toString();
    }

    /**
     * <p>获得一个String型的当前系统时间，格式为"yyyy_mm_dd"</p>
     * @return String
     */
    static public String getDateString() throws SQLException
    {
        java.util.Calendar calendar = Calendar.getInstance();
        String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if(strMonth.length() == 1)
        {
            strMonth = "0" + strMonth;
        }
        String strDay = String.valueOf(calendar.get(Calendar.DATE));
        if(strDay.length() == 1)
        {
            strDay = "0" + strDay;
        }
        return calendar.get(Calendar.YEAR) + "-" + strMonth + "-" + strDay;
    }

    /**
     * <p>取与交割单编号符合的日期格式，例如：2002－10－21转换成021021</p>
     * @return
     */
    public static String getTransDateString(Timestamp ts)
    {
        java.util.Calendar calendar = Calendar.getInstance();

        calendar.setTime(ts);

        String strYear = (calendar.get(Calendar.YEAR) + "").substring(2, 4);

        String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if(strMonth.length() == 1)
        {
            strMonth = "0" + strMonth;
        }
        String strDay = String.valueOf(calendar.get(Calendar.DATE));
        if(strDay.length() == 1)
        {
            strDay = "0" + strDay;
        }
        return strYear + strMonth + strDay;
    }

    /**
     * <p>得到数字随机数</p>
     * @param nLen随机数长度
     */
    public static String randomNumberPassword(int nLen)
    {
        long lNum = 1;
        for(int i = 0; i < nLen - 1; i++)
        {
            lNum = lNum * 10;
        }
        return String.valueOf((long)((lNum * 10 - lNum + 1) * Math.random()) +
                              lNum);
    }

    /**
     * <p>将一个double对象转换成一个String对象</p>
     * @param dValue
     * @param lScale 小数点后保留的位数
     * @return
     */
    public static String format(double dValue, int lScale)
    {
        //负数，则装化为正数后进行四舍五入
        boolean bFlag = false;
        if(dValue < 0)
        {
            bFlag = true;
            dValue = -dValue;
        }

        long lPrecision = 10000; //精度值，为了避免double型出现偏差，增加校验位
        long l45Value = lPrecision / 2 - 1; //四舍五入的判断值

        long lLength = 1; //乘的数字
        for(int i = 0; i < lScale; i++)
        {
            lLength = lLength * 10; //比如保留2位，乘以100
        }
        long lValue = (long)dValue; //值的整数部分
        long lValue1 = (long)((dValue - lValue) * lLength); //乘以保留位数
        long lValue2 = (long)((dValue - lValue) * lLength * lPrecision); //
        long lLastValue = lValue2 - (lValue2 / lPrecision) * lPrecision;
        if(lLastValue >= l45Value)
        {
            lValue1++;
        }
        dValue = lValue + (double)lValue1 / lLength; //四舍五入后的值
        if(bFlag)
        {
            dValue = -dValue;
        }

        java.math.BigDecimal bd = new java.math.BigDecimal(dValue);
        bd = bd.setScale(lScale, java.math.BigDecimal.ROUND_HALF_UP);

        return bd.toString();

    }

    /**
     * <p>将一个float对象转换成一个String对象</p>
     * @param dValue
     * @param lScale 小数点后保留的位数
     * @return
     */
    public static String format(float fValue, int lScale)
    {
        /*java.math.BigDecimal bd;
                 bd = new java.math.BigDecimal(fValue);
                 bd = bd.setScale(lScale, java.math.BigDecimal.ROUND_HALF_UP);
                 return bd.toString();*/
        java.math.BigDecimal bd, bdup, bddown;
        bd = new java.math.BigDecimal(fValue);
        bdup = bd.setScale(lScale, java.math.BigDecimal.ROUND_UP);
        bddown = bd.setScale(lScale, java.math.BigDecimal.ROUND_DOWN);
        if((bdup.doubleValue() - bd.doubleValue()) <=
           (bd.doubleValue() - bddown.doubleValue()))
        {
            return bdup.toString();
        } else
        {
            return bddown.toString();
        }
    }

    /**
     * <p>格式化利率</p>
     * @param fValue 利率值
     */
    public static String formatRate(float fValue)
    {
        if(fValue == 0)
        {
            return "0.00";
        } else
        {
            return format(fValue, 4);
        }
    }

    /**
     * <p>格式化金额</p>
     * @param dValue 金额
     */
    public static String formatAmount(double dValue)
    {
        if(dValue == 0)
        {
            return "";
        } else if(dValue > 0)
        {
            return format(dValue, 2);
        } else
        {
            return "-" + format(java.lang.Math.abs(dValue), 2);
        }
    }

    /**
     * <p>格式化金额包括0</p>
     * @param dValue
     * @return
     */
    public static String formatAmountUseZero(double dValue)
    {
        if(dValue == 0)
        {
            return "0";
        } else if(dValue > 0)
        {
            return format(dValue, 2);
        } else
        {
            return "-" + format(java.lang.Math.abs(dValue), 2);
        }
    }

    /**
     * <p>反向格式化金额，将","去掉</p>
     * @param strData 数据
     */
    public static String reverseFormatAmount(String strData)
    {
        int i;
        String strTemp;
        //去掉所有的","
        strTemp = new String(strData);
        strData = "";
        for(i = 0; i < strTemp.length(); i++)
        {
            char cData;
            cData = strTemp.charAt(i);
            if(cData != ',')
            {
                strData = strData + cData;
            }
        }
        return strData;
    }

    /**
     * <p>格式化列表的金额</p>
     * @param dAmount 金额
     * @return 返回格式化不戴小数点没有逗号分割符的金额
     */
    public static String formatAmountNotDot(double dAmount)
    {
        String strData = formatAmount(dAmount);
        if(strData.equals(""))
        {
            return strData;
        } else
        {
            //将小数点前和后的数据分别取出来
            int nPoint;
            nPoint = strData.indexOf(".");
            String strFront = new String(strData), strEnd = "";
            if(nPoint != -1)
            {
                strFront = strData.substring(0, nPoint);
                strEnd = strData.substring(nPoint + 1, strData.length());
            }
            //补或者截小数点后面的值，保持两位
            if(strEnd.length() > 2)
            {
                strEnd = strEnd.substring(0, 2);
            } else
            {
                if(strEnd.length() == 1)
                {
                    strEnd = strEnd + "0";
                } else
                {
                    if(strEnd.length() == 0)
                    {
                        strEnd = "00";
                    }
                }
            }
            strData = strFront + strEnd;
            for(int ii = 18; ii < strData.length(); ii--)
            {
                strData = "&nbsp;" + strData;
            }
        }
        return strData;
    }

    /**
     * <p>格式化列表的金额</p>
     * @param dAmount 金额
     * @param nType 1-将0转换，2-不将0转换
     * @return
     */
    public static String formatDisabledAmount(double dAmount, int nType)
    {
        String strData = "";
        if(nType == 1)
        {
            strData = formatAmount(dAmount);
        } else
        {
            strData = formatAmountUseZero(dAmount);
        }
        if(dAmount < 0)
        {
            strData = formatAmount(java.lang.Math.abs(dAmount));
        }
        if(strData.equals(""))
        {
            return strData;
        } else
        {
            //将小数点前和后的数据分别取出来
            int nPoint;
            nPoint = strData.indexOf(".");
            String strFront = new String(strData), strEnd = "";
            if(nPoint != -1)
            {
                strFront = strData.substring(0, nPoint);
                strEnd = strData.substring(nPoint + 1, strData.length());
            }
            String strTemp = "";
            //小数点前面的数据加","
            strTemp = new String(strFront);
            strFront = "";
            int nNum, i;
            nNum = 0;
            for(i = strTemp.length() - 1; i >= 0; i--)
            {
                if(nNum == 3)
                {
                    strFront = "," + strFront;
                    nNum = 0;
                }
                nNum++;
                char cData;
                cData = strTemp.charAt(i);
                strFront = cData + strFront;
            }
            //补或者截小数点后面的值，保持两位
            if(strEnd.length() > 2)
            {
                strEnd = strEnd.substring(0, 2);
            } else
            {
                if(strEnd.length() == 1)
                {
                    strEnd = strEnd + "0";
                } else
                {
                    if(strEnd.length() == 0)
                    {
                        strEnd = "00";
                    }
                }
            }
            strData = strFront + "." + strEnd;
        }
        if(dAmount < 0 && !strData.equals("0.00"))
        {
            strData = "-" + strData;
        }
        return strData;
    }

    /**
     * 格式化列表的金额
     * @param dAmount 金额
     * @return 返回格式化的金额
     */
    public static String formatDisabledAmount(double dAmount)
    {
        return formatDisabledAmount(dAmount, 1);
    }

    /**
     * 格式化列表的金额
     * @param dAmount 金额
     * @return 返回格式化的金额
     */
    public static String formatListAmount(double dAmount)
    {
        return formatDisabledAmount(dAmount);
    }

    /**
     * 格式化字符串
     * @param strData 字符串数据
     */
    public static String formatString(String strData)
    {
        if(strData == null)
        {
            return "";
        } else
        {
            if(strData.equals("null"))
            {
                return "";
            } else
            {
                return strData;
            }
        }
    }

    /**
     * 对于double型对象进行保留小数位数操作
     * @param dValue
     * @param nScale 指定小数点后保留的位数
     * @return
     */
    public static double formatDouble(double dValue, int nScale)
    {
        return new Double(format(dValue, nScale)).doubleValue();
    }

    public static String transCode(long ID, int length)
    {
        String returnString = "";
        if(ID >= 0)
        {
            returnString = String.valueOf(ID);
            for(int i = returnString.length(); i < length; i++)
            {
                returnString = "0" + returnString;
            }
        }
        return returnString;
    }

    /**
     * 格式化数字，例如：12345转化为12,345
     * @param dValue 被格式化的数值
     * @param iScale 小数点后保留位数,不足补0
     * @return
     */
    public static String formatNumber(double dValue, int iScale)
    {
        DecimalFormat df = null;
        StringBuffer sPattern = new StringBuffer(",##0");

        if(iScale > 0)
        {
            sPattern.append(".");
            for(int i = 0; i < iScale; i++)
            {
                sPattern.append("0");
            }
        }

        df = new DecimalFormat(sPattern.toString());

        // modified by chenlei 2003-05-06
        //if(dValue == 0)
        //{
        //    return "0"+df.format(dValue);
        //}

        return df.format(dValue);
    }

    public static String formatNumber(long lValue)
    {
        return formatNumber((double)lValue, 0);
    }

    /**
     * 解析格式化的字符串，转化为double数值
     * @param text
     * @return
     */
    public static double parseNumber(String text)
    {
        int index = text.indexOf(",");
        String sbNumber = "";

        while(index != -1)
        {
            sbNumber += text.substring(0, index);
            text = text.substring(index + 1, text.length());
            index = text.indexOf(",");
        }

        sbNumber += text;
        System.out.println(sbNumber);

        return Double.parseDouble(sbNumber);
    }

    /**
     * 如果字符串str1不为空返回str2+str1,否则返回&nbsp;
     * @param str1
     * @param str2
     * @return
     */
    public String replaceString(String str1, String str2)
    {
        String value = "";
        try
        {
            if(str1 == null || str1.equals(""))
            {
                value = "&nbsp;";
            } else
            {
                value = str2 + str1;
            }
        } catch(Exception e)
        {
            value = "&nbsp;";
        }
        return value;
    }

    /**
     * 处理空格，如果字符串为空返回"&nbsp;"
     * @param value
     * @return
     */
    public String dealEmpty(String value)
    {
        try
        {
            if(value == null || value.equals(""))
            {
                value = "&nbsp;";
            }
        } catch(Exception e)
        {
            value = "&nbsp;";
        }
        return value;
    }

    /**
     * 将0转换成"-"输出
     * @param temp
     * @return
     */
    public String convertNum(int temp)
    {
        String value = "";
        if(temp == 0)
        {
            return "-";
        } else
        {
            value = new Integer(temp).toString();
            return value;
        }
    }

    /**
     * 将0转换成空格输出
     * @param value
     * @return
     */
    public String formatNum(String value)
    {
        try
        {
            if(value.trim().equals("0"))
            {
                value = "&nbsp";
            }
        } catch(Exception e)
        {}
        return value;
    }

    /**
     * 将0转换成空格输出
     * @param temp
     * @return
     */
    public String formatNum(int temp)
    {
        String value = "";
        if(temp == 0)
        {
            return "&nbsp;";
        } else
        {
            value = new Integer(temp).toString();
            return value;
        }
    }

    /**
     * 计算岁数
     * @param str
     * @return
     */
    public String getAge(String str)
    {
        int csYear = 0;
        int nowYear = 0;
        int age = 0;
        String strAge = "";
        try
        {
            csYear = new Integer(str.substring(0, 4)).intValue();
        } catch(Exception e)
        {
            return "出生日期有误";
        }
        Calendar today = java.util.Calendar.getInstance();
        nowYear = today.get(Calendar.YEAR);
        age = nowYear - csYear;
        if(age < 0)
        {
            return "出生日期有误";
        } else
        {
            strAge = new Integer(age).toString() + "岁";
        }
        return strAge;
    }

    /**
     * 格式化字符串
     * ""
     * @param str
     * @return
     */
    public String formatStr(String str)
    {
        if(str == null || str.equals(""))
        {
            return "";
        }
        StringBuffer buf = new StringBuffer(str.length() + 6);
        char ch = ' ';
        for(int i = 0; i < str.length(); i++)
        {
            ch = str.charAt(i);
            if(ch == '<')
            {
                buf.append("&lt;");
            } else
            {
                if(ch == '>')
                {
                    buf.append("&gt;");
                } else
                {
                    if(ch == ' ')
                    {
                        buf.append("&nbsp;");
                    } else
                    {
                        if(ch == '\n')
                        {
                            buf.append("<br>");
                        } else
                        {
                            if(ch == '\'')
                            {
                                buf.append("&#039;");
                            } else
                            {
                                if(ch == '\"')
                                {
                                    buf.append("&quot;");
                                } else
                                {
                                    buf.append(ch);
                                }
                            }

                        }
                    }
                }
            }
        }
        str = buf.toString();
        return str;
    }

    public static void main(String[] args)
    {
        DataFormat obj_DataFormat = new DataFormat();
        try
        {
            System.out.println(obj_DataFormat.formatStr("das<das"));
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 返回DateFormat实例
     * @return
     */
    public static synchronized DataFormat getDataFormat()
    {
        if(obj_DataFormat == null)
        {
            obj_DataFormat = new DataFormat();
        }
        return obj_DataFormat;
    }
}
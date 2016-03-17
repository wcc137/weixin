package com.changing.framework.helper;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools
{

    private static DateTools obj_DateTools = null;

    private DateTools()
    {

    }

    // 初始化几种常见的日期格式
    public static final int FMT_DATE_YYYYMMDD = 1;
    public static final int FMT_DATE_YYYYMMDD_HHMMSS = 2;
    public static final int FMT_DATE_HHMMSS = 3;
    public static final int FMT_DATE_HHMM = 4;

    /**
     * <p>将Calendar类型日期对象转换成String型对象</p>
     * @param objCal Calendar型的对象
     * @return String 返回一个String型的对象（返回格式yyyy/MM/dd HH:mm:ss）
     */
    public static String formatCalendar(Calendar objCal)
    {
        String strFormat = "yyyy/MM/dd HH:mm:ss";

        if(objCal == null)
        {
            return null;
        } else
        {
            return formatCalendar(objCal, strFormat);
        }
    }

    /**
     * <p>将Calendar型的日期对象转换成指定的strFormat格式的String型对象</p>
     * <p>例如strFormat为：</p>
     * <p>"yyyy.MM.dd G 'at' HH:mm:ss z"    ->>  1996.07.10 AD at 15:08:56 PDT
     * <p>"EEE, MMM d, ''yy"                ->>  Wed, July 10, '96
     * <p>"h:mm a"                          ->>  12:08 PM
     * <p>"hh 'o''clock' a, zzzz"           ->>  12 o'clock PM, Pacific Daylight Time
     * <p>"K:mm a, z"                       ->>  0:00 PM, PST
     * <p>"yyyyy.MMMMM.dd GGG hh:mm aaa"    ->>  1996.July.10 AD 12:08 PM
     * <p>
     * @param objCal The calendar object
     * @param strFormat The format of the output string
     * @return the String format of the Calendar Object
     */
    public static String formatCalendar(Calendar objCal, String strFormat)
    {
        if(objCal == null)
        {
            return null;
        } else
        {
            SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
            return formatter.format(objCal.getTime());
        }
    }

    /**
     * <p>将String型的日期对象转换成指定格式strFormat的Calendar型日期对象</p>
     * <p>例如：</p>
     * <p>Usage: Change the string "2002-11 12:01" into a Calendar object
     * <p>          covertStringToCalendar("2002-11 12:01", "yyyy-MM HH:mm");
     * <p>
     * @param strDate The date in String format
     * @param strFormat The formate of the date string
     * @return the Calendar Object of the date string
     */
    public static Calendar convertStringToCalendar(String strDate, String strFormat)
    {
        if(strDate == null || strFormat == null)
        {
            return null;
        } else
        {
            SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
            ParsePosition pos = new ParsePosition(0);
            Calendar objCalendar = Calendar.getInstance();
            objCalendar.setTime(formatter.parse(strDate, pos));
            return objCalendar;
        }
    }

    /**
     * <p>将Calendar日期对象转换成Timestamp日期对象</p>
     * @param objCalendar The Calendar object
     * @return the Timstamp version of the Calendar object
     */
    public static Timestamp convertCalendarToTimestamp(Calendar objCalendar)
    {
        Timestamp objDate = null;
        if(objCalendar == null)
        {
            return null;
        } else
        {
            objDate = new Timestamp(objCalendar.getTime().getTime());
            return objDate;
        }
    }

    /**
     * <p>将Timestamp日期对象转换成Calendar日期对象</p>
     * @param objTimestamp The Timstamp version of the Calendar object
     * @return the Calendar object of the Timestamp object
     */
    public static Calendar convertTimestampToCalendar(Timestamp objTimestamp)
    {
        if(objTimestamp == null)
        {
            return null;
        } else
        {
            Calendar objCalendar = Calendar.getInstance();
            objCalendar.setTime(objTimestamp);
            return objCalendar;
        }
    }

    /**
     * <p>将特定格式的String型对象转换成java.util.Date对象</p>
     * @param strDate a date string
     * @param nFmtDate specific date string format defined in this class.
     * @exception raise ParseException, if string format dismathed.
     * @return Date
     * @throws Exception
     */
    public static Date parseDate(String strDate, int nFmtDate) throws Exception
    {
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        switch(nFmtDate)
        {
            default:
            case DateTools.FMT_DATE_YYYYMMDD:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                //fmtDate.applyLocalizedPattern("yyyy-MM-dd");
                break;
            case DateTools.FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //fmtDate.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case DateTools.FMT_DATE_HHMM:
                fmtDate = new SimpleDateFormat("HH:mm");
                //fmtDate.applyLocalizedPattern("HH:mm");
                break;
            case DateTools.FMT_DATE_HHMMSS:
                fmtDate = new SimpleDateFormat("HH:mm:ss");
                //fmtDate.applyLocalizedPattern("HH:mm:ss");
                break;
        }
        return fmtDate.parse(strDate);
    }

    /**the number of milliseconds since January 1, 1970, 00:00:00 GMT
     * 把秒数转为日期及时间 
     * @param lDateInt  秒数－毫秒
     * @param nFmtDate 格式
     * @return string型日期及时间
     * @throws Exception
     */
    public static String formatDate(long lDateInt, int nFmtDate) throws Exception
    {
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        switch(nFmtDate)
        {
            default:
            case DateTools.FMT_DATE_YYYYMMDD:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd");
                //fmtDate.applyLocalizedPattern("yyyy-MM-dd");
                break;
            case DateTools.FMT_DATE_YYYYMMDD_HHMMSS:
                fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //fmtDate.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
                break;
            case DateTools.FMT_DATE_HHMM:
                fmtDate = new SimpleDateFormat("HH:mm");
                //fmtDate.applyLocalizedPattern("HH:mm");
                break;
            case DateTools.FMT_DATE_HHMMSS:
                fmtDate = new SimpleDateFormat("HH:mm:ss");
                //fmtDate.applyLocalizedPattern("HH:mm:ss");
                break;
        }
        return fmtDate.format(new Date(lDateInt));
        
    }

    /**
     * 将Date转换成para形式的字符串
     * @param strDate
     * @param para
     * @return
     * @throws java.lang.Exception
     */
    public static String AllFormat(Date strDate, String para) throws Exception
    {
        try
        {
            SimpleDateFormat fmtDate = new SimpleDateFormat(para);
            return fmtDate.format(strDate);
        } catch(Exception e)
        {
            return "";
        }
    }

    /**
     * <p>通过给定的年、月、日、时、分、秒生成一个Timestamp对象</p>
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return
     *   得到的Timestamp对象
     */
    static public java.sql.Timestamp getDateTime(int year, int month, int day,
                                                 int hour, int minute,
                                                 int second)
    {
        java.sql.Timestamp ts = null;
        java.util.Date dt = null;
        java.util.Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        dt = calendar.getTime();
        ts = new java.sql.Timestamp(dt.getTime());
        return ts;
    }

    /**
     * <p>根据指定的日期field类型，如Calendar.YEAR、Calendar.MONTH，
     * 获取指定日期的前/后 日期
     * <br>
     * 用法示例：<br>
     * 1、得到指定日期的前5天 ：getAppoinDate(tsDate,-5,Calendar.DATE) <br>
     * 2、得到指定日期的后5月 ：getAppoinDate(tsDate,5,Calendar.MONTH) <br>
     * </p>
     * @param
     *   tsDate  ：指定的日期
     * @param
     *   nCount ：想要增加或减小的数值，如增加则是正数，否则为负数
     * @param
     *    nType : 日期field的类型，如Calendar.YEAR、Calendar.MONTH、Calendar.DATE等
     * @return
     *   获取的日期Timestamp对象
     */
    static public java.sql.Timestamp getAppoinDate(java.sql.Timestamp tsDate,
        int nCount, int nType)
    {
        if(null == tsDate)
        {
            return null;
        }
        java.util.Calendar calendar = Calendar.getInstance();

        calendar.setTime(tsDate);
        calendar.set(nCount, nType);

        return getDateTime(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DATE),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND));
    }

    /**
     * 获得制定年月的一共有多少天
     * @param strYear
     * @param strDate
     * @return
     */
    public int getLastDayOfMonth(String strYear,String strDate)
    {
        Calendar cal = Calendar.getInstance();
        int myYear = Integer.parseInt(strYear);
        int myMonth = Integer.parseInt(strDate);
        cal.set(myYear, myMonth - 1, 01);
        int myDay = cal.getActualMaximum(Calendar.DATE);
        return myDay;
    }

    /**
     * 获得DateTools实例
     * @return
     */
    public static synchronized DateTools getDateTools()
    {
        if(obj_DateTools == null)
        {
            obj_DateTools = new DateTools();
        }
        return obj_DateTools;
    }

    public static void main(String[] args)
    {
        try
        {
            //System.out.println((DateTools.parseDate("2005-02-01 12:12:12",DateTools.FMT_DATE_YYYYMMDD_HHMMSS).getHours()));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
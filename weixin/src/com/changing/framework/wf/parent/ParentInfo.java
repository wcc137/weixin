/********************************************************************************************************
 * 系统名称：温州MIS
 * 程序名称：ParentInfo.java
 * 程序类型：Bean
 * 功能简述：所有tableInfo的超类
 * 作    者：陈晶
 * 公    司：珠海亚仿
 * 完成时间：2005/08/01
 * 修 改 人：
 * 修改内容：
 * 修改日期：
 ********************************************************************************************************/
package com.changing.framework.wf.parent;

import java.util.Vector;

import com.changing.framework.web.LogInfo;
import com.changing.framework.web.WriterLogInfo;

public class ParentInfo
{
    private Vector vector = null; //结果集
    private LogInfo obj_LogInfo = new LogInfo(); //用户登陆session
    private String userMID; //用户登陆机器I
    private WriterLogInfo obj_WriterLogInfo = new WriterLogInfo(); //记录日志Info

    public Vector getVector()
    {
        return this.vector;
    }

    public void setVector(Vector vector)
    {
        this.vector = vector;
    }

    public LogInfo getObj_LogInfo()
    {
        return obj_LogInfo;
    }

    public void setObj_LogInfo(LogInfo obj_LogInfo)
    {
        this.obj_LogInfo = obj_LogInfo;
    }

    public String getUserMID()
    {
        return userMID;
    }

    public void setUserMID(String userMID)
    {
        this.userMID = userMID;
    }
    public WriterLogInfo getObj_WriterLogInfo()
    {
        return obj_WriterLogInfo;
    }
    public void setObj_WriterLogInfo(WriterLogInfo obj_WriterLogInfo)
    {
        this.obj_WriterLogInfo = obj_WriterLogInfo;
    }
}
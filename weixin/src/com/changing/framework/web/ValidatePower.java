package com.changing.framework.web;

import com.changing.framework.db.QueryOneBean;
import com.changing.framework.helper.StringHelper;

public class ValidatePower
{
	
   /**
    * 
    * @param mkdm 模块代码
    * @param sfzb 是否主表 主从表设置,如果主表有权限,则从表也有权限访问
    * @return 无权限返回true 否则返回false
    */
    public boolean checkPagePower(LogInfo logInfo,String mkdm,String sfzb)
    {
    	
    	boolean returnVal = true;
    	if ("1".equals(sfzb)) {
    		returnVal = false;
    	} else {
    		for(int i = 0; i < logInfo.getMkVector().size(); i++)
	        {
	            if(((String) logInfo.getMkVector().elementAt(i)).equals(mkdm))
	            {
	                returnVal = false;
	                break;
	            }
	        }
    	}
        return returnVal;
    }
    /**
     * 查询页面新增按钮权限控制
     * @param logInfo 登录人信息
     * @param czxh 查询页面注册 按钮注册 序号
     * @return
     */
    public String checkButtonPower(LogInfo logInfo, String czxh)
    {
        String returnVal = "disabled";
        for(int i = 0; i < logInfo.getCzVector().size(); i++)
        {
            if(((String) logInfo.getCzVector().elementAt(i)).equals(czxh))
            {
                returnVal = "";
                break;
            }
        }
        return returnVal;
    }
    /**
     * 查询页面查询权限控制
     * @param logInfo 登录信息
     * @param mkdm 模块代码
     * @param yzzd 权限字段 C_ID,也可以定义为其它的字段(只能是人)
     * @return
     */
    public String checkQueryDatePower(LogInfo logInfo, String mkdm, String yzzd)
    {
        String returnVal = "";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
        //除全厂 部门 班组 个人 之外 其它值都无权限查询
        String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@1") != null ?logInfo.getSjqxHashMap().get(mkdm+"@1").toString():"";
        if ("1".equals(qxbs)) { //全部厂级
           returnVal = "";
        } else if ("2".equals(qxbs)) { //部门
           returnVal = " AND c_dept LIKE '"+StringHelper.substring(user_dept,0,4)+"%'";
        } else if ("3".equals(qxbs)) { //班组
           returnVal = " AND c_dept = '"+user_dept+"'";
        } else if ("4".equals(qxbs)) { //个人
           returnVal = " AND " + yzzd + " = '" + user_id + "'";
        } else {//没有授任何权限
           returnVal = " AND (1 = 2) ";
           //returnVal = " AND " + yzzd + " = '" + user_id + "'";
        }
        return returnVal;
    }
    /**
     * 
     * @param logInfo
     * @param mkdm 模块代码
     * @param c_id 创建人代码
     * @param c_dept 创建部门代码
     * @return
     */
    public String checkQueryDatePower(LogInfo logInfo, String mkdm, String c_id,String c_dept)
    {
        String returnVal = "";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
        //除全厂 部门 班组 个人 之外 其它值都无权限查询
        String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@1") != null ?logInfo.getSjqxHashMap().get(mkdm+"@1").toString():"";
        if ("1".equals(qxbs)) { //全部厂级
           returnVal = "";
        } else if ("2".equals(qxbs)) { //部门
           returnVal = " AND " + c_dept + " LIKE '"+StringHelper.substring(user_dept,0,4)+"%'";
        } else if ("3".equals(qxbs)) { //班组
           returnVal = " AND " + c_dept + " ='"+user_dept+"'";
        } else if ("4".equals(qxbs)) { //个人
           returnVal = " AND " + c_id + " = '" + user_id + "'";
        } else {//没有授任何权限
           returnVal = " AND (1 = 2) ";
           //returnVal = " AND " + yzzd + " = '" + user_id + "'";
        }
        return returnVal;
    }
    /**
     * 编辑页面 保存按钮权限控制
     * @param logInfo	登录人信息
     * @param mkdm	模块代码
     * @param c_id  创建人代码
     * @return
     */
    public String checkEditPower(LogInfo logInfo, String mkdm, String c_id)
    {
        String returnVal = "disabled";
        String c_dept = "";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        QueryOneBean queryOneBean = new QueryOneBean();
        if(queryOneBean.executeQuery("select BMDM from T_XT_YGDM where YGDM = '" + c_id + "'")) {	
        	//创建人的部门代码
            c_dept = queryOneBean.getValue("BMDM")==null?"":queryOneBean.getValue("BMDM").trim();
        }
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
        //除全厂 部门 班组 个人 之外 其它值都无权限便捷
    	String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@2") != null ?logInfo.getSjqxHashMap().get(mkdm+"@2").toString():"";
        if ("1".equals(qxbs)) { //全部
        	returnVal = "";
        } else if ("2".equals(qxbs)) { //部门
    		//创建人部门 和 登录人部门相同 同一个部门
    		if (StringHelper.substring(c_dept,0,4).equals(StringHelper.substring(user_dept,0,4))) {
    			 returnVal = "";
    		} 
    	} else if ("3".equals(qxbs)) { //班组 
    		//创建人班组 和 登录人班组相同 同一个班组
            if (c_dept.equals(user_dept)){
       			 	returnVal = "";
            }
        } else if ("4".equals(qxbs)) { //个人
        	//创建人 和 登录人工号相同 同一个人
        	if (c_id.equals(user_id)) {
        		returnVal = "";
        	}
        } else {//没有授任何权限
        	//returnVal = "disabled";
        }
          
        return returnVal;
    }
    /**
     * 
     * 编辑页面 保存按钮权限控制
     * @param logInfo	登录人信息
     * @param mkdm	模块代码
     * @param c_id 创建人代码
     * @param c_dept 创建部门代码
     * @return
     */
    public String checkEditPower(LogInfo logInfo, String mkdm, String c_id,String c_dept)
    {
        String returnVal = "disabled";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
		//除全厂 部门 班组 个人 之外 其它值都无权限
		String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@2") != null ?logInfo.getSjqxHashMap().get(mkdm+"@2").toString():"";
		if ("1".equals(qxbs)) { //全部
			returnVal = "";
		} else if ("2".equals(qxbs) && 
			StringHelper.substring(c_dept,0,4).equals(StringHelper.substring(user_dept,0,4))) { //部门
			//创建人部门 和 登录人部门相同 同一个部门
			returnVal = "";
		} else if ("3".equals(qxbs) && c_dept.equals(user_dept)) {//班组 
			//创建人班组 和 登录人班组相同 同一个班组
			returnVal = "";
		} else if ("4".equals(qxbs) && c_id.equals(user_id)) {//个人
			//创建人 和 登录人工号相同 同一个人
			returnVal = "";
		} else {//没有授任何权限
			//returnVal = "disabled";
		}
        return returnVal;
    }
    /**
     * 编辑页面 删除按钮权限控制 
     * @param logInfo	登录人信息
     * @param mkdm	模块代码
     * @param c_id 创建人代码
     * @return
     */
    public String checkDeletePower(LogInfo logInfo, String mkdm, String c_id)
    {
        String returnVal = "disabled";
        String c_dept = "";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        QueryOneBean queryOneBean = new QueryOneBean();
        if(queryOneBean.executeQuery("select BMDM from T_XT_YGDM where YGDM = '" + c_id + "'"))
        {
            c_dept = queryOneBean.getValue("BMDM");
        }
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
		//除全厂 部门 班组 个人 之外 其它值都无权限
    	String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@3") != null ?logInfo.getSjqxHashMap().get(mkdm+"@3").toString():"";
		if ("1".equals(qxbs)) { //全部
			returnVal = "";
		} else if ("2".equals(qxbs) && 
				StringHelper.substring(c_dept,0,4).equals(StringHelper.substring(user_dept,0,4))) { //部门
			//创建人部门 和 登录人部门相同 同一个部门
			returnVal = "";
		} else if ("3".equals(qxbs) && c_dept.equals(user_dept)) { //班组 
			//创建人班组 和 登录人班组相同 同一个班组
			returnVal = "";
		} else if ("4".equals(qxbs) && c_id.equals(user_id)) { //个人
			//创建人 和 登录人工号相同 同一个人
			returnVal = "";
		} else {//没有授任何权限
			//returnVal = "disabled";
		}
         return returnVal;
    }
    /**
     * 编辑页面 删除按钮权限控制
     * @param logInfo	登录人信息
     * @param mkdm	模块代码
     * @param c_id 创建人代码
     * @param c_dept 创建部门代码
     * @return
     */
    public String checkDeletePower(LogInfo logInfo, String mkdm, String c_id,String c_dept)
    {
        String returnVal = "disabled";
        String user_id = logInfo.getXtYgdm().getYgdm()==null?"    ":logInfo.getXtYgdm().getYgdm().trim();
        String user_dept = logInfo.getXtYgdm().getBmdm()== null?"    ":logInfo.getXtYgdm().getBmdm().trim();
        //授权中(包括角色和个人) 权限标识 1 全厂 2 部门 3 班组 4 个人 (5 特殊) 6 没任何模块权限
		//除全厂 部门 班组 个人 之外 其它值都无权限便捷
		String qxbs = logInfo.getSjqxHashMap().get(mkdm+"@3") != null ?logInfo.getSjqxHashMap().get(mkdm+"@3").toString():"";
		if ("1".equals(qxbs)) { //全部
		returnVal = "";
		} else if ("2".equals(qxbs) && 
				StringHelper.substring(c_dept,0,4).equals(StringHelper.substring(user_dept,0,4))) { //部门
			//创建人部门 和 登录人部门相同 同一个部门
			returnVal = "";
		} else if ("3".equals(qxbs) && c_dept.equals(user_dept)) { //班组 
			//创建人班组 和 登录人班组相同 同一个班组
			returnVal = "";
		} else if ("4".equals(qxbs) && c_id.equals(user_id)) { //个人
			//创建人 和 登录人工号相同 同一个人
			returnVal = "";
		} else {//没有授任何权限
			//returnVal = "disabled";
		}
          return returnVal;
    }
    /**
     * 查询页面 属性列图标显示 
     * 权限不同 显示的图标也不同
     * @param logInfo
     * @param mkdm
     * @param c_id
     * @return
     */
    public String checkState(LogInfo logInfo, String mkdm, String c_id,String c_dept)
    {
        String returnVal = "";
        String edit = "0";
        String delete = "0";
        if(checkEditPower(logInfo, mkdm, c_id,c_dept).trim().equals(""))
        {
            edit = "1";
        }
        if(checkDeletePower(logInfo, mkdm, c_id,c_dept).trim().equals(""))
        {
            delete = "1";
        }
        returnVal = edit + delete;
        return returnVal;
        
        
    }
    /**
     * 主从表权限控制 用在主从表注册页面中
     * 主表编辑页面 嵌入 tab查询页面 使用参数power传递到子表查询页面
     * 在查询页面控制查询新增以及其它注册按钮的权限
     * power的值通过在主表中调用此方法获得 传递到子表查询页面以及编辑页面
     * 在编辑页面中根据此值控制保存 和 删除按钮的权限
     * @param logInfo
     * @param c_id
     * @param xh
     * @return
     */
    public String checkZC(LogInfo logInfo,String mkdm, String c_id,String c_dept)
    {
        String returnVal = "";
        String add = "0";
        String edit = "0";
        String delete = "0";
        if(checkEditPower(logInfo, mkdm, c_id,c_dept).equals(""))
        {
            add = "1";
            edit = "1";
        }
        if(checkDeletePower(logInfo,mkdm, c_id,c_dept).equals(""))
        {
            delete = "1";
        }
        returnVal = add + delete + edit;
        return returnVal;
    }
    /**
     * 子表查询页面 按钮权限控制
     * power 权限级别 add + delete + edit 111标识有全部权限
     */
    public String checkAddPowerByPower(String power)
    {
        //新增按钮
        if (StringHelper.substring(power, 0, 1).equals("1")) {
        	return "";
        } else {
        	return " disabled ";
        }
    }
    /**
     * 子表编辑页面 保存修改按钮权限控制
     * power 权限级别 add + delete + edit 111标识有全部权限
     */
    public String checkEditPowerByPower(String power)
    {
        //保存修改按钮
        if (StringHelper.substring(power, 2, 3).equals("1")) {
        	return "";
        } else {
        	return " disabled ";
        }
    }
    /**
     * 子表编辑页面 删除按钮权限控制
     * power 权限级别 add + delete + edit 111标识有全部权限
     */
    public String checkDeletePowerByPower(String power)
    {
        //删除按钮
        if (StringHelper.substring(power,1,2).equals("1")) {
        	return "";
        } else {
        	return " disabled ";
        }
    }
    /**
     * 验证附件权限
     * @param logInfo 登陆信息
     * @param c_id 主表信息的创建人
     * @return 返回四位的字符串【增删改查】
     * 暂时附件的新增权限,根据主表修改一样,有主表修改就有附件新增和修改权限
     */
    public String checkFJPower(LogInfo logInfo,String mkdm, String c_id,String c_dept)
    {
        String returnVal = "";
        String add = "0";
        String delete = "0";
        String edit = "0";
        if(checkEditPower(logInfo, mkdm, c_id,c_dept).equals(""))
        {
            add = "1";
            edit = "1";
        }
        if(checkDeletePower(logInfo, mkdm, c_id,c_dept).equals(""))
        {
            delete = "1";
        }
        returnVal = add + delete + edit + "1";
        return returnVal;
    }

}
package com.changing.common.systemmanager;

import java.io.Serializable;
import java.util.HashMap;


import com.changing.framework.db.QueryBean;
import com.changing.framework.helper.LogHelper;

public class ParentApplication implements Serializable 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HashMap obj_DmbMx = new HashMap(1000);
    private static HashMap obj_Bmmc = new HashMap(1000);
    private static HashMap obj_Ygmc = new HashMap(1000);
    private static HashMap obj_Yhm  = new HashMap(1000);
    private boolean isStart = false;
    public ParentApplication()
    {
        try
        {
            System.out.println("正在ParentApplication inital ...");
            this.initialize();
            System.out.println("完成ParentApplication!");
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 功能描叙:
     * 初始化应用服务器范围的信息
     * @throws Exception
     * */
    public void initialize() throws Exception
    {
        this.refreshDmbMx();
        isStart = true;
    }
    public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public static HashMap getObj_DmbMx() {
		return obj_DmbMx;
	}

	

	public static HashMap getObj_Bmmc() {
		return obj_Bmmc;
	}

	

	public static HashMap getObj_Ygmc() {
		return obj_Ygmc;
	}

	

	public static HashMap getObj_Yhm() {
		return obj_Yhm;
	}
    public void setIsStart(boolean isStart)
    {
        this.isStart = isStart;
    }

    public boolean isIsStart()
    {
        return isStart;
    }

    /**
     * 功能描叙:
     * 刷新代码明细
     * @throws Exception
     * */
    public void refreshDmbMx() throws Exception
    {
       
        try
        {
            
            QueryBean queryBean = new QueryBean();
            
            queryBean.executeQuery("select bm,ccz,xsz from t_xt_dmbmx");
            
            for (int i = 1; i <= queryBean.getRowsCount(); i++) {
                String strBm = queryBean.getValue("bm",i);
                String strCcz = queryBean.getValue("ccz",i);
                if(strBm == null || strCcz == null)
                {
                    continue;
                }
                String strXsz = queryBean.getValue("xsz",i);
                if(strXsz == null)
                {
                    strXsz = "";
                }
                obj_DmbMx.put(strBm + " + " + strCcz, strXsz);
            }
            
            
            queryBean.clear();
            queryBean.executeQuery("select bmdm,bmmc from t_xt_bmdm");
           

            for (int i = 1; i <= queryBean.getRowsCount(); i++) {
                obj_Bmmc.put(queryBean.getValue("bmdm",i),queryBean.getValue("bmmc",i));
            }
          
            
            queryBean.clear();
            
            queryBean.executeQuery("select ygdm,xm,yhm from t_xt_ygdm");
          

            for (int i = 1; i <= queryBean.getRowsCount(); i++)
            {
                obj_Ygmc.put(queryBean.getValue("ygdm",i),queryBean.getValue("xm",i));
                obj_Yhm.put(queryBean.getValue("ygdm",i),queryBean.getValue("yhm",i));
            }
      
        } catch(Exception ex)
        {
        	LogHelper.logError(ParentApplication.class,ex.toString());
        } 

    }

    /**
     * 功能描叙:
     * 初始化应用服务器范围的信息
     * @throws Exception
     * */
    public String getDmbXsz(String strBm, String strCcz)
    {
        if(obj_DmbMx == null)
        {
            return "";
        }
        String s = "";
        try
        {
            if((String)obj_DmbMx.get(strBm + " + " + strCcz) == null)
           {
               s = "";
           } else {
               s = (String)obj_DmbMx.get(strBm + " + " + strCcz);
           }
        } catch(Exception e)
        {
            s = "";
            LogHelper.logError(ParentApplication.class," getDmbXsz(String strBm,String strCcz)" +
                                          e.toString());
        }

        return s;
    }
    
    public String getDmmc(String bmdm)
    {
        if(obj_Bmmc == null)
        {
            return "";
        }
        String s = "";
        try
        {
            if(obj_Bmmc.get(bmdm) == null)
            {
                s = "";
            } else {
                s = (String)obj_Bmmc.get(bmdm);
            }
        } catch(Exception e)
        {
            s = "";
            LogHelper.logError(ParentApplication.class+ " getDmmc(String bmdm)" +
                                          e.toString());
        }
        return s;
    }
    /**
     * 根据员工代码 获取员工姓名
     * @param ygdm
     * @return
     */
    public String getYgmc(String ygdm)
    {
        if(obj_Ygmc == null)
        {
            return "";
        }
        String s = "";
        try
        {
            if(obj_Ygmc.get(ygdm) == null)
            {
                s = "";
            } else {
                s = (String)obj_Ygmc.get(ygdm);
            }
        } catch(Exception e)
        {
            s = "";
            LogHelper.logError(ParentApplication.class+ " getYgmc(String ygdm)" +
                                          e.toString());
        }
        return s;
    }
    /**
     * 根据员工代码获取用户名
     * @param ygdm
     * @return
     */
    public String getYhm(String ygdm)
    {
        if(obj_Yhm == null)
        {
            return "";
        }
        String s = "";
        try
        {
            if(obj_Yhm.get(ygdm) == null)
            {
                s = "";
            } else {
                s = (String)obj_Yhm.get(ygdm);
            }
        } catch(Exception e)
        {
            s = "";
            LogHelper.logError(ParentApplication.class + " obj_Yhm(String ygdm)" +
                                          e.toString());
        }
        return s;
    }
    
	/**
	 * 加载初始化参数,包括原Note中的所有变量
	 */
    public void loadInitParameter()
    {
    	
    }
    public static void main(String[] args) throws Exception
    {
        ParentApplication p = new ParentApplication();
        System.out.println(p.getDmbXsz("WZCXTJ", "1"));
    }

}
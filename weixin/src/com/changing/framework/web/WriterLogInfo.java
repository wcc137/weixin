package com.changing.framework.web;

import java.util.Date;

import com.changing.framework.db.DbDateTime;
import com.changing.framework.db.Sequence;

public class WriterLogInfo {

    /**
     * 创建人 c_id
     * cId
     */
    protected String cId;

    /**
     * 创建代理人 c_agent_id
     * cAgentId
     */
    protected String cAgentId;

    /**
     * 创建部门 c_dept
     * cDept
     */
    protected String cDept;

    /**
     * 创建日期 c_date
     * cDate
     */
    protected Date cDate;

    /**
     * 最近修改人 m_id
     * mId
     */
    protected String mId;

    /**
     * 最近修改代理人 m_agent_id
     * mAgentId
     */
    protected String mAgentId;

    /**
     * 最近修改时间 m_date
     * mDate
     */
    protected Date mDate;

    /**
     * 最近修改机器 m_cn
     * mCn
     */
    protected String mCn;

    /**
     * 并发保护 md_prot
     * mdProt
     */
    protected String mdProt;

    /**
     * 删除标志 d_del
     * dDel
     */
    protected String dDel;

    /**
     * 序号 xh
     * xh
     */
    protected String xh;

    /**
     * 操作人 czr
     * czr
     */
    protected String czr;

    /**
     * 操作代理人 czdlr
     * czdlr
     */
    protected String czdlr;

    /**
     * 操作模块 czmk
     * czmk
     */
    protected String czmk;

    /**
     * 操作时间 czsj
     * czsj
     */
    protected Date czsj;

    /**
     * 所在机器 szjq
     * szjq
     */
    protected String szjq;

    /**
     * 操作类型 czlx
     * czlx
     */
    protected String czlx;

    /**
     * 操作内容（SQL文） cznr
     * cznr
     */
    protected String cznr;

    /**
     * 取得cId变量
     */
    public String getCId()
    {
        return cId;
    }

    /**
     * 设置cId变量
     */
    public void setCId(String _cId)
    {
        cId = _cId;
    }

    /**
     * 取得cAgentId变量
     */
    public String getCAgentId()
    {
        return cAgentId;
    }

    /**
     * 设置cAgentId变量
     */
    public void setCAgentId(String _cAgentId)
    {
        cAgentId = _cAgentId;
    }

    /**
     * 取得cDept变量
     */
    public String getCDept()
    {
        return cDept;
    }

    /**
     * 设置cDept变量
     */
    public void setCDept(String _cDept)
    {
        cDept = _cDept;
    }

    /**
     * 取得cDate变量
     */
    public Date getCDate()
    {
        return cDate;
    }

    /**
     * 设置cDate变量
     */
    public void setCDate(Date _cDate)
    {
        cDate = _cDate;
    }

    /**
     * 取得mId变量
     */
    public String getMId()
    {
        return mId;
    }

    /**
     * 设置mId变量
     */
    public void setMId(String _mId)
    {
        mId = _mId;
    }

    /**
     * 取得mAgentId变量
     */
    public String getMAgentId()
    {
        return mAgentId;
    }

    /**
     * 设置mAgentId变量
     */
    public void setMAgentId(String _mAgentId)
    {
        mAgentId = _mAgentId;
    }

    /**
     * 取得mDate变量
     */
    public Date getMDate()
    {
        return mDate;
    }

    /**
     * 设置mDate变量
     */
    public void setMDate(Date _mDate)
    {
        mDate = _mDate;
    }

    /**
     * 取得mCn变量
     */
    public String getMCn()
    {
        return mCn;
    }

    /**
     * 设置mCn变量
     */
    public void setMCn(String _mCn)
    {
        mCn = _mCn;
    }

    /**
     * 取得mdProt变量
     */
    public String getMdProt()
    {
        return mdProt;
    }

    /**
     * 设置mdProt变量
     */
    public void setMdProt(String _mdProt)
    {
        mdProt = _mdProt;
    }

    /**
     * 取得dDel变量
     */
    public String getDDel()
    {
        return dDel;
    }

    /**
     * 设置dDel变量
     */
    public void setDDel(String _dDel)
    {
        dDel = _dDel;
    }

    /**
     * 取得xh变量
     */
    public String getXh()
    {
        return xh;
    }

    /**
     * 设置xh变量
     */
    public void setXh(String _xh)
    {
        xh = _xh;
    }

    /**
     * 取得czr变量
     */
    public String getCzr()
    {
        return czr;
    }

    /**
     * 设置czr变量
     */
    public void setCzr(String _czr)
    {
        czr = _czr;
    }

    /**
     * 取得czdlr变量
     */
    public String getCzdlr()
    {
        return czdlr;
    }

    /**
     * 设置czdlr变量
     */
    public void setCzdlr(String _czdlr)
    {
        czdlr = _czdlr;
    }

    /**
     * 取得czmk变量
     */
    public String getCzmk()
    {
        return czmk;
    }

    /**
     * 设置czmk变量
     */
    public void setCzmk(String _czmk)
    {
        czmk = _czmk;
    }

    /**
     * 取得czsj变量
     */
    public Date getCzsj()
    {
        return czsj;
    }

    /**
     * 设置czsj变量
     */
    public void setCzsj(Date _czsj)
    {
        czsj = _czsj;
    }

    /**
     * 取得szjq变量
     */
    public String getSzjq()
    {
        return szjq;
    }

    /**
     * 设置szjq变量
     */
    public void setSzjq(String _szjq)
    {
        szjq = _szjq;
    }

    /**
     * 取得czlx变量
     */
    public String getCzlx()
    {
        return czlx;
    }

    /**
     * 设置czlx变量
     */
    public void setCzlx(String _czlx)
    {
        czlx = _czlx;
    }

    /**
     * 取得cznr变量
     */
    public String getCznr()
    {
        return cznr;
    }

    /**
     * 设置cznr变量
     */
    public void setCznr(String _cznr)
    {
    	if (_cznr.length() > 2000) {
    		cznr = _cznr.substring(0,2000)+"...";
		} else {
			cznr = _cznr;
		}
    }
    /**
	 * 获取写数据库的日志
	 */
	public String getLogSQL() {
		String str = " insert into T_XT_RIZHI (XH,CZR,CZMK,CZSJ,SZJQ,CZLX,CZNR,c_id,c_date,seq_number) " +
					 " values ("+Sequence.getSequenceStr("S_XT_RIZHI")+",'" + cId + "','"+
					 czmk + "'," + DbDateTime.getDbTimeStr()+",'"+ szjq+ "','"+ czlx+ "','"+ 
					 filter(cznr)+ "','"+cId+"',"+DbDateTime.getDbTimeStr()+","+Sequence.getCurrentSequenceStr("S_XT_RIZHI")+")";
		return str;
	}
	/**
	 * 将字符串做符合SQL标准的处理
	 * @param str 待处理的字符串
	 **/
	public String filter(String str) {
		if (str == null)
			return "";
		String rtn = str.replaceAll("\'", "''"); // 将单引号“'”做“''”处理
		return rtn;
	}


}

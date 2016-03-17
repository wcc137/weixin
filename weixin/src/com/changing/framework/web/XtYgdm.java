package com.changing.framework.web;

import java.io.Serializable;

/**
 * 表名: 员工代码表 T_XT_YGDM 类名: XtYgdm
 */
public class XtYgdm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 员工代码 ygdm
	private String ygdm;
	// 姓名 xm
	private String xm;
	// 用户名
	private String yhm;
	// 密码 mm
	private String mm;
	// 性别 xb
	private String xb;
	// 岗位代码
	private String gwdm;
	// 岗位名称
	private String gwmc;
	// 是否主管
	private String sfzg;
	// 邮件地址 yjdz
	private String yjdz;
	// 是否登录 sfdl
	private String sfdl;
	// 部门代码 bmdm
	private String bmdm;
	// 部门名称
	private String bmmc;
	// 部门代码 bmdm
	private String bzdm;
	// 部门名称
	private String bzmc;
	
	// 微信USERID
	private String wxUserId;
	
	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getBzdm() {
		return bzdm;
	}

	public void setBzdm(String bzdm) {
		this.bzdm = bzdm;
	}

	public String getBzmc() {
		return bzmc;
	}

	public void setBzmc(String bzmc) {
		this.bzmc = bzmc;
	}

	// 备注 bz
	private String bz;
	// 子系统管理员标识
	private String zxtglybs;
	// 子系统代码
	private String zxtDm;
	// 锁定标识 “1”为锁定，否则为非锁定状态
	private String sdbs;
	// 用户标识 1为是
	private String yhbs;
	// 显示顺序
	private int xssx;
	// temp1
	private String temp1;
	// temp2
	private String temp2;
	// temp3
	private String temp3;
	// temp4
	private String temp4;

	public String getZxtglybs() {
		return zxtglybs;
	}

	public void setZxtglybs(String zxtglybs) {
		this.zxtglybs = zxtglybs;
	}

	public int getXssx() {
		return xssx;
	}

	public void setXssx(int xssx) {
		this.xssx = xssx;
	}

	public String getSdbs() {
		return sdbs;
	}

	public void setSdbs(String sdbs) {
		this.sdbs = sdbs;
	}

	public String getYhbs() {
		return yhbs;
	}

	public void setYhbs(String yhbs) {
		this.yhbs = yhbs;
	}

	public XtYgdm() {
	}

	public String getYgdm() {
		return ygdm;
	}

	public void setYgdm(String _ygdm) {
		ygdm = _ygdm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String _xm) {
		xm = _xm;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String _mm) {
		mm = _mm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String _xb) {
		xb = _xb;
	}

	public String getYjdz() {
		return yjdz;
	}

	public void setYjdz(String _yjdz) {
		yjdz = _yjdz;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String _bmdm) {
		bmdm = _bmdm;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String _bz) {
		bz = _bz;
	}

	public String getSfdl() {
		return sfdl;
	}

	public void setSfdl(String sfdl) {
		this.sfdl = sfdl;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getTemp3() {
		return temp3;
	}

	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}

	public String getTemp4() {
		return temp4;
	}

	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}

	public String getZxtDm() {
		return zxtDm;
	}

	public void setZxtDm(String zxtDm) {
		this.zxtDm = zxtDm;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getGwdm() {
		return gwdm;
	}

	public void setGwdm(String gwdm) {
		this.gwdm = gwdm;
	}

	public String getGwmc() {
		return gwmc;
	}

	public void setGwmc(String gwmc) {
		this.gwmc = gwmc;
	}

	public String getSfzg() {
		return sfzg;
	}

	public void setSfzg(String sfzg) {
		this.sfzg = sfzg;
	}
}

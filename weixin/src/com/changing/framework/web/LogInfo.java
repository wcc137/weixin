package com.changing.framework.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class LogInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 系统用户信息
	private XtYgdm xtYgdm = new XtYgdm();
	// 用户个性化
	private UserGxh userGxh = new UserGxh();
	// 保存模块代码
	private Vector mkVector = new Vector();
	// 保存操作代码(查询页面注册按钮权限)
	private Vector czVector = new Vector();
	// 数据权限
	private HashMap sjqxHashMap = new HashMap(1000);
	//登录序号
	private String dlXh = "";
	//登录来源
	private String dlly = "";
	public String getDlly() {
		return dlly;
	}

	public void setDlly(String dlly) {
		this.dlly = dlly;
	}

	// 当前模块代码
	private String sDqmkdm = "";
	// 当前模块名称
	private String sDqmkmc = "";
	private String ip = "";
	private String sError = "";

	public String getSError() {
		return sError;
	}

	public void setSError(String error) {
		sError = error;
	}

	public Vector getCzVector() {
		return czVector;
	}

	public void setCzVector(Vector czVector) {
		this.czVector = czVector;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Vector getMkVector() {
		return mkVector;
	}

	public void setMkVector(Vector mkVector) {
		this.mkVector = mkVector;
	}

	public String getSDqmkdm() {
		return sDqmkdm;
	}

	public void setSDqmkdm(String sDqmkdm) {
		this.sDqmkdm = sDqmkdm;
	}

	public String getSDqmkmc() {
		return sDqmkmc;
	}

	public void setSDqmkmc(String sDqmkmc) {
		this.sDqmkmc = sDqmkmc;
	}

	public String getIp() {
		return ip;
	}

	public XtYgdm getXtYgdm() {
		return xtYgdm;
	}

	public void setXtYgdm(XtYgdm xtYgdm) {
		this.xtYgdm = xtYgdm;
	}

	public HashMap getSjqxHashMap() {
		return sjqxHashMap;
	}

	public void setSjqxHashMap(HashMap sjqxHashMap) {
		this.sjqxHashMap = sjqxHashMap;
	}

	public UserGxh getUserGxh() {
		return userGxh;
	}

	public void setUserGxh(UserGxh userGxh) {
		this.userGxh = userGxh;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDlXh() {
		return dlXh;
	}

	public void setDlXh(String dlXh) {
		this.dlXh = dlXh;
	}
}

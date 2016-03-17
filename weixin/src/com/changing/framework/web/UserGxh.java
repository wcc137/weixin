package com.changing.framework.web;

import java.io.Serializable;

public class UserGxh implements Serializable {
	private static final long serialVersionUID = 1L;
	//个性化设置
	private String GXH_BTDM = "1"; // 个性化是否有标题栏
	private int GXH_TOTALNUMBER = 20; // 个性化每页显示条数
	private String GXH_QSXX = "0"; // 个性化缺省提示信息
	private String GXH_TQTS = "1"; // 个性化提前提示天数
	// 0－自动，1－手动
	private String GXH_TSCK = "1"; // 个性化信息窗口弹出方式
	// 0- 关闭, 5- 5分种 时间单位为分钟
	// 见 bm=GXH_TSJG
	private String GXH_TSJG = "0"; // 个性化信息提醒间隔
	// 0-无声音, 1-幻想空间
	private String GXH_TSYY = "0"; // 个性化信息提示音乐
	private String GXH_YDTS = "2"; // 保留已读消息天数

	public String getGXH_BTDM() {
		return GXH_BTDM;
	}

	public void setGXH_BTDM(String gxh_btdm) {
		GXH_BTDM = gxh_btdm;
	}

	public String getGXH_QSXX() {
		return GXH_QSXX;
	}

	public void setGXH_QSXX(String gxh_qsxx) {
		GXH_QSXX = gxh_qsxx;
	}

	public String getGXH_TQTS() {
		return GXH_TQTS;
	}

	public void setGXH_TQTS(String gxh_tqts) {
		GXH_TQTS = gxh_tqts;
	}

	public String getGXH_TSCK() {
		return GXH_TSCK;
	}

	public void setGXH_TSCK(String gxh_tsck) {
		GXH_TSCK = gxh_tsck;
	}

	public String getGXH_TSJG() {
		return GXH_TSJG;
	}

	public void setGXH_TSJG(String gxh_tsjg) {
		GXH_TSJG = gxh_tsjg;
	}

	public String getGXH_TSYY() {
		return GXH_TSYY;
	}

	public void setGXH_TSYY(String gxh_tsyy) {
		GXH_TSYY = gxh_tsyy;
	}

	public String getGXH_YDTS() {
		return GXH_YDTS;
	}

	public void setGXH_YDTS(String gxh_ydts) {
		GXH_YDTS = gxh_ydts;
	}

	public int getGXH_TOTALNUMBER() {
		return GXH_TOTALNUMBER;
	}

	public void setGXH_TOTALNUMBER(int gxh_totalnumber) {
		GXH_TOTALNUMBER = gxh_totalnumber;
	}

}

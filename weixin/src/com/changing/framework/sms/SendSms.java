package com.changing.framework.sms;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.changing.common.systemmanager.ErrorMessage;
/**
 * 短信发送处理类
 * 
 * @author Administrator
 * 
 */
public class SendSms {
	
	private static String smsSeverIp = "";
	private static int smsSeverPort = 9992;
	
	public SendSms () {
		smsSeverIp = ErrorMessage.getErrorMessage("SMS_SERVER");
		smsSeverPort = Integer.parseInt(ErrorMessage.getErrorMessage("SMS_PORT") == null ?"9992":ErrorMessage.getErrorMessage("SMS_PORT"));
	}
	public SendSms (String serverIp) {
		smsSeverIp = serverIp;
	}
	public SendSms (String serverIp,int port) {
		smsSeverIp = serverIp;
		smsSeverPort = port;
	}
	

	/**
	 * 发送短信(无工号、无姓名)(多个发送号码 请用,号隔开)
	 * 
	 * @param jshm
	 *            接收号码
	 * @param fsnr
	 *            发送内容
	 * @param fssj
	 *            发送时间 格式:2012-12-12 23:59:59
	 * @param lybm 区分是哪个业务系统发送的
	  
	 * @param c_id 短信发送人
	 * 
	 * @parma c_dept 短信发送部门
	 */
	public void sendMsg(String jshm, String fsnr, String fssj,String lybm,String c_id,String c_dept) {
		String str = "FSDX@@" + lybm + "@@" + jshm + "@@" + fsnr + "@@" + fssj + "@@" + c_id + "@@" + c_dept;
		new Thread(new SendSmsSocketClient(str)).start();
	}
	/**
	 * 发送短信(有工号、姓名)(可按,号分割形式发给多个人,如果发给多个人,工号、姓名、电话号码个数必须一致)
	 * 
	 * @param gh
	 *            工号
	 * @param xm
	 *            姓名
	 * @param fshm
	 *            接收号码
	 * @param fsnr
	 *            发送内容
	 * @param fssj 
	 *            发送时间 格式:2012-12-12 23:59:59 为空表示立即发送
	 * @param lybm 区分是哪个业务系统发送的    
	 * 
	 * @param c_id 短信发送人
	 * 
	 * @parma c_dept 短信发送部门
	 */
	public void sendMsg(String gh, String xm, String jshm, String fsnr,String fssj,String lybm,String c_id,String c_dept) {
		String str = "FSDX@@" + lybm + "@@" + gh + "@@" + xm + "@@" + jshm + "@@" + fsnr + "@@" +  fssj + "@@" + c_id + "@@" + c_dept;
		new Thread(new SendSmsSocketClient(str)).start();
	}
	/**
	 * 工作流程系统审批操作时发送 (可按,号分割形式发给多个人,如果发给多个人,工号、姓名、电话号码个数必须一致)
	 *
	 * @param procNo 流程号
	 *
	 * @param actNo 节点号
	 *
	 * @param gh
	 *            工号
	 * @param xm
	 *            姓名
	 * @param jshm
	 *            发送号码
	 * @param fsnr
	 *            发送内容
	 * @param fssj 
	 *            发送时间 格式:2012-12-12 23:59:59 为空表示立即发送
	 * @param lybm 区分是哪个业务系统发送的
	 * 
	 * @param c_id 短信发送人
	 * 
	 * @parma c_dept 短信发送部门
	 */
	public void sendMsg(int procNo,int actNo,String gh, String xm, String jshm, String fsnr,String fssj,String c_id,String c_dept) {
		String str = "FSDX@@" + "MIS_WF" + "@@" + procNo + "@@" + actNo + "@@" + gh + "@@" + xm + "@@" + jshm + "@@" + fsnr + "@@" + fssj + "@@" + c_id + "@@" + c_dept ;
		new Thread(new SendSmsSocketClient(str)).start();
	}
	
	/**
	 * 发送回复信息(无工号、姓名) (可按,号分割形式发给多个人,如果发给多个人,工号、姓名、电话号码个数必须一致)
	 * 
	 * @param jshm
	 *            发送号码
	 * @param fsnr
	 *            发送内容
	 * @param fssj 
	 *            发送时间 格式:2012-12-12 23:59:59 为空表示立即发送
	 * @param lybm 区分是哪个业务系统发送的
	 * 
	 * @param dxjb 短信级别
	 * 
	 * @param hfdlxh 回复队列序号
	 * 
	 * @param c_id 短信发送人
	 * 
	 * @parma c_dept 短信发送部门
	 */
	public void sendHfMsg(String jshm, String fsnr,String fssj,String lybm,String dxjb,String hfdlxh,String c_id,String c_dept) {
		String str = "HFDX@@" + lybm + "@@" + jshm + "@@" + fsnr + "@@" + fssj + "@@" + dxjb + "@@" + hfdlxh + "@@" + c_id + "@@" + c_dept;
		new Thread(new SendSmsSocketClient(str)).start();
	}
	/**
	 * 发送回复信息(有工号、姓名) (可按,号分割形式发给多个人,如果发给多个人,工号、姓名、电话号码个数必须一致)
	 * @param gh
	 *            工号
	 * @param xm
	 *            姓名
	 * @param jshm
	 *            发送号码
	 * @param fsnr
	 *            发送内容
	 * @param fssj 
	 *            发送时间 格式:2012-12-12 23:59:59 为空表示立即发送
	 * @param lybm 区分是哪个业务系统发送的
	 * 
	 * @param dxjb 短信级别
	 * 
	 * @param hfdlxh 回复队列序号
	 * 
	 * @param c_id 短信发送人
	 * 
	 * @parma c_dept 短信发送部门
	 */
	public void sendHfMsg(String gh, String xm, String jshm, String fsnr,String fssj,String lybm,String dxjb,String hfdlxh,String c_id,String c_dept) {
		String str = "HFDX@@" + lybm + "@@" + gh + "@@" + xm + "@@" + jshm + "@@" + fsnr + "@@" + fssj + "@@" + dxjb + "@@" + hfdlxh + "@@" + c_id + "@@" + c_dept;
		new Thread(new SendSmsSocketClient(str)).start();
	}
	
	
	// 服务器进程
	class SendSmsSocketClient implements Runnable {
		Socket socket = null;
		String str = "";
		OutputStream netOut;
		DataOutputStream doc;

		public SendSmsSocketClient(String _str) {
			str = _str;
		}

		public void run() {
			try {
				socket = new Socket(smsSeverIp, smsSeverPort);
				netOut = socket.getOutputStream();
				// 向服务器端发送字符串 
				//来源编码@@工号@@姓名@@电话号码@@内容@@发送时间
				//或
				//来源编码@@电话号码@@内容@@发送时间
				doc = new DataOutputStream(netOut);
				doc.writeUTF(str);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (doc != null) {
					try {
						doc.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (netOut != null) {
					try {
						netOut.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SendSms sendSms = new SendSms("127.0.0.1",9992);
		sendSms.sendMsg("13798983717", "abc","MIS_WF","","SYS-XT","9999");
	}
}

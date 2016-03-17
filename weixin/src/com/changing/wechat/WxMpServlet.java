package com.changing.wechat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.WxMenu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.util.crypto.WxMpCryptUtil;

import com.changing.framework.helper.FileHelper;
import com.changing.framework.helper.LogHelper;

/**
 * 对应服务中心 企业消息 回调接口 企业调用接口将消息发送到成员的微信上。
 * 
 * @author Administrator
 */
@WebServlet({ "/wxmpp111" })
public class WxMpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected WxMpInMemoryConfigStorage config;
	protected WxMpService wxMpService;

	@Override
	public void init() throws ServletException {
		super.init();
		String appID = DyConfigUtil.get("appID");
		String appSecret = DyConfigUtil.get("appSecret");
		String token = DyConfigUtil.get("Token");
		String aeskey = DyConfigUtil.get("EncodingAESKey");
		System.out.println("服务中心->企业消息  回调接口,init方法... 只执行一次! 配置信息:appID=" + appID + ";appSecret=" + appSecret
				+ ";agentid=;token=" + token + ";aeskey=" + aeskey);
		LogHelper.logDebug(WxMpServlet.class, "服务中心->企业消息  回调接口,init方法... 只执行一次! 配置信息:appID=" + appID + ";appSecret="
				+ appSecret + ";agentid=;token=" + token + ";aeskey=" + aeskey);
		config = new WxMpInMemoryConfigStorage();
		config.setAppId(appID); // 设置微信公众号的appid
		config.setSecret(appSecret); // 设置微信公众号的app corpSecret
		config.setToken(token); // 设置微信公众号应用的token
		config.setAesKey(aeskey); // 设置微信公众号应用的EncodingAESKey
		wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(config);
		// 创建菜单
		menuCreat();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			response.getWriter().println("非法请求");
			return;
		}
		String echostr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echostr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			response.getWriter().println(echostr);
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String msgSignature = request.getParameter("msg_signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		LogHelper.logDebug(WxMpServlet.class,
				"服务中心->企业消息 ==>回调接口执行,POST方法...企业消息处理回复..." + msgSignature + request.getParameter("signature"));
		System.out.println(request.getParameter("msg_signature") + "     msg_signature      "
				+ request.getParameter("signature"));
		System.out.println(timestamp + "              " + nonce);
		String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request
				.getParameter("encrypt_type");
		WxMpXmlMessage inMessage = null;
		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
			System.out.println(inMessage.getFromUserName() + "--" + inMessage.getEventKey());

			try {
				if (inMessage.getEventKey().equals("EWM")) {
					WxMpQrCodeTicket ticket = wxMpService.qrCodeCreateLastTicket("1");
					File file = wxMpService.qrCodePicture(ticket);
					System.out.println(file.getPath() + "               " + ticket.getUrl());
					WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
					item.setDescription("二维码");
					item.setPicUrl(ticket.getUrl());
					// item.setPicUrl(ticket.getUrl());
					item.setTitle("二维码title");
					// item.setUrl(ticket.getUrl());
					item.setUrl(ticket.getUrl());
					// System.out.println("ticket   "+ticket.getTicket());
					WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS().fromUser(inMessage.getToUserName())
							.toUser(inMessage.getFromUserName()).addArticle(item).build();
					response.getWriter().write(m.toXml());
				}
				if (inMessage.getEventKey().equals("rselfmenu_0_1")) {
					WxMpXmlMessage.ScanCodeInfo codeInf = inMessage.getScanCodeInfo();
					System.out.println("扫码类型:" + codeInf.getScanType() + " 扫码结果: " + codeInf.getScanResult());
					/*无作用
					 * WxMpXmlOutMessage m = WxMpXmlOutMessage.TEXT().content("二维码扫描结果为：" + codeInf.getScanResult()) .fromUser(inMessage.getToUserName()).toUser(inMessage.getFromUserName()).build();
					 * response.getWriter().write(m.toXml());
					 */
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce,
					msgSignature);
		} else {
			response.getWriter().println("不可识别的加密类型");
			return;
		}
		/*
		 * WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
		 * 
		 * if (outMessage != null) { if ("raw".equals(encryptType)) { response.getWriter().write(outMessage.toXml()); } else if ("aes".equals(encryptType)) {
		 * response.getWriter().write(outMessage.toEncryptedXml(config)); } return; }
		 */
	}

	/**
	 * 显示html信息
	 * 
	 * @param response
	 * @param _msg
	 */
	public void showHtml(HttpServletResponse response, String _msgType, String _msg) {
		if (_msg == null)
			_msg = "";
		try {
			response.setContentType("text/html;charset=utf-8");
			java.io.PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"zh-CN\">");
			out.println("  <head>");
			out.println("<meta charset=\"utf-8\">");
			out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
			out.println("<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->");
			if (_msgType.equals("INFO")) {
				out.println("<title>提示消息</title>");
			} else if (_msgType.equals("ERROR")) {
				out.println("<title>错误消息</title>");
			} else if (_msgType.equals("SUCCESS")) {
				out.println("<title>成功消息</title>");
			}
			out.println(" </head>");
			out.println("<body>");
			if (_msgType.equals("INFO")) {
				out.println("<H3 style=\"color:#57addb;\">" + _msg + "</H3>");
			} else if (_msgType.equals("ERROR")) {
				out.println("<H3 style=\"color:#de4343;\">" + _msg + "</H3>");
			} else if (_msgType.equals("SUCCESS")) {
				out.println("<H3 style=\"color:#48bb5e;\">" + _msg + "</H3>");
			}
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void menuCreat() {
		WxMenu menu = new WxMenu();
		WxMenuButton button1 = new WxMenuButton();
		button1.setType("click");
		button1.setName("今日歌曲");
		button1.setKey("EWM");

		WxMenuButton button2 = new WxMenuButton();
		button2.setType("click");
		button2.setName("歌手简介");
		button2.setKey("V1001_TODAY_SINGER");

		WxMenuButton button3 = new WxMenuButton();
		button3.setName("菜单");

		menu.getButtons().add(button1);
		menu.getButtons().add(button2);
		menu.getButtons().add(button3);

		WxMenuButton button31 = new WxMenuButton();
		button31.setType("view");
		button31.setName("搜索");
		button31.setUrl("http://www.soso.com/");

		WxMenuButton button32 = new WxMenuButton();
		button32.setType("view");
		button32.setName("视频");
		button32.setUrl("http://v.qq.com/");

		WxMenuButton button33 = new WxMenuButton();
		button33.setType("click");
		button33.setName("赞一下我们");
		button33.setKey("V1001_GOOD");

		WxMenuButton button44 = new WxMenuButton();
		button44.setType("scancode_push");
		button44.setName("扫码推事件");
		button44.setKey("rselfmenu_0_1");

		button3.getSubButtons().add(button31);
		button3.getSubButtons().add(button32);
		button3.getSubButtons().add(button33);
		button3.getSubButtons().add(button44);
		// 创建菜单
		try {
			wxMpService.menuCreate(menu);
		} catch (WxErrorException e) {
			System.out.println("创建菜单失败！" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
}

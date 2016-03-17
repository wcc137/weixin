package com.changing.framework.web;
/**
 * 验证码生成类
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NumberYZM extends HttpServlet {
	private static final long serialVersionUID = 3807188334253495338L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Random random = new Random();
		int width = 60, height = 20;
		// 创建BufferedImage对象，设置图片的长度宽度和色彩。
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		OutputStream os = response.getOutputStream();
		// 取得Graphics对象，用来绘制图片
		Graphics g = image.getGraphics();
		// 绘制图片背景和文字,释放Graphics对象所占用的资源。
		g.setColor(getRandColor(200, 250));
		// 设置内容生成的位置
		g.fillRect(0, 0, width, height);
		// 设置内容的字体和大小
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 设置内容的颜色：主要为生成图片背景的线条
		g.setColor(getRandColor(160, 200));
		// 图片背景上随机生成155条线条，避免通过图片识别破解验证码
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		//生成四位的随机数,生成一个数，写一个
		String content = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			content += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		// 释放此图形的上下文以及它使用的所有系统资源，类似于关闭流
		g.dispose();
		// 将生成的验证码值(即运算结果的值)放到session中，以便于后台做验证。
		HttpSession session = request.getSession();
		session.setAttribute("safecode", content);
		// 通过ImageIO对象的write静态方法将图片输出。
		ImageIO.write(image, "JPEG", os);
		os.close();
	}

	/**
	 * 生成随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}

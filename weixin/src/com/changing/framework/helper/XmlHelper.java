/**
*@公司：          前景科技
*@系统名称：changing
*@文件名称：XmlHelper.java
*@功能描述:
*@创建人  ：zn
*@创建时间: 2011-8-5 下午03:04:55
*@完成时间：2011-8-5 下午03:04:55
*@修该人：
*@修改内容：
*@修改日期：
*/
package com.changing.framework.helper;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;



/**
 * @author Administrator <p>	
 * 功能描述:
 * <p>	
 * 使用示例：<p>	
 */
public class XmlHelper  {
	private Element rootElement = null;

	/**
	 * 检查一个String对象是否为空或者为空串，如果读取配置不正确，则写错误日志
	 * @param	fileName	配置文件路径
	 **/
	public XmlHelper (String fileName) throws Exception{
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(fileName);
		rootElement = doc.getRootElement();
		
	}
	
	public String getValue(String[] nodes) {
		if (nodes == null || nodes.length == 0)
			return null;
		else {
			Element e = rootElement;
			for (int i = 0; i < nodes.length; i++) {
				e = e.element(nodes[i]).createCopy();
				if (e == null)
					return null;
			}
			return e.getTextTrim();
		}
	}
	
	/**
	 * 获取节点对象
	 * @param nodes 节点路径
	 * @return 节点对象
	 */
	private Element getElement(String[] nodes) {
		if (nodes == null || nodes.length == 0)
			return null;
		else {
			Element e = rootElement;
			for (int i = 0; i < nodes.length; i++) {
				e = e.element(nodes[i]);
				if (e == null)
					return null;
			}
			return e;
		}
	}
	
	/**
	 * 获取参数集
	 * @param nodes 节点路径
	 * @return
	 */
	public Hashtable getChilds(String[] nodes) {
		Hashtable map = new Hashtable();
		if (nodes != null && nodes.length > 0) {
			Element e = this.getElement(nodes);
			if (e != null){
				int count = e.nodeCount();
				for (int i = 0; i < count; i++) {
					Node node = e.node(i);
					if (!node.hasContent())
						continue;
					map.put(node.getName(), node.getStringValue());
				}
			}
		}
		return map;
	}

	/**
	 * 根据二级节点名和三级节点名得到三级节点的值，如果节点为空或节点在文件中不存在，返回null值
	 * @param	node2	二级节点名
	 * @param	node3	三级节点名
	 **/
	public String getValue(String node2, String node3) {
		if(node2 == null || (node2=node2.trim()).equals(""))
			return null;
		if(node3 == null || (node3=node3.trim()).equals(""))
			return null;
		return rootElement.element(node2).element(node3).getText().trim();
	}

	/**
	 * 根据二级节点名二级节点的值，如果节点为空或节点在文件中不存在，返回null值
	 * @param	node2	二级节点名
	 **/
	public String getValue(String node2) {
		if(node2 == null || (node2=node2.trim()).equals(""))
			return null;
		return rootElement.element(node2).getText().trim();
		
	}
	/**
	 * 返回只包含二级的数据的xml文档的所有以map集合形式存储的数据
	 */
	public HashMap getMap() {
		HashMap map = new HashMap();
		String key = "";
		Iterator it = rootElement.elements().iterator();
		while (it.hasNext()) {
			Element element = (Element)it.next();
			key = element.getName();
			map.put(key, rootElement.element(key).getText().trim());
		}
		return map;
	}
}

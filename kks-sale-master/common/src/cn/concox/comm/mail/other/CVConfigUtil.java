package cn.concox.comm.mail.other;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CVConfigUtil {
	public static Map<String, String> dynamicConfigsCach = null;
	private static Logger log = Logger.getLogger(CVConfigUtil.class);

	static {
		if (dynamicConfigsCach == null) {
			try {
				dynamicConfigsCach = new HashMap<String, String>();
				new CVConfigUtil().parseConfig2Map("config.xml");
			} catch (Exception e) {
				log.error("加载config.xml出错，系统错误：" + e);
				e.printStackTrace();
			}
		}
	}

	private void parseConfig2Map(String configPath) throws Exception {
		log.info("正在加载配置参数.....");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
			Document doc = builder.parse(in);
			NodeList nodes = doc.getChildNodes().item(0).getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					String nodeValue = "";
					if (null != nodes.item(i).getFirstChild()) {
						nodeValue = nodes.item(i).getFirstChild().getNodeValue();
					}
					dynamicConfigsCach.put(nodes.item(i).getNodeName(), nodeValue);
				}

			}
			// 加载其它配置文件
			String configs = get("import");
			if (null != configs && configs.trim().equals("")) {
				String[] configPathArray = configs.split(",");
				for (String path : configPathArray) {
					try {
						parseConfig2Map(path);
					} catch (Exception e) {
						throw e;
					}
				}
			}
			log.info("主参数文件[config.xml]读取完毕!");
		} catch (SAXException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (ParserConfigurationException e) {
			throw e;
		}
	}

	/**
	 * 获取配置文件中的值 Create Date:2012-9-5
	 * 
	 * @param name
	 * @return String
	 * @author: lizhongjie
	 */
	public static String get(String name) {
		return dynamicConfigsCach.get(name);
	}

	/**
	 * 获取配置文件中的值
	 * 
	 * @author Li Zhongjie create date: 2013-5-26 下午6:39:45
	 * @param name
	 * @return int
	 */
	public static int getInt(String name) {
		return Integer.valueOf(dynamicConfigsCach.get(name));
	}

	public static boolean getBoolean(String name) {
		if (!dynamicConfigsCach.containsKey(name))
			return false;
		try {
			return Boolean.valueOf(dynamicConfigsCach.get(name));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取配置文件中的值
	 * 
	 * @author Li Zhongjie create date: 2013-5-26 下午6:39:45
	 * @param name
	 * @return Double
	 */
	public static Double getDouble(String name) {
		return Double.valueOf(dynamicConfigsCach.get(name));
	}

	/**
	 * 获取配置文件中的值，并获取值的第index个值，以","分隔的值。从0开始。 如配置：<user>name,password</user> 使用getIndexValue("user",1)，获得 password
	 * 
	 * @author Li Zhongjie create date: 2013-5-26 下午6:43:29
	 * @param name
	 * @param index
	 * @return String
	 */
	public static String getIndexValue(String name, int index) {
		return dynamicConfigsCach.get(name).split(",")[index];
	}

	/**
	 * 获取配置文件中的值，并获取值的第index个值，以","分隔的值。从0开始。 如配置：<user>name,password</user> 使用getIndexValue("user",1)，获得 password
	 * 
	 * @author Li Zhongjie create date: 2013-5-26 下午6:43:29
	 * @param name
	 * @param index
	 * @return int
	 */
	public static int getIndexIntValue(String name, int index) {
		return Integer.valueOf(dynamicConfigsCach.get(name).split(",")[index]);
	}

	/**
	 * 获取已英文逗号分割的邮件地址列表。
	 * 
	 * @param name
	 * @return
	 */
	public static String[] getStrArray(String name) {
		return dynamicConfigsCach.get(name).split(",");
	}
	
	public static void main(String[] args) {
		String s = CVConfigUtil.get("business-send-email");
		System.out.println(s);
	}
}
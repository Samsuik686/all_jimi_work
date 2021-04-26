/*
 * Created: 2016-09-13
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.wechat.uti;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息工具类 用于解析微信平台消息xml报文
 */
public final class RequestUtil {

	private static final Logger LOG = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 此类不需要实例化
	 */
	private RequestUtil() {
	}

	/**
	 * 解析从微信服务器来的请求，将消息或者事件返回出去
	 *
	 * @param request
	 *            http请求对象
	 * @param token
	 *            用户设置的taken
	 * @param appId
	 *            公众号的APPID
	 * @param aesKey
	 *            用户设置的加密密钥
	 * @return 微信消息或者事件Map
	 */
	public static Map<String, Object> parseXml(HttpServletRequest request, String token, String appId, String aesKey) {
		Map<String, Object> map = new HashMap<String, Object>();

		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);// 会完全禁止DTD
            factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false); // 禁用外部实体
            XMLEventReader reader = factory.createXMLEventReader(inputStream);
            LOG.error("reader为：{}", reader.toString());
			while (reader.hasNext()) {
				XMLEvent event = reader.nextEvent();
				if (event.isStartElement()) {
					String tagName = event.asStartElement().getName().toString();
					if ("SendPicsInfo".equals(tagName)) {
						map.put(tagName, eventSendPicsInfo(reader));
					} else if ("SendLocationInfo".equals(tagName)) {
						map.put(tagName, eventSendLocationInfo(reader));
					} else if ("ScanCodeInfo".equals(tagName)) {
						map.put(tagName, eventScanCodePush(reader));
					} else if ("xml".equals(tagName)) {
					} else {
						map.put(tagName, reader.getElementText());
					}
				}
			}
		} catch (IOException e) {
			LOG.error("IO出现异常", e);
		} catch (XMLStreamException e) {
			LOG.error("XML解析出现异常", e);
		} catch (Exception e) {
			LOG.error("其他异常", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOG.error("IO出现异常", e);
			}
		}
		return map;
	}

	/**
	 * Event为pic_sysphoto, pic_photo_or_album, pic_weixin时触发
	 * 
	 * @param reader
	 *            reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventSendPicsInfo(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> sendPicsInfoMap = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				if ("Count".equals(tagName)) {
					sendPicsInfoMap.put(tagName, reader.getElementText());
				} else if ("PicList".equals(tagName)) {
					StringBuilder sb = new StringBuilder();
					while (reader.hasNext()) {
						XMLEvent event1 = reader.nextEvent();
						if (event1.isStartElement() && "PicMd5Sum".equals(event1.asStartElement().getName().toString())) {
							sb.append(reader.getElementText());
							sb.append(",");
						} else if (event1.isEndElement() && "PicList".equals(event1.asEndElement().getName().toString())) {
							break;
						}
					}
					sendPicsInfoMap.put(tagName, sb.substring(0, sb.length()));
				}
			}
		}

		return sendPicsInfoMap;
	}

	/**
	 * Event为location_select时触发
	 * 
	 * @param reader
	 *            reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventSendLocationInfo(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> sendLocationInfo = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				sendLocationInfo.put(tagName, reader.getElementText());
			}
		}
		return sendLocationInfo;
	}

	/**
	 * Event为scancode_push, scancode_waitmsg时触发
	 * 
	 * @param reader
	 *            reader
	 * @return 读取结果
	 * @throws XMLStreamException
	 */
	protected static Map<String, Object> eventScanCodePush(XMLEventReader reader) throws XMLStreamException {
		Map<String, Object> scanCodePush = new HashMap<String, Object>();
		while (reader.hasNext()) {
			XMLEvent event = reader.nextEvent();
			if (event.isStartElement()) {
				String tagName = event.asStartElement().getName().toString();
				scanCodePush.put(tagName, reader.getElementText());
			}
		}
		return scanCodePush;
	}
}

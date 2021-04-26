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
package cn.concox.wechat.api;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.concox.wechat.uti.WeChatGlobals.WeChatInfo;
import cn.concox.wechat.uti.pay.CommonUtil;
import cn.concox.wechat.uti.pay.DateUtil;
import cn.concox.wechat.uti.pay.Dom4jUtils;
import cn.concox.wechat.uti.pay.HttpTools;
import cn.concox.wechat.uti.pay.MD5;
import cn.concox.wechat.uti.pay.ObjectTools;
import cn.concox.wechat.uti.pay.entity.ScanIn;
import cn.concox.wechat.uti.pay.entity.WeChatAPI;
@Transactional(rollbackFor = Exception.class)
@Service
public class WeChatPayService {

	private static Logger log = Logger.getLogger(WeChatPayService.class);
	
	private static HttpTools httpTools = new HttpTools();

	/**
	 * 
	 * 获取二维码路径接口
	 * @author Li.Shangzhi
	 * @date  2016-09-19 10:20:26
	 * @param outTradeNo
	 * @param body
	 * @param totalFee
	 * @param ip
	 * @param timeExpire
	 * @return
	 * @throws Exception 
	 * @throws LezcashierException
	 */
	public String getQrcodeUrl(String outTradeNo, String body, BigDecimal totalFee, String ip, Date timeExpire) throws Exception {
		String xml = createScanXml(outTradeNo, "WEB", body, totalFee, "172.16.0.189", timeExpire);
		log.info(xml);
		String result = httpTools.post(WeChatInfo.URL_UNIFIEDORDER, xml);
		log.info("获取扫描接口响应数据:" + result);
		String status = Dom4jUtils.findAppointDocVal(result, "return_code");
		if ("SUCCESS".equals(status)) {
			String resultCode = Dom4jUtils.findAppointDocVal(result, "result_code");
			if ("SUCCESS".equals(resultCode)) {
				String codeUrl = Dom4jUtils.findAppointDocVal(result, "code_url");
				return codeUrl;
			} else {
				String errCode = Dom4jUtils.findAppointDocVal(result, "err_code");
				String errMsg = Dom4jUtils.findAppointDocVal(result, "err_code_des");
				throw new Exception(errCode+":"+errMsg);
			}
		} else {
			String message = Dom4jUtils.findAppointDocVal(result, "return_msg");
			throw new Exception(message);
		}
	}

	/**
	 * 创建XML 数据参数
	 * @param outTradeNo
	 * @param deviceInfo
	 * @param body
	 * @param totalFee
	 * @param ip
	 * @param timeExpire
	 * @return
	 * @throws Exception
	 */
	private String createScanXml(String outTradeNo, String deviceInfo, String body, BigDecimal totalFee, String ip,
			Date timeExpire) throws Exception {
		ScanIn scanIn = new ScanIn();
		scanIn.setOut_trade_no(outTradeNo);
		scanIn.setBody(body);
		scanIn.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue());
		scanIn.setTime_start(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
		if (scanIn.getTime_expire() != null) {
			scanIn.setTime_expire(DateUtil.format(timeExpire, "yyyyMMddHHmmss"));
		}
		scanIn.setNonce_str(CommonUtil.CreateNoncestr(32));
		scanIn.setSpbill_create_ip(ip);
		return toXML(scanIn);
	}

	/**
	 * 封装XML
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private String toXML(Object obj) throws Exception {
		try {
			Map<String, Object> map = ObjectTools.object2Map(obj);
			map.remove("sign");
			String strPara = CommonUtil.FormatQueryParaMap(map, false);
			String sign = MD5.sign(strPara, WeChatInfo.MCH_KEY, WeChatInfo.CHARSET);
			map.put("sign", sign);
			String xml = arrayToXml(map);
			return xml;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 装CDATA 格式编码
	 * @param arr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String arrayToXml(Map<String, Object> arr) throws UnsupportedEncodingException {
		String xml = "<xml>";

		Iterator<Entry<String, Object>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue().toString();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}
	
	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 微信支付回调
	 * @return
	 * @throws Exception
	 */
	public static void WeChatServiceAPI() throws Exception{
		//TODO 同步返回给微信
		WeChatAPI api = new WeChatAPI();
		api.setReturn_code("SUCCESS");
		api.setReturn_msg("OK");
		
		Map<String, Object> map = ObjectTools.object2Map(api);
		String xml = WeChatPayService.arrayToXml(map);

		httpTools.post(WeChatInfo.URL_UNIFIEDORDER, xml);
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
//		WeChatPayService ws = new WeChatPayService();
//		String qr = ws.getQrcodeUrl("TEST" + DateUtil.format(new Date(), "yyyyMMddHHmmss"),WeChatInfo.PRODUCT, new BigDecimal(0.01), "172.16.0.189",null);
//		System.out.println(qr);
//		QRCode test = new QRCode();
//		String filePostfix="png";
//		File file = new File("C://Users//Administrator//Desktop//img//QR_CODE."+filePostfix);
//		test.encode(qr, file,filePostfix, BarcodeFormat.QR_CODE, 500, 500, null);
		
		WeChatServiceAPI();
	}
}

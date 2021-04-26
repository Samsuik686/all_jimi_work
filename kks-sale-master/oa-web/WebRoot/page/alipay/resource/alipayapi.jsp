<%@page import="java.math.BigDecimal"%>
<%
/* *
 *功能：即时到账交易接口接入页
 *版本：1.0
 *修改日期：2016-08-29 
 *作者：Li.Shangzhi
 *说明：
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.concox.comm.alipay.config.*"%>
<%@ page import="cn.concox.comm.alipay.util.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="org.apache.log4j.Logger" %>
<!DOCTYPE html>
<html>      
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>几米终端客户服务</title>
	</head>
	<%
	request.setAttribute("path", request.getServerName());
	
	/*测试环境 */
	/* String baseUrl=request.getScheme() + "://" + "113.108.62.204:31120" + request.getContextPath(); */

	/*测试环境 */
//	 String baseUrl=request.getScheme() + "://" + "172.16.10.113:12287" + request.getContextPath();
	//线上环境
	String baseUrl=request.getScheme() + "://" + "www.concox400.com" + request.getContextPath();

	%>
	
	<%
		Logger logger=Logger.getLogger("workflowInfo");
		/////////////服务端配置信息
		//服务器异步通知
		String notify_url = baseUrl+"/custClient/alipayback";
		logger.info("notify_url："+notify_url);
		//页面跳转同步
		String return_url = baseUrl+"/page/alipay/ClientIndex.jsp";
		logger.info("out_trade_no：" + (request.getParameter("out_trade_no")));
		logger.info("total_fee：" + (request.getParameter("total_fee")));
	    //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		String total_fee    = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", notify_url);
 		sParaTemp.put("return_url", return_url);
		//订单信息
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", AlipayConfig.subject);
		sParaTemp.put("total_fee",total_fee);
		sParaTemp.put("body", AlipayConfig.body);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		out.println(sHtmlText);
	%>
	<body>
	</body>
</html>

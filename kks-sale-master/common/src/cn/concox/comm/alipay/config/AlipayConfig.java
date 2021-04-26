package cn.concox.comm.alipay.config;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *修改日期：2016-08-29 
 *作者：Li.Shangzhi
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// -----------------------原康凯斯账户信息---------------------
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
//	public static String partner = "2088421844215634";
//
//	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
//	public static String seller_id = partner;
//
//	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
//	public static String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK2CqWz/EIML+5DPxONqzis5OT0mRSBG12jm3k1WC7evModHE8/avbEVjgAZrHWYEEU1Y20zw5wiO7Chw9wWyiSz2mkGn+HTK8x7MfiVd6D6lXT6dDLHWChEN4Hb4w9V4rLpQWVr7hfwBaSvJqxOK/hnFa+VHDs+TT9+HdHm3B0jAgMBAAECgYBNziXDZq001o68ZS7Y++/vmf182khtLNczhzNwYoFmoP17g/fyYRhm7E985AZdxQ6mukYXlfrFbORvVFrLurxx87QOJMBkmyur6/7s8NyTe7seCLlRKoDd9eTUmzjWCGdEw4cvtolenjzjlNGFd7RVE0Y16DDXGbcglzP30ktfgQJBANvwbFdIq+168yqjuXMvZt8IJ6HIPwcOrQawP358RzbsqQSTccH5RRMhZyr2gs7AAL+ks6NATWl20o/4qRO+XNECQQDJ9XgzTufijWuX5ETH0EFsFsgjeGa0G9TezDGsjgpHeTd627Vzurp5sc4R48OIdy4wR9KrGf4dvzvrSHcQn4ezAkEAoVnVMYd30gYEBMEOGQmqb27dHFv6mSPUHfcI7bMgz5N5P4knyIUlfq5fsG4ecqz7H50lyFsxL3DjcombCIf8MQJBAKxfVrh0IHa9PZfmvM3OcghPaYLcwMv49Tc0ETG2Cg/25eN2ouij94TmvXuHatyO3F1Y7/YZuUqqbfnjX7xQbQECQQCxvF+HDg+Spulv2ESYDq6M5d2/QhJImmdSJCMzCHb8JiDtDYdUMf5yrJg3OZ1bhHS45BzaAMkpOT4c3GOY650y";
//
//	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
//	// public static String alipay_public_key =
//	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
//	public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	// -----------------------现几米账户信息-----------------------
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088731931771575";

	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKieEznIgtVjyNPTtS2Ju0alyy2JbXcT0MfORhbvi/KO8U4Aw9nt8QRQlPjH34M+r/NailZ9TKgd+DA7RwRMm4wHHDgE4JRC9+Fae4oiQfAFSXLaxtT5puWVzx5PkmKzdSsfgqNIrs+f/Hj9r3Nt3EM+ay/PCLzluQAR21pU2Yk1AgMBAAECgYAw7ubvbvQhDzs51gePNphFsVmcVnSRCQwe4ArRHyWC996pX2l29ilqsiP5aq+/u9J+Eflxjh1P8JqVDK7YXY8KBHNHjqOyI1khMsC/LWHa72H/Jk9JOtc4EAZ9lhwBBEHd83mChFDya0DPzEM4zfehfBTEg++apXnBVirN8Wx8BQJBANAwnGdwsPg/GkyX6D/jgq1VOu6WhR3DzrYOcVJMU43a4AuQCl+eUyYt0iG2yjmMZCrrYQx3EG/EpgXlMFBILlMCQQDPVwU2J0Jtm2nYAjcFlJLF86xBVQ0UwB75Su5RNCRUaz86c34WJmBJk35Q6dqS6wV0hGO6c28l+1329jP5r6lXAkEAj/rOcAtTmJuriIUWLfkeCtoC47ZdYXRlpSrf3FbFAjn48IYxZmeMgb72sDJGMP6S18vpVkGRsHqDh1g1zY/d2QJBAJotTk3Hgcj5YQpkE0AOC9ow6S0nrnN4rU+Y3X/6su0nVrwDN4YHAWCU0cAfNQ48vzC2ZWY3AjaPJX9H8rw/WD8CQQC7SBkhLHQ5vMxN+OlXmX+sOTL266VdV3JQAsRiOBzJh4L0UDGfuwVDOvrd1+N9z1kFQJAAu5Gx/TIXGZrxJ2/Z";

	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	// public static String alipay_public_key =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 签名方式
	public static String sign_type = "RSA";

	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "F:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 支付类型 ，无需修改
	public static String payment_type = "1";

	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/*
	 * 详细：收款方配置信息 版本：1.0 修改日期：2016-08-29 15:43:08 作者：Li.Shangzhi 说明：
	 **/
	// ↓↓↓↓↓↓↓↓↓↓ 开发者 管理商开发配置中心 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

//	public static String subject = "康凯斯终端客户服务";
//
//	public static String body = "康凯斯设备终端维修";
	
	public static String subject = "几米终端客户服务";

	public static String body = "几米设备终端维修";

	// ↑↑↑↑↑↑↑↑↑↑ 开发者 管理商开发配置中心 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

package cn.concox.comm.util;
/**
 * 送修批号管理
 * @author Li.Shangzhi
 * @date 2016年8月2日
 */
public class BatchNumUtil {
	
	public static String getBatchNum(){
		return String.valueOf(System.currentTimeMillis());  
	}
	
	//1470106806062
	//1470106866211
	//1470106997828
	//1470107007940
	
//	SELECT  repairnNmber FROM `t_sale_workflow` WHERE sxdwId ='40' ORDER BY acceptanceTime DESC LIMIT 1
	
	public static void main(String[] args) {
		System.out.println(BatchNumUtil.getBatchNum()); 
	}
}

package cn.concox.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.concox.comm.Globals;

public class HttpParamsParse {

	public static int getIndexByChar(String srcStr,char s,int counts){
		int count=0;
		char arr[] = srcStr.toCharArray();
		for(int i=0;i<arr.length;i++)
		{
		    if(s==arr[i])
			{
			      count++;
			}
		    
		    if(count==counts){
		    	  return i;
		    }
		}
		
		return -1;
	}
	
	public static String getReviewStatusDesc(String getStatus){
		if("0".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC0;
		}else if("1".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC1;
		}else if("2".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC2;
		}else if("3".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC3;
		}else if("4".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC4;
		}else if("5".equals(getStatus)){
			return Globals.REVIEWSTATUS_DESC5;
		}
		return "";
	}
	
	public static String getIsNotAssessStatusDesc(String getIsNotAssessStatus){
		if("0".equals(getIsNotAssessStatus)){
			return Globals.ISNOTASSESS_DESC0;
		}else if("1".equals(getIsNotAssessStatus)){
			return Globals.ISNOTASSESS_DESC1;
		}
		return "";
	}
	
	
	public static String getUStatusDesc(String getUStatus){
		if("0".equals(getUStatus)){
			return Globals.USTATUS_DESC0;
		}else if("1".equals(getUStatus)){
			return Globals.USTATUS_DESC1;
		}else if("2".equals(getUStatus)){
			return Globals.USTATUS_DESC2;
		}
		return "";
	}
	
	public static int getTotalNumsByChar(String srcStr,char s){
		int count=0;
		if(!StringUtils.isBlank(srcStr)){
			if(srcStr.indexOf(",")!=-1){
				char arr[] = srcStr.toCharArray();
				for(int i=0;i<arr.length;i++)
				{
				    if(s==arr[i])
					{
					      count++;
					}
				}
				count=count+1;
			}else {
				count=1;
			}
			
		}
		return count;
	}
	
	public static void main(String[] args){
		String str="/oa-/web/system/menuselect/aa.pdf";
		/*int indexs=HttpParamsParse.getIndexByChar(str, '/', 3);
		System.out.println("indexs="+indexs);
		
		
		String str2="张世明,吴南强,杨清平,胡桃,龚文娟,王佳书";
		int getTotalNums=HttpParamsParse.getTotalNumsByChar(str2,',');
		System.out.println("getTotalNums="+getTotalNums);*/
		
		String getFileName=str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
		System.out.println("getFileName="+getFileName);
		
	}
}

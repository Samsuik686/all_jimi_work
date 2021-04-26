package cn.concox.app.user.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sso.comm.model.UserInfo;
import sso.comm.util.SSOUtils;
import cn.concox.comm.base.controller.BaseController;
 

@Controller
@Scope("prototype")
public class UserAddressList extends BaseController{

Logger log=Logger.getLogger(UserAddressList.class);
	
	@RequestMapping("/Address/export")
	public void OrganSelectList(@ModelAttribute UserInfo userInfo,HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
 
		SSOUtils utils=new SSOUtils();
		
		   
		 String[] excelHeader = {"工号","账号","姓名","主部门","职务","直接领导","手机号码","固定电话","邮箱","QQ","企业QQ"};  
		//	List<UserManager> um=new ArrayList<UserManager>();

			log.info("通讯录导出开始");
			try { 
			  if(userInfo.getoId()==0)
				userInfo.setoId(null);
			  
			  if(userInfo.getuName().equals(""))
				userInfo.setuName(null);
			   userInfo.setuStatus(0);  
			   List<UserInfo> result=utils.getUserInfoList(userInfo);
		        HSSFWorkbook wb = new HSSFWorkbook();    
		        HSSFSheet sheet = wb.createSheet("员工通讯录");    
		        HSSFRow row = sheet.createRow((int) 0);    
		        HSSFCellStyle style = wb.createCellStyle();    
		         
		        Font font = wb.createFont();  
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体  
		        style.setFont(font);  
		        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
		        row.setHeight((short) (15.625*20));
		        
		        for (int i = 0; i < excelHeader.length; i++) {    
		            HSSFCell cell = row.createCell(i);    
		            cell.setCellValue(excelHeader[i]);    
		            cell.setCellStyle(style);    
		            sheet.setColumnWidth(i, 5000);
		        }    
		    
		      for (int i = 0; i < result.size(); i++) {    
		            row = sheet.createRow(i + 1);    
		            UserInfo salarym = result.get(i); 
		            row.createCell(0).setCellValue(salarym.getcId());
		            row.createCell(1).setCellValue(salarym.getuId()); 
		            row.createCell(2).setCellValue(salarym.getuName()); 
		            row.createCell(3).setCellValue(salarym.getoName()); 
		            row.createCell(4).setCellValue(salarym.getuPosition());
		            row.createCell(5).setCellValue(salarym.getuManagerName());
		            row.createCell(6).setCellValue(salarym.getuTelphone()); 
		            row.createCell(7).setCellValue(salarym.getuPhone()); 
		            row.createCell(8).setCellValue(salarym.getuMail());  
		            row.createCell(9).setCellValue(salarym.getuQQ()); 
		            row.createCell(10).setCellValue(salarym.getuEnterpriseQQ()); 
		            
		        } 
		      SimpleDateFormat simper=new SimpleDateFormat("yyyyMMddHHmmss");
		      response.setContentType("application/vnd.ms-excel");    
		        response.setHeader("Content-disposition", "attachment;filename=OA_AddressBook_"+simper.format(new Date())+".xls");    
		        OutputStream ouputStream = response.getOutputStream();    
		        wb.write(ouputStream);    
		        ouputStream.flush();    
		        ouputStream.close();  
		    } 
	         catch(Exception e) 
	         {
	        
	        	 log.error("------------"+e.getMessage());  
	        	 log.error("通讯录导出失败");
	         }
	        
	}
}

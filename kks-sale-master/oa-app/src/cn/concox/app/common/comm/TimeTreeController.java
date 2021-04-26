package cn.concox.app.common.comm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.commvo.TimeTree;
@Controller
@Scope("prototype")
public class TimeTreeController extends BaseController {

	Logger logger=Logger.getLogger("privilege");
	
	/**
	 * 获取分组列表信息
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping("timetree/queryList")
	@ResponseBody
	public APIContent queryList(HttpServletRequest req ){
		logger.info("开始获取列表信息");
		try{
			int num;
			String numStr;
			numStr = req.getParameter("num");
			if(numStr != null){
				num = Integer.valueOf(numStr);
			}else{
				num = 10;
			}
			return super.putAPIData(getTimeTree(num));
		}catch(Exception e){
			logger.info("获取获取列表信息失败:"+e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE,Globals.OPERA_FAIL_CODE_DESC); 
		}
	}


	/**
	 * 获取本周日期树
	 * 
	 * @return List<TimeTree>
	 */
	public static List<TimeTree> getTimeTree(Integer num){
		List<TimeTree> timeTrees = new ArrayList<TimeTree>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<num;i++){
		   TimeTree tree = new TimeTree();
		   Calendar cal =Calendar.getInstance();
		   cal.add(Calendar.DAY_OF_YEAR, -i);
		   tree.setId(sdf.format(cal.getTime()));
		   tree.setText(sdf.format(cal.getTime()));
		   timeTrees.add(tree);		  
		}
		return timeTrees;
		
		
	}
	public static void main(String[] args) {
		getTimeTree(10);
	}
}

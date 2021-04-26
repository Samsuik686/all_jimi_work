package cn.concox.app.basicdata.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.LockIdUrlService;
import cn.concox.app.basicdata.service.QueryRepairStateService;
import cn.concox.comm.Globals;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.util.StringUtil;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.basicdata.LockIdUrl;
import cn.concox.vo.basicdata.QueryRepairState;

@Controller
@Scope("prototype")
public class QueryRepairStateController extends BaseController {
	private static Logger log = Logger.getLogger(QueryRepairStateController.class);

	@Resource(name = "queryRepairStateService")
	public QueryRepairStateService queryRepairStateService;
	
	@Resource(name="lockIdUrlService")
	private LockIdUrlService lockIdUrlService;
	
	@RequestMapping("queryRepairState/getList")
	@ResponseBody
	public APIContent getList(@ModelAttribute QueryRepairState queryRepairState, HttpServletRequest request) {
		try {
			String imei = request.getParameter("s_imei");
			if(!StringUtil.isRealEmpty(imei)){
				if(imei.trim().length() == 15){
					queryRepairState.setImei(imei);
				}else{
					LockIdUrl lockIdUrl = lockIdUrlService.matchLongStr(imei.trim());
					if(null != lockIdUrl && null != lockIdUrl.getLockInfo()){//输入信息（imei或智能锁id或只能锁id二维码）
						 if(StringUtil.isDigit(imei.trim())){//输入数字小于15位，默认是智能锁id
							 lockIdUrl.setLockId(imei.trim());
						 }
					}
					if(null != lockIdUrl.getLockId()){
						queryRepairState.setLockId(lockIdUrl.getLockId());
					}
				}
			}
			List<QueryRepairState> c = queryRepairStateService.queryList(queryRepairState);
			return super.putAPIData(c);
		} catch (Exception e) {
			log.error("获取维修状态查询数据失败" + e.toString());
			return super.operaStatus(Globals.OPERA_FAIL_CODE, "获取维修状态查询数据" + Globals.OPERA_FAIL_CODE_DESC);
		}
	}
}

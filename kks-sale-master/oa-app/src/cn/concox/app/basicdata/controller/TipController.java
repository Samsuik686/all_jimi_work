package cn.concox.app.basicdata.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.basicdata.service.TipService;
import cn.concox.app.basicdata.service.TipService.TipState;
import cn.concox.vo.basicdata.Tip;

@Controller
@RequestMapping("/tip")
public class TipController {

	
	@Resource(name="tipService")
	private TipService tipService;
	
	@RequestMapping("/findTipNum")
	@ResponseBody
	public Integer findTipNum(String enumWS){
		Integer result = 0;
		Tip tip = tipService.findTipByEnumWS(TipState.valueOf(enumWS));
		if(null != tip){
			result = tip.getCountNum();
		}
		return result;
	}
	
	@RequestMapping("/resetTip")
	@ResponseBody
	public String resetTip(String enumWS){
		String result = "ok";
		try {
			tipService.resetTip(TipState.valueOf(enumWS));
		} catch (Exception e) {
			e.printStackTrace();
			result = "系统异常";
		}
		return result;
	}
}

package cn.concox.app.basicdata.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.TipMapper;
import cn.concox.vo.basicdata.Tip;

@Service("tipService")
@Scope("prototype")
public class TipService {
	
	@Resource(name="tipMapper")
	private TipMapper<Tip> tipMapper;

	
	public void addTip(TipState tipState){
		addTip(tipState,1);
	}
	
	public void addTip(TipState tipState,Integer num){
		Tip tip = findTipByEnumWS(tipState);
		if(null != tip){
			tip.setCountNum(tip.getCountNum()+num);
			tipMapper.update(tip);
		}else{
			tip = new Tip();
			tip.setEnumWS(tipState.name());
			tip.setCountNum(num);
			tipMapper.insert(tip);
		}
	}
	
	public void resetTip(TipState tipState){
		Tip tip = findTipByEnumWS(tipState);
		if(null != tip){
			tip.setCountNum(0);
			tipMapper.update(tip);
		}
	}
	
	public Tip findTipByEnumWS(TipState tipState) { 
		Tip tip = new Tip();
		tip.setEnumWS(tipState.name());
		return tipMapper.findTipCountByEnumWS(tip);
	}
	
	public enum TipState{
		TIP_SL,TIP_FJ,TIP_WX,TIP_BJ,TIP_ZJ,TIP_ZX,TIP_TEST;
	}
	
}

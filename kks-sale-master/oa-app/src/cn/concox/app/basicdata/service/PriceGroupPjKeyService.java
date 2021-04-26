package cn.concox.app.basicdata.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.PriceGroupPjKeyMapper;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.PriceGroupPjKey;
/**
 * 报价配件料组合关键字 业务层
 */
@Service("priceGroupPjKeyService")
@Scope("prototype")
public class PriceGroupPjKeyService {
	@Resource(name="priceGroupPjKeyMapper")
	private PriceGroupPjKeyMapper<PriceGroupPjKey> priceGroupPjKeyMapper;
	
	
	public List<PriceGroupPjKey> queryList(PriceGroupPjKey priceGroupPjKey){
		return priceGroupPjKeyMapper.queryList(priceGroupPjKey);
		
	}
}
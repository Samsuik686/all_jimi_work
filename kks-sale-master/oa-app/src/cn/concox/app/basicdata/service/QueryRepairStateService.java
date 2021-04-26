package cn.concox.app.basicdata.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.CjgzmanageMapper;
import cn.concox.app.basicdata.mapper.QueryRepairStateMapper;
import cn.concox.app.basicdata.mapper.RepairPriceMapper;
import cn.concox.app.basicdata.mapper.SjfjmanageMapper;
import cn.concox.app.basicdata.mapper.ZgzmanageMapper;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Cjgzmanage;
import cn.concox.vo.basicdata.QueryRepairState;
import cn.concox.vo.basicdata.RepairPriceManage;
import cn.concox.vo.basicdata.Sjfjmanage;
import cn.concox.vo.basicdata.Zgzmanage;

@Service("queryRepairStateService")
@Scope("prototype")
public class QueryRepairStateService {
	@Resource(name = "queryRepairStateMapper")
	private QueryRepairStateMapper<QueryRepairState> queryRepairStateMapper;

	@Resource(name = "sjfjmanageMapper")
	private SjfjmanageMapper<Sjfjmanage> sjfjmanageMapper;

	@Resource(name = "cjgzmanageMapper")
	private CjgzmanageMapper<Cjgzmanage> cjgzmanageMapper;

	@Resource(name = "zgzmanageMapper")
	private ZgzmanageMapper<Zgzmanage> zgzmanageMapper;

	@Resource(name = "repairPriceMapper")
	private RepairPriceMapper<RepairPriceManage> repairPriceMapper;

	public List<QueryRepairState> queryList(QueryRepairState queryRepairState) {
		List<QueryRepairState> qsList = queryRepairStateMapper.queryList(queryRepairState);
		setList(qsList);
		return qsList;
	}

	/**
	 * 数据封装
	 * 
	 * @param repairStates
	 * @return
	 */
	public List<QueryRepairState> setList(List<QueryRepairState> repairStates) {
		if (null != repairStates && repairStates.size() > 0) {
			for (QueryRepairState wf : repairStates) {
				if (!StringUtil.isEmpty(wf.getSjfjDesc())) {
					// TODO 随机附件匹配
					String sjfj_Name = sjfjmanageMapper.getGRoupConcat(StringUtil.split(wf.getSjfjDesc()));
					wf.setSjfjDesc(sjfj_Name);
				}
				if (!StringUtil.isEmpty(wf.getZzgzDesc())) {
					// TODO 最终故障
					String zzgz_Name = zgzmanageMapper.getGRoupConcat(StringUtil.split(wf.getZzgzDesc()));
					wf.setZzgzDesc(zzgz_Name);
				}
				if (!StringUtil.isEmpty(wf.getWxbjDesc())) {
					// TODO 维修报价
					String wxbj_Name = repairPriceMapper.getGRoupConcat(StringUtil.split(wf.getWxbjDesc()));
					wf.setWxbjDesc(wxbj_Name);
					;
				}
			}
		}
		return repairStates;
	}
}

package cn.concox.app.aipay.mapper;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.aipay.SaleFeedback;

public interface SaleFeedbackMapper <T> extends BaseSqlMapper<T>{

	public SaleFeedback selectByRepairnNmber(@Param("repairnNmber")String repairnNmber);
	
	public Integer getSendNumByRepairnNum(@Param("repairnNmber")String repairnNmber);
	
}

package cn.concox.app.basicdata.mapper;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.ValidateCode;

public interface ValidateCodeMapper<T> extends BaseSqlMapper<T> {
	/**
	 * @Title: getByPhone 
	 * @Description:获取验证码
	 * @param phone
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午2:32:24
	 */
	public ValidateCode getByPhone(@Param("phone")String phone, @Param("sendType")Integer sendType);
	
	/**
	 * @Title: updateByPhone 
	 * @Description:修改验证码
	 * @param validateCode 
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午2:32:29
	 */
	public void updateByPhone(ValidateCode validateCode);
}

package cn.concox.app.basicdata.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.ValidateCodeMapper;
import cn.concox.vo.basicdata.ValidateCode;

@Service("validateCodeService")
@Scope("prototype")
public class ValidateCodeService {
	@Resource(name = "validateCodeMapper")
	private ValidateCodeMapper<ValidateCode> validateCodeMapper;

	/**
	 * @Title: getValidateCodeByPhone
	 * @Description:查询验证码
	 * @param phone
	 * @return
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午5:21:39
	 */
	public ValidateCode getValidateCodeByPhone(String phone, Integer sendType) {
		return validateCodeMapper.getByPhone(phone, sendType);
	}

	public void updateByPhone(ValidateCode validateCode) {
		validateCodeMapper.updateByPhone(validateCode);
	}

	public void insert(ValidateCode validateCode) {
		validateCodeMapper.insert(validateCode);
	}

}

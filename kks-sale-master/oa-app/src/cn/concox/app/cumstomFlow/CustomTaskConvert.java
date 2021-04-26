package cn.concox.app.cumstomFlow;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cn.concox.vo.customProcess.CustomTask;

@Component
public class CustomTaskConvert implements Converter<String, CustomTask>{

	@Override
	public CustomTask convert(String source) {
		CustomTask ct = new CustomTask();
		ct.setParams(source);
		return ct;
	}

}

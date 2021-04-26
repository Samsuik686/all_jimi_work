package cn.concox.app.cumstomFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.concox.app.cumstomFlow.mapper.CustomFlowMapper;
import cn.concox.vo.customProcess.CustomField;
import cn.concox.vo.customProcess.CustomFlow;
import cn.concox.vo.customProcess.CustomModel;

/**
 * 部分流程信息缓存
 * @author 86183
 *
 */
@Component
public class CustomFlowCache {
	
	@Autowired
	private CustomFlowMapper<CustomFlow> customFlowMapper;

	// modelid -》 modelname缓存
	public static Map<String, String> modelNameMap = new HashMap<>();

	// customflowname -》 customField缓存
	public static Map<String, List<CustomField>> customFieldMap = new HashMap<>();

	public Map<String, String> getModelNameMap() {
		if (null == modelNameMap || modelNameMap.size() == 0) {
			updateModelNameMap();
		}
		return modelNameMap;
	}

	public void updateModelNameMap() {
		List<CustomModel> selectIdNameMap = customFlowMapper.selectIdNameMap();
		modelNameMap = new HashMap<>();
		modelNameMap.put("0", "开始");
		for (CustomModel cm : selectIdNameMap) {
			modelNameMap.put(cm.getId(), cm.getName());
		}
	}

	public Map<String, List<CustomField>> getCustomFieldMap() {
		if (null == customFieldMap || customFieldMap.size() == 0) {
			updateCustomFieldMap();
		}
		return customFieldMap;
	}

	public void updateCustomFieldMap() {
		customFieldMap = new HashMap<>();
		List<String> selectFlowNames = customFlowMapper.selectFlowNames();
		if(selectFlowNames != null && selectFlowNames.size() > 0){
			for(String name : selectFlowNames){
				List<CustomField> customFlowMap = customFlowMapper.selectCustomField(name);
				customFieldMap.put(name, customFlowMap);
			}
		}
	}
}

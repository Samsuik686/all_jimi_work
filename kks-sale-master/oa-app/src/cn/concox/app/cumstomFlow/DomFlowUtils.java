package cn.concox.app.cumstomFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.concox.comm.util.StringUtil;
import cn.concox.vo.customProcess.CustomFlow;
import cn.concox.vo.customProcess.CustomModel;

public class DomFlowUtils {

	private String xml;

	private Document document;

	private Element process;

	private String flowName;

	private Map<String, CustomModel> cmMap = new LinkedHashMap<String, CustomModel>();

	private final static String START_EVENT = "startEvent";

	private final static String END_EVENT = "endEvent";

	private final static String TASK = "task";

	private final static String EXCLUSIVE_GATEWAY = "exclusiveGateway";

	private final static String SEQUENCEFLOW = "sequenceFlow";

	private final static String SOURCE_REF = "sourceRef";

	private final static String TARGET_REF = "targetRef";
	
	private Map<String,String> idMap = new HashMap<String,String>();
	
	private Map<String,String> idMapReverse = new HashMap<String,String>();

	private static Map<String, Integer> typeMap = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4885695954189858732L;

		{
			put(START_EVENT, 0);
			put(END_EVENT, 1);
			put(TASK, 2);
			put(EXCLUSIVE_GATEWAY, 3);
		}
	};

	private DomFlowUtils(String xml) throws Exception {
		this.xml = xml;
		this.document = DocumentHelper.parseText(xml);
	}

	public static DomFlowUtils getDomFlowUtils(String xml) throws Exception {
		DomFlowUtils ds = new DomFlowUtils(xml);
		return ds;
	}

	public static void main(String[] args) throws Exception {
		InputStream is = new FileInputStream(new File(
				"E:\\GitRepositories\\oa-web\\src\\test\\java\\cn\\concox\\test\\activity\\diagrams\\test2.bpmn"));
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		br.close();
		DomFlowUtils domUtils = DomFlowUtils.getDomFlowUtils(sb.toString());

		// System.out.println(domUtils.getFlowName());
		//
		// // task,sequenceFlow,startEvent,endEvent
		// for (Element e : domUtils.getProcessChilds("sequenceFlow")) {
		// System.out.println(e.getName());
		// }

		Collection<CustomModel> trans2Model = domUtils.trans2Model();
		for (CustomModel cm : trans2Model) {
			System.out.println(cm.toString());
		}
		
		// 页面插入bpmn插件，画图
		// 流程图页面，兼容流程图制作，并存储xml
		// 生成流程，模块，生成对应的权限
		// 一个主页面（起始，结束），一个模块页面
		
	}
	
	public void setProcess(){
		this.process = findAppointDocElement("process");
		if(null == process){
			throw new RuntimeException("找不到流程信息");
		}
	}

	// 获取流程名称
	public String getFlowName() {
		if (null == flowName) {
			// 迭代查找process节点
			Element process = findAppointDocElement("participant");
			// 保存process节点
			setProcess();
			// 判断空
			if (process == null || process.attribute("name") == null
					|| StringUtil.isEmpty(process.attribute("name").getValue())
					|| process.attribute("name").getValue().trim().length() < 1) {
				throw new RuntimeException("流程图名称不能为空！");
			}
			// 返回节点名称
			this.flowName = process.attribute("name").getValue();
		}
		return flowName;
	}
	
	// 创建流程图
	public CustomFlow getCustomeFlow(String user){
		CustomFlow cf = new CustomFlow();
		cf.setName(getFlowName());
		cf.setXml(this.xml);
		cf.setCreateDate(new Date());
		cf.setCreateUser(user);
		return cf;
	}

	// 获取流程process下各种元素
	public List<Element> getProcessChilds(String name) {
		if (null == process) {
			getFlowName();
		}
		List<Element> elements = process.elements(name);
		return elements;
	}

	// 转换元素为CustomModel
	public List<CustomModel> trans2Model() {
		// 获取开始页
		if (null == process) {
			getFlowName();
		}
		// 开始、结束节点
		convertStartOrEnd(0);
		convertStartOrEnd(1);
		// 转换任务
		convertTask();
		// 绘制连线
		calConnection();

		return new ArrayList<CustomModel>(cmMap.values());
	}

	// 绘制连线
	public void calConnection() {
		// 绘制连线
		List<Element> flows = getProcessChilds(SEQUENCEFLOW);
		if (flows == null || flows.size() == 0) {
			throw new RuntimeException("找不到连线！");
		}
		for (Element flowEle : flows) {
			Attribute preAttr = flowEle.attribute(SOURCE_REF);
			Attribute backAttr = flowEle.attribute(TARGET_REF);
			// 无前置节点，判断是否为开始节点
			if (null == preAttr || null == backAttr) {
				throw new RuntimeException("连线错误，请保证所有连线都有连接支持的节点类型！");
			}
			String startId = preAttr.getValue();
			String endId = backAttr.getValue();
			CustomModel start = cmMap.get(idMapReverse.get(startId));
			CustomModel end = cmMap.get(idMapReverse.get(endId));
			if (null == start || null == end) {
				throw new RuntimeException("连线错误，现只支持开始、结束、任务三种节点类型！");
			}
			String backNodes = start.getBackNodes();
			if (backNodes == null) {
				backNodes = end.getId();
			} else {
				backNodes = backNodes + "," + end.getId();
			}
			start.setBackNodes(backNodes);
		}
	}

	// 转换任务节点
	public void convertTask() {
		// 任务节点
		List<Element> tasks = getProcessChilds(TASK);
		if (tasks == null || tasks.size() == 0) {
			throw new RuntimeException("找不到任务节点！");
		}
		Set<String> nameSet = new HashSet<String>();
		for (Element taskEle : tasks) {
			CustomModel cm = convertCm(taskEle);
			if(null == cm.getName() || "".equals(cm.getName()) || nameSet.contains(cm.getName())){
				throw new RuntimeException("任务节点名称不能为空或重复");
			}
			nameSet.add(cm.getName());
		}
	}

	// 转换开始、结束节点
	public CustomModel convertStartOrEnd(Integer type) {
		List<Element> events;
		if (0 == type) {
			events = getProcessChilds(START_EVENT);
		} else {
			if (1 == type) {
				events = getProcessChilds(END_EVENT);
			} else {
				throw new RuntimeException("找不到此节点类型：" + type);
			}
		}
		if (events == null || events.size() == 0) {
			throw new RuntimeException("找不到开始、结束节点！");
		}
		if (events.size() > 1) {
			throw new RuntimeException("开始、结束节点只能有一个！");
		}
		Element event = events.get(0);
		return convertCm(event);
	}

	// 转换成model并存入MAP
	public CustomModel convertCm(Element element) {
		CustomModel cm = new CustomModel();
		Attribute idAttr = element.attribute("id");
		Attribute nameAttr = element.attribute("name");

		if(null != idMap.get(idAttr.getValue())){
			throw new RuntimeException("任务名称不能重复！");
		}
		// uuid 和 文件中名称的映射
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		idMap.put(id,idAttr.getValue());
		idMapReverse.put(idAttr.getValue(), id);
		cm.setId(id);
		cm.setName(nameAttr.getValue());
		cm.setType(typeMap.get(element.getName()));
		cm.setBelong(getFlowName());

		cmMap.put(id, cm);
		return cm;
	}

	/**
	 * 通过xml格式，查找指定的节点内容
	 * 
	 * @param xml
	 * @param doc
	 * @return
	 */
	public Element findAppointDocElement(String doc) {
		Element val = null;
		try {
			Element root = this.document.getRootElement();
			val = getElement(root, doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	/**
	 * 递归遍历方法,查找指定节点
	 *
	 * @param element
	 */
	public static Element getElement(Element element, String targetDoc) {
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			String name = element.getName();
			if (name.equals(targetDoc)) {
				return element;
			}
		} else {
			// 有子元素
			for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 当前标签是否为要找的，是的话返回，不是继续遍历子标签
				String name = element.getName();
				if (name.equals(targetDoc)) {
					return element;
				}
				// 递归遍历
				Element result = getElement(elem, targetDoc);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
}

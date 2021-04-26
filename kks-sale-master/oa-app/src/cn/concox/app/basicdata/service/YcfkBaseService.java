package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.BasegroupMapper;
import cn.concox.app.basicdata.mapper.YcfkBaseMapper;
import cn.concox.app.basicdata.mapper.YcfkmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.YcfkBase;
import cn.concox.vo.basicdata.Ycfkmanage;
import freemarker.template.Template;

/**
 * <pre>
 * 异常反馈基础业务类
 * </pre>
 * 
 * @author Liao.bifeng
 * @version v1.0
 */
@Service("ycfkBaseService")
@Scope("prototype")
public class YcfkBaseService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "ycfkBaseMapper")
	private YcfkBaseMapper<YcfkBase> ycfkBaseMapper;
	
	@Resource(name="ycfkmanageMapper")
	private  YcfkmanageMapper<Ycfkmanage> ycfkmanageMapper;

	@Resource(name = "basegroupMapper")
	private BasegroupMapper<Basegroup> basegroupMapper;

	public void insert(YcfkBase ycfkBase) {
		ycfkBaseMapper.insert(ycfkBase);
	}

	public void update(YcfkBase ycfkBase) {
		ycfkBaseMapper.update(ycfkBase);
	}

	public void delete(YcfkBase ycfkBase) {
		ycfkBaseMapper.delete(ycfkBase);
	}

	public YcfkBase select(YcfkBase ycfkBase) {
		return ycfkBaseMapper.select(ycfkBase);
	}

	public Integer isExistsGid(YcfkBase ycfkBase) {
		return ycfkBaseMapper.isExistsGid(ycfkBase);
	}

	public List<YcfkBase> getProDetailsByGid(Integer gId){
		return ycfkBaseMapper.getProDetailsByGid(gId);
	}
	/**
	 * 删除或修改组时查看组下是否有数据
	 * 
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId) {
		return ycfkBaseMapper.getCountByGid(gId);
	}

	/**
	 * 修改分组，改其下数据分类名称
	 * 
	 * @param gId
	 */
	public void updateByGid(Integer gId) {
		ycfkBaseMapper.updateByGid(gId);
	}

	@SuppressWarnings("unchecked")
	public Page<YcfkBase> getYcfkBaseListPage(YcfkBase ycfkBase, int currentPage, int pageSize) {
		Page<YcfkBase> ycfkBasePage = new Page<YcfkBase>();
		ycfkBasePage.setCurrentPage(currentPage);
		ycfkBasePage.setSize(pageSize);
		ycfkBasePage = ycfkBaseMapper.queryYcfkBaseListPage(ycfkBasePage, ycfkBase);
		return ycfkBasePage;
	}

	public int isExists(YcfkBase ycfkBase) {
		return ycfkBaseMapper.isExists(ycfkBase);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void ExportDatas(YcfkBase ycfkBase, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String problems = ycfkBase.getProblems();
		String proDetails = ycfkBase.getProDetails();
		if (null != problems && !"".equals(problems)) {
			ycfkBase.setProblems(new String(problems.getBytes("iso8859-1"), "utf-8"));
		}
		if (null != proDetails && !"".equals(proDetails)) {
			ycfkBase.setProDetails(new String(proDetails.getBytes("iso8859-1"), "utf-8"));
		}

		List<YcfkBase> ycfkBase_list = ycfkBaseMapper.queryList(ycfkBase);

		String[] fieldNames = new String[] { "类别", "问题描述", "处理方法 " };
		Map map = new HashMap();
		map.put("size", ycfkBase_list.size() + 2);
		map.put("peList", ycfkBase_list);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("异常反馈基础列表")
											.append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath("freemarker"))
												.append("/")
												.append(fileName)
												.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA)
												.append("YcfkBase.ftl")
												.toString();

		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	public List<YcfkBase> queryList(YcfkBase ycfkBase) {
		return ycfkBaseMapper.queryList(ycfkBase);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<YcfkBase> successList = new ArrayList<YcfkBase>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file, null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						YcfkBase s = new YcfkBase();
						// YcfkBase对象的属性值{Problems[0]、ProDetails[1]、Methods[2]}
						Object[] obj = new Object[3];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String) obj[0]) || StringUtils.isBlank((String) obj[1]) || StringUtils.isBlank((String) obj[2])) {
									throw new RuntimeException();
								} else {
									s.setProblems((String) obj[0]);
									s.setProDetails((String) obj[1]);
									s.setMethods((String) obj[2]);
									if (isExistsGid(s) == null) {
										Basegroup b = new Basegroup();
										b.setEnumSn("BASE_YCFK");
										b.setGName(s.getProblems());
										basegroupMapper.insert(b);
									}
									if (isExists(s) == 0) {
										try {
											ycfkBaseMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										repeatList.add((repeatList.size() + 1) + ":第" + (j + 1) + "行数据已存在,未导入,请检查......");
									}
								}
							} catch (Exception e) {
								if (errorList.size() < 1000) {// 最多保存1000条错误数据
									errorList.add((errorList.size() + 1) + ":第" + (j + 1) + "行导入错误,有必填数据未填写......");
								}
							}
						}
					}
					if (errorList.size() < 1) {

						if (successList.size() > 0) {
							errorList.add("导入成功！！！");
						} else {
							errorList.add("可导入数据已全部存在！！！");
						}
						if (repeatList.size() > 0) {
							errorList.addAll(repeatList);
						}
					} else {
						errorList.add(0, "导入失败.......");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return errorList;
	}

	public boolean checkSupportDel(YcfkBase ycfk) {
		int count = ycfkBaseMapper.findCountByYcfk(ycfk);
		if(count > 0){
			return false;
		}
		return true;
	}
}

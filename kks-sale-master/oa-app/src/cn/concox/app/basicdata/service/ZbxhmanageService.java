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
import cn.concox.app.basicdata.mapper.ZbxhmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.Basegroup;
import cn.concox.vo.basicdata.Zbxhmanage;
import freemarker.template.Template;

@Service("zbxhmanageService")
@Scope("prototype")
public class ZbxhmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "zbxhmanageMapper")
	private ZbxhmanageMapper<Zbxhmanage> zbxhmanageMapper;
	
	@Resource(name="basegroupMapper")
	private  BasegroupMapper<Basegroup> basegroupMapper;

	@SuppressWarnings("unchecked")
	public Page<Zbxhmanage> getZbxhmanageListPage(Zbxhmanage zbxhmanage, int currentPage, int pageSize) {
		Page<Zbxhmanage> zbxhmanageListPage = new Page<Zbxhmanage>();
		zbxhmanageListPage.setCurrentPage(currentPage);
		zbxhmanageListPage.setSize(pageSize);
		zbxhmanageListPage = zbxhmanageMapper.getZbxhmanageListPage(zbxhmanageListPage, zbxhmanage);
		return zbxhmanageListPage;
	}
	
	
	public Zbxhmanage getZbxhmanage(Zbxhmanage zbxhmanage) {
		return zbxhmanageMapper.getT(zbxhmanage.getMId());
	}

	public void insertZbxhmanage(Zbxhmanage zbxhmanage) {
		if(zbxhmanage != null){
			if(null != zbxhmanage.getMarketModel()){
				zbxhmanage.setMarketModel(zbxhmanage.getMarketModel().trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
			}
			if(null != zbxhmanage.getModel()){
				zbxhmanage.setModel(zbxhmanage.getModel().trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
			}
		}
		zbxhmanageMapper.insert(zbxhmanage);
	}

	public void updateZbxhmanage(Zbxhmanage zbxhmanage) {
		transferData(zbxhmanage);
		zbxhmanageMapper.update(zbxhmanage);
	}

	public void deleteZbxhmanage(Zbxhmanage zbxhmanage) {
		zbxhmanageMapper.delete(zbxhmanage);
	}

	public List<Zbxhmanage> queryList(Zbxhmanage zbxhmanage) {
		return zbxhmanageMapper.queryList(zbxhmanage);
	}
	
	public int isExists(Zbxhmanage zbxhmanage){
		return zbxhmanageMapper.isExists(zbxhmanage);
	}
	
	public Integer isExistsGid(Zbxhmanage zbxhmanage){
		return zbxhmanageMapper.isExistsGid(zbxhmanage);
	}
	
	/**
	 * 删除或修改组时查看组下是否有数据
	 * @param gId
	 * @return
	 */
	public int getCountByGid(Integer gId){
		return zbxhmanageMapper.getCountByGid(gId);
	}
	
	/**
	 * 修改分组，改其下数据分类名称
	 * @param gId
	 */
	public void updateByGid(Integer gId){
		zbxhmanageMapper.updateByGid(gId);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Zbxhmanage zbxhmanage, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Zbxhmanage> zbxhmanageList = zbxhmanageMapper
				.queryList(zbxhmanage);
		String[] fieldNames = new String[] { "市场型号", "主板型号", "型号类别", "创建类型", "禁用", "备注" };
		Map map = new HashMap();
		map.put("size", zbxhmanageList.size() + 2);
		map.put("peList", zbxhmanageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("主板型号管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA).append("Zbxhmanage.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName,template, map);
	}

	@Transactional(readOnly = false)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Zbxhmanage> successList = new ArrayList<Zbxhmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Zbxhmanage s = new Zbxhmanage();
						// zbxhmanage对象的属性值{model[0]、marketModel[1]、modelType[2]、remark[3]}
						Object[] obj = new Object[4];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								//判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2])) {
									throw new RuntimeException();
								}else{
									s.setMarketModel(((String) obj[0]).trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
									s.setModel(((String) obj[1]).trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
									s.setModelType(((String)obj[2]).trim());
									s.setRemark((String)obj[3]);
									s.setCreateType(0);
									s.setEnabledFlag(0);
									if(isExistsGid(s)==null){
										Basegroup b=new Basegroup();
										b.setEnumSn("BASE_ZBXH");
										b.setGName(s.getModelType());
										basegroupMapper.insert(b);
									}
									if(isExists(s)==0){
										try {
											zbxhmanageMapper.insert(s);
											successList.add(s);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}else{
										repeatList.add((repeatList.size() + 1) +":第" + (j + 1) + "行数据已存在,未导入,请检查......");
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
						}else{
							errorList.add("可导入数据已全部存在！！！");
						}
						if(repeatList.size()>0){
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


	public List<Zbxhmanage> getZbTypeBySctype(String marketModel) {
		return zbxhmanageMapper.getZbTypeBySctype(marketModel);
	}


	/**
	 * 检查是否可删除
	 * @param zbxhmanage
	 * @return
	 */
	public boolean checkSupportDel(Zbxhmanage zbxhmanage) {
		int count = zbxhmanageMapper.findCountWorkflowByxhId(zbxhmanage);
		if(count > 0){
			return false;
		}
		return true;
	}

	/**
	 * 中英文括号装换、消除空格、转换为大写
	 * @param zbxhmanage
	 * @return
	 */
	public Zbxhmanage transferData(Zbxhmanage zbxhmanage){
		if(zbxhmanage != null){
			if(null != zbxhmanage.getMarketModel()){
				zbxhmanage.setMarketModel(zbxhmanage.getMarketModel().trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
			}
			if(null != zbxhmanage.getModel()){
				zbxhmanage.setModel(zbxhmanage.getModel().trim().toUpperCase().replaceAll("\\（", "(").replaceAll("\\）", ")").replaceAll(" ", ""));
			}
		}
		return zbxhmanage;
	}
	public int getCountByCreateTypeId(Integer createTypeId){
		return zbxhmanageMapper.selectCountByCreateTypeId(createTypeId);
	}

	@Transactional
	public Integer  batchModifyTypeByMids(List<Zbxhmanage> zbxhmanages,Integer gid,Integer createType){
		List<Integer> mids = new ArrayList<>(zbxhmanages.size()+1);
		//查询主板类型对应的名字。加行锁，防止被更改
		String gname = basegroupMapper.selectByGidInShareMode(gid);
		if(gname == null || "".equals(gname)){
			throw  new  IllegalArgumentException("主板类型不存在");
		}
		//判断修改后的数据是否已经存在。数据库字段没加唯一索引，这里并不能避免更新后变为重复数据。
		for (Zbxhmanage zbxhmanage : zbxhmanages) {
			zbxhmanage.setGId(gid);
			if (zbxhmanageMapper.isExistsByGid(zbxhmanage) >= 1) {
				throw new IllegalArgumentException("主板数据已存在");
			}else{
				mids.add(zbxhmanage.getMId());
			}
		}
		return  zbxhmanageMapper.updateTypeAndCreateTypeByMids(mids,gid,gname,createType);
	}

	public List<Zbxhmanage> getZbxhmanagesByMids(List<Integer> mids){
		return  zbxhmanageMapper.selectZbxhmanagesByMids(mids);
	}
}

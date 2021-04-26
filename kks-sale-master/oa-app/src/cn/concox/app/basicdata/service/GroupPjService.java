package cn.concox.app.basicdata.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.basicdata.mapper.GroupPjDetailsMapper;
import cn.concox.app.basicdata.mapper.GroupPjMapper;
import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.MaterialMapper;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.vo.basicdata.GroupPj;
import cn.concox.vo.basicdata.GroupPjDetails;
import cn.concox.vo.material.Material;
/**
 * 配件料 业务层
 */
@Service("groupPjService")
@Scope("prototype")
public class GroupPjService {
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource(name="groupPjMapper")
	private GroupPjMapper<GroupPj> groupPjMapper;

	@Resource(name = "materialMapper")
	private MaterialMapper<Material> materialMapper;
	
	@Resource(name="groupPjDetailsMapper")
	private GroupPjDetailsMapper<GroupPjDetails> groupPjDetailsMapper;
	
	@SuppressWarnings("unchecked")
	public Page<GroupPj> getGroupPjList(GroupPj groupPj, int currentPage, int pageSize){
		Page<GroupPj> groupPjsPage = new Page<GroupPj>();
		groupPjsPage.setCurrentPage(currentPage);
		groupPjsPage.setSize(pageSize);
		groupPjsPage = groupPjMapper.getGroupPjList(groupPjsPage, groupPj);
		return groupPjsPage;
	} 
	
	public void deleteGroupPjInfo(GroupPj groupPj){
		groupPjMapper.delete(groupPj);
	} 
	
	public void insertGroupPjInfo(GroupPj groupPj){
		BigDecimal pjlPrice = groupPjDetailsMapper.getPriceByGroupId(groupPj.getGroupPjId());
		groupPj.setGroupPrice(pjlPrice);
		groupPjMapper.insert(groupPj);
	}
	
	public void updateGroupPjInfo(GroupPj groupPj){
		BigDecimal pjlPrice = groupPjDetailsMapper.getPriceByGroupId(groupPj.getGroupPjId());
		groupPj.setGroupPrice(pjlPrice);
		groupPjMapper.update(groupPj);
	}
	
	public GroupPj getGroupPjInfo(GroupPj groupPj){ 
		return groupPjMapper.getT(groupPj.getGroupPjId());
	}

	public int isExists(GroupPj groupPj){
		return groupPjMapper.isExists(groupPj);
	}

	public int checkSupportDel(GroupPj groupPj) {
		return groupPjMapper.findCountByGroupPjId(groupPj);
	}
	
	public Integer addOrUpdateGroupPjInfo(GroupPj groupPj) {		
		if (null != groupPj.getGroupPjId() && groupPj.getGroupPjId() >0 ) {
			updateGroupPjInfo(groupPj);
		} else {
			insertGroupPjInfo(groupPj);
		}
		return groupPj.getGroupPjId();
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<GroupPj> successList = new ArrayList<GroupPj>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						GroupPj s = new GroupPj();
						// PriceGroupPj对象的属性值{groupName[0]、marketModel[1]、remark[2]}
						Object[] obj = new Object[3];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1])) {
									throw new RuntimeException();
								} else {
									s.setGroupName((String) obj[0]);
									s.setMarketModel((String) obj[1]);
									s.setRemark((String) obj[2]);
									s.setImportFalg(0);
									if(isExists(s)==0){
										try {
											groupPjMapper.insert(s);
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
}
package cn.concox.app.basicdata.service;

import java.io.IOException;
import java.sql.Timestamp;
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

import sso.comm.model.UserInfo;
import cn.concox.app.basicdata.mapper.SxdwmanageMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.Globals;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.comm.poi.ReadExcel;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.Sxdwmanage;
import freemarker.template.Template;

@Service("sxdwmanageService")
@Scope("prototype")
public class SxdwmanageService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "sxdwmanageMapper")
	private SxdwmanageMapper<Sxdwmanage> sxdwmanageMapper;

	@SuppressWarnings("unchecked")
	public Page<Sxdwmanage> getSxdwmanageListPage(Sxdwmanage sxdwmanage, int currentPage, int pageSize) {
		Page<Sxdwmanage> sxdwmanageListPage = new Page<Sxdwmanage>();
		sxdwmanageListPage.setCurrentPage(currentPage);
		sxdwmanageListPage.setSize(pageSize);
		sxdwmanageListPage = sxdwmanageMapper.getSxdwmanageListPage(sxdwmanageListPage, sxdwmanage);
		return sxdwmanageListPage;
	}

	public Sxdwmanage getSxdwmanage(Sxdwmanage sxdwmanage) {
		return sxdwmanageMapper.getT(sxdwmanage.getCId());
	}

	public void insertSxdwmanage(Sxdwmanage sxdwmanage) {
		if(!StringUtil.isRealEmpty(sxdwmanage.getPhone())){
			Sxdwmanage sx = sxdwmanageMapper.getHasLoginPwdOneByPhone(sxdwmanage.getPhone());
			if(sx != null){
				sxdwmanage.setLoginPwd(sx.getLoginPwd());
				sxdwmanage.setRegState(sx.getRegState());
			}
		}
		sxdwmanageMapper.insert(sxdwmanage);
	}

	public void updateSxdwmanage(Sxdwmanage sxdwmanage) {
		sxdwmanageMapper.update(sxdwmanage);
	}

	public void deleteSxdwmanage(Sxdwmanage sxdwmanage) {
		sxdwmanageMapper.delete(sxdwmanage);
	}

	public List<Sxdwmanage> queryList(Sxdwmanage sxdwmanage) {
		return sxdwmanageMapper.queryList(sxdwmanage);
	}

	public int isExists(Sxdwmanage sxdwmanage){
		return sxdwmanageMapper.isExists(sxdwmanage);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Sxdwmanage sxdwmanage, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cusName=sxdwmanage.getCusName();
		if(null!=cusName&&!"".equals(cusName)){
			sxdwmanage.setCusName(new String(cusName.getBytes("iso8859-1"),"utf-8"));
		}
		List<Sxdwmanage> sxdwmanageList = sxdwmanageMapper.queryList(sxdwmanage);
		String[] fieldNames = new String[] { "客户名称", "服务周期", "联系人", "手机号", "座机号","邮箱", "传真", "通信地址", "禁用", "备注" };
		Map map = new HashMap();
		map.put("size", sxdwmanageList.size() + 2);
		map.put("peList", sxdwmanageList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);
		String fileName = new StringBuilder("送修单位管理列表").append(GlobalCons.FILE_ENDTYPE_XLS).toString();
		String exportFile = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)).append("/").append(fileName).toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_BASEDATA).append("Sxdwmanage.ftl").toString();
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public List<String> ImportDatas(MultipartFile file, HttpServletRequest request) {
		List<String> errorList = new ArrayList<String>();// 记录插入错误的列
		List<Sxdwmanage> successList = new ArrayList<Sxdwmanage>();// 保存可以插入成功的表的数据
		List<String> repeatList = new ArrayList<String>();// 重复数据
		Object[] result;
		try {
			result = ReadExcel.readXls(file,null);
			for (int i = 0; i < result.length; i++) {
				Object[] m = (Object[]) result[i];
				if (m != null && m.length > 0) {
					for (int j = 2; j < m.length; j++) { // TODO 行
						Sxdwmanage s = new Sxdwmanage();

						// Sxdwmanage对象的属性值{cusName[0]、serviceTime[1]、linkman[2]、phone[3]、telephone[4]、email[5]、fax[6]、address[7]、remark[8]}
						Object[] obj = new Object[9];

						Object[] n = (Object[]) m[j];
						if (n != null && n.length > 0) {
							for (int k = 0; k < n.length; k++) { // TODO 列
								obj[k] = n[k];
							}
							try {
								// 判断非空字段，如有为空，直接抛出异常
								if (StringUtils.isBlank((String)obj[0]) || StringUtils.isBlank((String)obj[1]) || StringUtils.isBlank((String)obj[2]) || StringUtils.isBlank((String)obj[3]) || StringUtils.isBlank((String)obj[7])) {
									throw new RuntimeException();
								} else {
									s.setCusName((String) obj[0]);
									s.setServiceTime((String) obj[1]);
									s.setLinkman((String) obj[2]);
									s.setPhone((String) obj[3]);
									s.setTelephone((String) obj[4]);
									s.setEmail((String) obj[5]);
									s.setFax((String) obj[6]);
									s.setAddress((String) obj[7]);
									s.setRemark((String) obj[8]);
									s.setCreateBy(this.getSessionUserName(request));
									s.setCreateTime(new Timestamp(System.currentTimeMillis()));
									if(isExists(s)==0){
										try {
											s.setEnabledFlag(0);//导入数据默认不禁用
											this.insertSxdwmanage(s);
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

	/**
	 * 检查是否可删除
	 * @param sxdwmanage
	 * @return
	 */
	public boolean checkSupportDel(Sxdwmanage sxdwmanage) {
		int count = sxdwmanageMapper.findCountWorkflowBysxdw(sxdwmanage);
		if(count > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当前登录用户 ID
	 * @param req
	 * @return
	 */
	protected String getSessionUserName(HttpServletRequest req) {
		UserInfo user =  (UserInfo) req.getSession().getAttribute(Globals.USER_KEY);
		if(null !=user) return user.getuName(); else return null;
	}
	
	/**
	 * @Title: checkRegister 
	 * @Description:客户注册验证
	 * @param phone
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年8月23日 下午2:52:55
	 */
	public List<Sxdwmanage> checkRegister(String phone){
		return sxdwmanageMapper.checkRegister(phone);
	}

	public void updateByRegister(Sxdwmanage sxdwmanage) {
		sxdwmanageMapper.updateByRegister(sxdwmanage);
	}
	
	public void updateByCustomer(Sxdwmanage sxdwmanage) {
		sxdwmanageMapper.updateByCustomer(sxdwmanage);
	}
	
	public Sxdwmanage getSxdwInfo(Integer cId){
		return sxdwmanageMapper.getSxdwInfo(cId);
	}
	
	public int addressCheck(Sxdwmanage sxdwmanage) {
		return sxdwmanageMapper.addressCheck(sxdwmanage);
	}
	
	public int phoneCheck(Sxdwmanage sxdwmanage) {
		return sxdwmanageMapper.phoneCheck(sxdwmanage);
	}
}

package cn.concox.app.material.service;

import java.io.IOException;
import java.sql.Timestamp;
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
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.concox.app.common.page.Page;
import cn.concox.app.material.mapper.ChangeboardMapper;
import cn.concox.comm.GlobalCons;
import cn.concox.comm.freemarker.FreemarkerManager;
import cn.concox.vo.material.Changeboard;
import freemarker.template.Template;

@Service("changeboardService")
@Scope("prototype")
public class ChangeboardService {
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Resource(name = "changeboardMapper")
	private ChangeboardMapper<Changeboard> changeboardMapper;

	@SuppressWarnings("unchecked")
	public Page<Changeboard> getChangeboardListPage(Changeboard changeboard, int currentPage,int pageSize) {
		Page<Changeboard> changeboardListPage = new Page<Changeboard>();
		changeboardListPage.setCurrentPage(currentPage);
		changeboardListPage.setSize(pageSize);
		changeboardListPage = changeboardMapper.getListPage(changeboardListPage, changeboard);
		return changeboardListPage;
	}

	public Changeboard getChangeboard(Changeboard changeboard) {
		return changeboardMapper.getT(changeboard.getId());
	}

	public void insertChangeboard(Changeboard changeboard) {
		changeboardMapper.insert(changeboard);
	}
	
   public void updateChangeboard(Changeboard changeboard) {
        changeboardMapper.update(changeboard);
    }
	
	@Transactional
	public void updateChangeboard(List<Changeboard> changeboardList, HttpServletRequest request) {
	    
        for (Changeboard changeboard : changeboardList) {
            String chargerUpdateTime = request.getParameter("chargerUpdateDate");
            if (!StringUtils.isBlank(chargerUpdateTime)) {
                changeboard.setChargerUpdateTime(Timestamp.valueOf(chargerUpdateTime));
            }
            String managerUpdateTime = request.getParameter("managerUpdateDate");
            if (!StringUtils.isBlank(managerUpdateTime)) {
                changeboard.setManagerUpdateTime(Timestamp.valueOf(managerUpdateTime));
            }
            String serviceUpdateTime = request.getParameter("serviceUpdateDate");
            if (!StringUtils.isBlank(serviceUpdateTime)) {
                if ("kong".equals(serviceUpdateTime)) {
                    changeboard.setServiceUpdateTime(null);
                } else {
                    changeboard.setServiceUpdateTime(Timestamp.valueOf(serviceUpdateTime));
                }
            }
            String testBackTime = request.getParameter("testBackDate");
            if (!StringUtils.isBlank(testBackTime)) {
                if ("kong".equals(testBackTime)) {
                    changeboard.setTestBackTime(null);
                } else {
                    changeboard.setTestBackTime(Timestamp.valueOf(testBackTime));
                }
            }
            changeboardMapper.update(changeboard);
        }
	}

	public void deleteChangeboard(Changeboard changeboard) {
		changeboardMapper.delete(changeboard);
	}

	public List<Changeboard> queryList(Changeboard changeboard) {
		return changeboardMapper.queryList(changeboard);
	}
	public int isExists(Changeboard changeboard){
		return changeboardMapper.isExists(changeboard);
	}
	
	public int getCountByWfId(Integer wfId){
		return changeboardMapper.getCountByWfId(wfId);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked", "deprecation" })
	public void ExportDatas(Changeboard changeboard, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Changeboard> changeboardList = changeboardMapper.queryList(changeboard);
		String[] fieldNames = new String[] { "客户名称", "IMEI", "状态", "处理岗位", "主板型号", "保内|保外", "申请数量", "维修|测试", "申请人", "申请日期", "换板原因", "主管", "主管批复日期", "经理", "经理批复日期", "客服文员", "换板日期", "备注" };
		Map map = new HashMap();
		map.put("size", changeboardList.size()+2);
		map.put("peList",changeboardList);
		map.put("fieldNames", fieldNames);
		map.put("cosLenth", fieldNames.length);  
		String fileName     = new StringBuilder("主板免费更换管理")
									.append(GlobalCons.FILE_ENDTYPE_XLS)
									.toString();
		String exportFile   = new StringBuilder(request.getRealPath(GlobalCons.FREEMARKER)) 
									.append("/")
									.append(fileName)
									.toString();
		String templatePath = new StringBuilder(GlobalCons.FREEMARKER_MATERIAL)
				
									.append("Changeboard.ftl")
									.toString(); 
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
		FreemarkerManager.down(request, response, exportFile, fileName, template, map);
	}
}

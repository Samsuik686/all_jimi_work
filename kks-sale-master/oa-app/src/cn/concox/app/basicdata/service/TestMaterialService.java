package cn.concox.app.basicdata.service;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.TestMaterialMapper;
import cn.concox.app.common.page.Page;
import cn.concox.vo.basicdata.TestMaterial;
/**
 * 试流料 业务层
 */
@Service("testMaterialService")
@Scope("prototype")
public class TestMaterialService {
	@Resource(name="testMaterialMapper")
	private TestMaterialMapper<TestMaterial> testMaterialMapper;
	
	@SuppressWarnings("unchecked")
	public Page<TestMaterial> getTestMaterialList(TestMaterial testMaterial, int currentPage, int pageSize){
		Page<TestMaterial> testMaterialsPage = new Page<TestMaterial>();
		testMaterialsPage.setCurrentPage(currentPage);
		testMaterialsPage.setSize(pageSize);
		testMaterialsPage = testMaterialMapper.getTestMaterialList(testMaterialsPage, testMaterial);
		return testMaterialsPage;
	} 
	
	public TestMaterial getTestMaterialInfo(TestMaterial testMaterial){ 
		return testMaterialMapper.getT(testMaterial.getTid());
	}
}
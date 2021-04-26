package cn.concox.app.basicdata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Sxdwmanage;

public interface SxdwmanageMapper<T> extends BaseSqlMapper<T> {
	@SuppressWarnings("rawtypes")
	public Page getSxdwmanageListPage(@Param(PageInterceptor.PAGE_KEY) Page page, @Param("po") T po) throws DataAccessException;

	public Sxdwmanage getByRepairNumber(@Param("repairnNmber") String repairnNmber);

	public int findCountWorkflowBysxdw(Sxdwmanage sxdwmanage); 
	
	public Sxdwmanage findCustomerByPhone(String phone);
	
	/**
	 * @Title: checkRegister 
	 * @Description:客户注册验证
	 * @param phone
	 * @return 
	 * @author HuangGangQiang
	 * @date 2017年8月23日 下午2:52:25
	 */
	public List<Sxdwmanage> checkRegister(String phone);
	
	public Sxdwmanage getSxdwInfo(Integer cId);
	
	/**
	 * @Title: updateByRegister 
	 * @Description:客户注册增加密码和注册状态
	 * @param sxdwmanage 
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午2:00:26
	 */
	public void updateByRegister(Sxdwmanage sxdwmanage);
	
	/**
	 * @Title: updateByCustomer 
	 * @Description:客户修改信息
	 * @param sxdwmanage 
	 * @author HuangGangQiang
	 * @date 2017年8月11日 下午2:00:26
	 */
	public void updateByCustomer(Sxdwmanage sxdwmanage);
	
	/**
	 * @Title: getHasLoginPwdOneByPhone 
	 * @Description:新增时根据手机号查询已注册的单位密码等信息
	 * @param phone
	 * @return 
	 * @author 20160308
	 * @date 2017年10月31日 下午6:19:15
	 */
	public Sxdwmanage getHasLoginPwdOneByPhone(String phone);
	
	/**
	 * 查询该地址是否存在数据
	 * @param phone
	 * @return
	 */
	public int addressCheck(Sxdwmanage sxdwmanage);
	
	/**
	 * 查询该手机号是否存在数据
	 * @param phone
	 * @return
	 */
	public int phoneCheck(Sxdwmanage sxdwmanage);
	
	
}

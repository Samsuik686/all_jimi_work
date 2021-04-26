package cn.concox.app.basicdata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import cn.concox.app.common.page.Page;
import cn.concox.app.common.page.PageInterceptor;
import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.YcfkManageFile;
import cn.concox.vo.basicdata.YcfkTwomanage;

public interface YcfkTwomanageMapper<T> extends BaseSqlMapper<T>{
	public Page<YcfkTwomanage>  queryYcfkTwomanageListPage(@Param(PageInterceptor.PAGE_KEY) Page<YcfkTwomanage> page, @Param("po") T po) throws  DataAccessException; 

	public List<YcfkTwomanage> queryYcfkTwomanageListPage( @Param("po") YcfkTwomanage po);
	
	public List<YcfkTwomanage> ycfkCountList(YcfkTwomanage ycfk);
	
	public void sendDataToNextSite(@Param("po") YcfkTwomanage ycfk, @Param("ids")String... ids);
	
	public void deleteByIds(@Param("ids")String... ids);
	
	public void insertYcfkFile(YcfkManageFile file);
	
	public List<YcfkManageFile> getYcfkManageFile(@Param("yid")Integer yid);
	
	public void deleteFileByFid(@Param("fid") Integer fid);
	
	/**
	 * 删除异常反馈时连带删除附件信息
	 * @author TangYuping
	 * @version 2017年4月14日 下午2:33:31
	 * @param yids
	 */
	public void deleteFileByYid(@Param("yids")String[] yids);

	public List<YcfkTwomanage> selectNotCompleteByUname(@Param("po")YcfkTwomanage ycfkTwomanage);

	public List<YcfkTwomanage> selectNotCompleteByUnameCopy(@Param("po")YcfkTwomanage ycfkTwomanage);
}

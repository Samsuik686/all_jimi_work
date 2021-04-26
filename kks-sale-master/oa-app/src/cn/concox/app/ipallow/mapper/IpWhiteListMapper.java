package cn.concox.app.ipallow.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import cn.concox.comm.base.rom.BaseSqlMapper;
public interface IpWhiteListMapper extends BaseSqlMapper<T>{
	public Integer getIpWhiteListByIp(@Param("ip") String ip);
}

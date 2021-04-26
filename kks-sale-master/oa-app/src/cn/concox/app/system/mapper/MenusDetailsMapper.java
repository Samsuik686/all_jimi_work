package cn.concox.app.system.mapper;

import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;

public interface MenusDetailsMapper<T> extends BaseSqlMapper<T> {

	public void deleteByMenuId(T entity) throws DataAccessException;
}

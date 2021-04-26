package cn.concox.app.system.mapper;


import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.system.Dictionary;

public interface DictionaryMapper<T>  extends BaseSqlMapper<T> {
  
	public Dictionary queryDictionaryById(Integer entity) throws DataAccessException;
	public List<T> queryDictionaryBySN(T entity) throws DataAccessException;
	public List<T> queryListByParent(T entity) throws DataAccessException;
	public void deletechildren(T entity) throws DataAccessException;
	public Dictionary queryByItemSN(T entity) throws DataAccessException;
	
}

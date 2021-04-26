package cn.concox.app.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.system.mapper.DictionaryMapper;
import cn.concox.vo.system.Dictionary;


@Service("dictionaryService")
@Scope("prototype")
public class DictionaryService {
	
	@Resource(name="dictionaryMapper")
	private DictionaryMapper<Dictionary> dictionaryMapper;
	
	
	public List<Dictionary> queryDictionary(){
		Dictionary dic=new Dictionary();
		dic.setProTypeId(0);
		List<Dictionary> list=dictionaryMapper.queryList(dic);
		return list;
	}
	
	public List<Dictionary> queryDictionarySNID(Dictionary dic){
		List<Dictionary> list=dictionaryMapper.queryDictionaryBySN(dic);
		return list;
	}
	
	public List<Dictionary> selectTreeByProTypeId(Integer dicId){
		Dictionary dic=new Dictionary();
		dic.setDicId(dicId);
		List<Dictionary> list=dictionaryMapper.queryListByParent(dic);
		return list;
	}
	
	public Dictionary queryDictionaryById(Integer dicId)
	{
		Dictionary dic=dictionaryMapper.queryDictionaryById(dicId);
		return dic;
	}
	
	
	public void treeupdate(Dictionary dic)
	{
		dictionaryMapper.update(dic);
	}
	
	
	public void insert(Dictionary dic)
	{
		dictionaryMapper.insert(dic);
	}
	
	public void delete(Integer dicId)
	{
		Dictionary dic=new Dictionary();
		dic.setDicId(dicId);
		dictionaryMapper.delete(dic);
	}
	
	public void treeDeleteChildren(Integer dicId)
	{
		Dictionary dic=new Dictionary();
		dic.setDicId(dicId);
		dictionaryMapper.deletechildren(dic);
	}
	
	
	
	
	// ****************************************

	public List<Dictionary> queryQJLX() { // 请假类型数据
		Dictionary dic = new Dictionary();
		dic.setProTypeId(0);
		List<Dictionary> list = dictionaryMapper.queryList(dic);
		return list;
	}

	public String queryByItemSN(String sn) {
		Dictionary dic = new Dictionary();
		dic.setSn(sn);  
		//dic.setItemValue(itemVal);
		Dictionary dicVo = dictionaryMapper.queryByItemSN(dic);
		if (dicVo != null)
  
			return dicVo.getItemName();
		else
			return "";
	}

	// ****************************************
	
	
	
	
	
	public static void main(String[] args) {
		try {
			System.out.println();
			System.exit(0);
		} catch (Exception e) {
			 System.out.println("error");
		}
		
	}
	
}

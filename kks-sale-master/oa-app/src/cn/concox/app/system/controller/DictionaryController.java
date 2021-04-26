package cn.concox.app.system.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.concox.app.system.service.DictionaryService;
import cn.concox.comm.base.controller.BaseController;
import cn.concox.comm.vo.APIContent;
import cn.concox.vo.system.Dictionary;



@Controller
@Scope("prototype")
public class DictionaryController extends BaseController {
	
	
	Logger log=Logger.getLogger(DictionaryController.class);
	
	@Resource(name="dictionaryService")
	private DictionaryService service;
	
	@RequestMapping("/dictionary/selectdic")
	@ResponseBody
	public APIContent selectdic(){
		log.info("数据字典树查询开始");
		try {
		return super.putAPIData(service.queryDictionary());
		} catch (Exception e) {
			log.error("数据字典查询失败！");
			log.error(e);
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/dictionary/selectTreeById")
	@ResponseBody
	public APIContent selectTreeById(@RequestParam("dicId") int dicId){
		log.info("数据字典树指定Id查询开始");
		try {
		return super.putAPIData(service.queryDictionaryById(dicId));
		} catch (Exception e) {
			log.error("数据字典指定Id查询失败！");
			log.error(e);
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/dictionary/selectTreeByProTypeId")
	@ResponseBody
	public APIContent selectTreeByProTypeId(@RequestParam("dicId") int dicId){
		log.info("数据字典树指定Id查询开始");
		try {
		return super.putAPIData(service.selectTreeByProTypeId(dicId));
		} catch (Exception e) {
			log.error("数据字典指定Id查询失败！");
			log.error(e);
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/dictionary/selectdicIdOrSn")
	@ResponseBody
	public APIContent selectdic(@ModelAttribute Dictionary dic){
		log.info("数据字典树指定dicIdorSN查询开始");
		try {
		return super.putAPIData(service.queryDictionarySNID(dic));
		} catch (Exception e) {
			log.error("数据字典树指定dicIdorSN查询失败！");
			log.error(e.getMessage());
			return super.putAPIData("数据字典树指定dicIdorSN查询失败");
			
		}
	}
	
	@RequestMapping("/dictionary/treeAdd")
	@ResponseBody
	public APIContent insert(@ModelAttribute Dictionary dic){
		//service.addMenus();
		log.info("数据字典树增加开始");
		try {
			service.insert(dic);	
			return super.putAPIData(service.queryDictionary());
			
		} catch (Exception e) {
			log.error("数据字典树增加失败！");
			log.error(e);
			return super.putAPIData("false");
			
		}
		
		
	}
	
	@RequestMapping("/dictionary/treedelete")
	@ResponseBody
	public APIContent delete(@RequestParam("dicId") int dicId)
	{
		log.info("数据字典树删除开始");
		try{
			service.delete(dicId);
			return super.putAPIData(service.queryDictionary());
		}catch(Exception e)
		{
			log.error("数据字典树删除失败！");
			log.error(e);
			return super.putAPIData("false");
		}
	}
	          
  	@RequestMapping("/dictionary/treeDeleteChildren")
  	@ResponseBody
  	public APIContent treeDeleteChildren(@RequestParam("dicId") int dicId)
  	{
  		log.info("数据字典树删除子节点开始");
  		try{
  			service.treeDeleteChildren(dicId);
  			return super.putAPIData("true");
  		}catch(Exception e)
  		{
  			log.error("数据字典树删除子节点失败！");
  			log.error(e);
  			return super.putAPIData("false");
  		}
  	}          
	          
	          
	@RequestMapping("/dictionary/UpdateTreeById")
	@ResponseBody
	public APIContent UpdateTreeById(@ModelAttribute Dictionary dic){
		log.info("数据字典树修改开始");
		try {
			service.treeupdate(dic);
			return super.putAPIData(service.queryDictionary());
		} catch (Exception e) {
			log.error("数据字典修改失败！");
			log.error(e);
			return super.putAPIData("false");
			
		}
	}
	
	@RequestMapping("/dictionary/queryQJLX")
	@ResponseBody     
	public APIContent queryQJLX(){
		log.info("请假类型数据字典树查询开始"); 
		try { 
		return super.putAPIData(service.queryQJLX());
		} catch (Exception e) {  
			log.error("请假类型数据字典查询失败！");
			log.error(e.toString());
			return super.putAPIData("false");
			
		}
	}
	
	
	
}

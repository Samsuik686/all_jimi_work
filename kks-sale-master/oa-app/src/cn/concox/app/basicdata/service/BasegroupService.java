package cn.concox.app.basicdata.service;

import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.concox.app.basicdata.mapper.BasegroupMapper;
import cn.concox.vo.basicdata.Basegroup;

@Service("basegroupService")
@Scope("prototype")
public class BasegroupService {
	
	Logger logger=Logger.getLogger("privilege");
	
	@Resource(name="basegroupMapper")
	private BasegroupMapper<Basegroup> basegroupMapper;

	
	public Basegroup getInfo(Basegroup basegroup) {  
		return basegroupMapper.getT(basegroup.getGId()); 
	} 
	
	public void deleteInfo(Basegroup basegroup){
		basegroupMapper.delete(basegroup);
	} 
	
	public void insertInfo(Basegroup basegroup){
		basegroupMapper.insert(basegroup);
	}
	
	public void updateInfo(Basegroup basegroup){
		basegroupMapper.update(basegroup);
	}
	
	public List<Basegroup> queryList(Basegroup basegroup) { 
		return basegroupMapper.queryList(basegroup);
	}
	
	public int isExists(Basegroup basegroup){
		return basegroupMapper.isExists(basegroup);
	}
}

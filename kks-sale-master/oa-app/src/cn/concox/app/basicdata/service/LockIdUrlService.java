package cn.concox.app.basicdata.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.concox.app.basicdata.mapper.LockIdUrlMapper;
import cn.concox.app.common.page.Page;
import cn.concox.comm.util.StringUtil;
import cn.concox.vo.basicdata.LockIdUrl;

@Service("lockIdUrlService")
@Scope("prototype")
public class LockIdUrlService {
	
	@Resource(name="lockIdUrlMapper")
	private LockIdUrlMapper<LockIdUrl> lockIdUrlMapper;
	
	@SuppressWarnings("unchecked")
	public Page<LockIdUrl> getLockIdUrlListPage(LockIdUrl lockIdUrl, int currentPage, int pageSize) {
		Page<LockIdUrl> lockIdUrls = new Page<LockIdUrl>();
		lockIdUrls.setCurrentPage(currentPage);
		lockIdUrls.setSize(pageSize);
		return lockIdUrlMapper.getLockIdUrlList(lockIdUrls, lockIdUrl);
	}

	public LockIdUrl getLockIdUrl(LockIdUrl lockIdUrl) {
		return lockIdUrlMapper.getT(lockIdUrl.getId());
	}

	public void deleteLockIdUrl(LockIdUrl lockIdUrl) {
		lockIdUrlMapper.delete(lockIdUrl);
	}

	public void insertLockIdUrl(LockIdUrl lockIdUrl) {
		lockIdUrlMapper.insert(lockIdUrl);
	}
	
	public void updateLockIdUrl(LockIdUrl lockIdUrl) {
		lockIdUrlMapper.update(lockIdUrl);
	}

	public List<LockIdUrl> getLockIdUrlList(LockIdUrl lockIdUrl) {
		return lockIdUrlMapper.getLockIdUrlList(lockIdUrl);
	}
	
	public int isExists(LockIdUrl lockIdUrl){
		return lockIdUrlMapper.isExists(lockIdUrl);
	}
	
	public LockIdUrl matchLongStr(String urlPrefix){
		if(null != urlPrefix && !"".equals(urlPrefix)){
			LockIdUrl c = lockIdUrlMapper.matchLongStr(urlPrefix);
			if(null != c && null != c.getUrlPrefix()){
				String lockId = urlPrefix.substring(c.getUrlPrefix().length());
				c.setLockId(lockId);
//			}else if(StringUtil.isDigit(urlPrefix)){
//				c = new LockIdUrl();
//				c.setLockId(urlPrefix);
			}else{
				c = new LockIdUrl();
			}
			c.setLockInfo(urlPrefix);
			return c;
		}else{
			return null;
		}
	}
}

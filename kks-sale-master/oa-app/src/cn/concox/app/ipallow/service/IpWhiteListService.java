package cn.concox.app.ipallow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.concox.app.ipallow.mapper.IpWhiteListMapper;

/**
 * 查询ip白名单
 * @author ZhaYuanKai
 *
 */
@Service("ipWhiteListService")
public class IpWhiteListService {
	@Autowired
	private IpWhiteListMapper ipAllowMapper;
	public boolean getAccessIp(String ip) {
		if(ipAllowMapper.getIpWhiteListByIp(ip)>0) {
			return true;
		}else {
			return false;
		}
	};
}

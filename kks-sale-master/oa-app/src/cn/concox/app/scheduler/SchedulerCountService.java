package cn.concox.app.scheduler;

import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.concox.app.aipay.service.CustomerClientService;

@Component
@Service("schedulerCountService")
public class SchedulerCountService {
	
	@Autowired
	private CustomerClientService customerClientService;
	
	/**
	 * 发货15天后系统自动设置好评
	 * @author TangYuping
	 * @version 2017年2月17日 下午1:48:51
	 * @throws SQLException
	 */
	@Scheduled(cron="0 0 5 * * ?")
	public void updateFeedback() throws SQLException{
		System.out.println("开始>>>>>>>>>>>>>>>>>>>>>>>>>>>"+new Timestamp(System.currentTimeMillis()));
		customerClientService.updateFeedback();
		System.out.println("结束>>>>>>>>>>>>>>>>>>>>>>>>>>>"+new Timestamp(System.currentTimeMillis()));
		
	}

}

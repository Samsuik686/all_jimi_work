package cn.concox.vo.aipay;

import java.util.ArrayList;
import java.util.List;

public class ReturnJson {
	public List<CustomerClient> clients = new  ArrayList<CustomerClient>();
	
	public CustomerClient client;
	
	public Integer repairCount;

	public List<CustomerClient> getClients() {
		return clients;
	}

	public void setClients(List<CustomerClient> clients) {
		this.clients = clients;
	}

	public CustomerClient getClient() {
		return client;
	}

	public void setClient(CustomerClient client) {
		this.client = client;
	}

	public Integer getRepairCount() {
		return repairCount;
	}

	public void setRepairCount(Integer repairCount) {
		this.repairCount = repairCount;
	}
	
	
	

}

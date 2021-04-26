package cn.concox.vo.report;
import java.sql.Timestamp;
/**
 * 内部二次返修
 * @author 20160308
 *
 */
public class InnerRepairAgainReport{

	/** ----------------- 业务字段 ---------------------------- Start **/
    private String engineer;	      //维修员
    
    private String returnTimes;	      //返修次数
    
    private String returnTimesA;	  //二次返修次数
    
    private String returnTimesP;	  //返修次数概率
    
    private Timestamp StratTime;      //开始时间
    
    private Timestamp EndTime;        //结束时间

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getReturnTimes() {
		return returnTimes;
	}

	public void setReturnTimes(String returnTimes) {
		this.returnTimes = returnTimes;
	}

	public String getReturnTimesA() {
		return returnTimesA;
	}

	public void setReturnTimesA(String returnTimesA) {
		this.returnTimesA = returnTimesA;
	}

	public String getReturnTimesP() {
		return returnTimesP;
	}

	public void setReturnTimesP(String returnTimesP) {
		this.returnTimesP = returnTimesP;
	}

	public Timestamp getStratTime() {
		return StratTime;
	}

	public void setStratTime(Timestamp stratTime) {
		StratTime = stratTime;
	}

	public Timestamp getEndTime() {
		return EndTime;
	}

	public void setEndTime(Timestamp endTime) {
		EndTime = endTime;
	}
}
package cn.concox.app.aipay.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.aipay.PayRecord;

public interface PayRecordMapper <T> extends BaseSqlMapper<T>{
    
    PayRecord selectByNo(@Param("outTradeNo")String outTradeNo);
    
    /**
     * 通过支付单号获取支付成功记录
     * @author huangwm
     * @date 2016年8月1日上午12:37:19
     * @param payBillsNo
     * @return
     */
	List<PayRecord> selectByBillNoSuc(@Param("payBillsNo")String payBillsNo);
}
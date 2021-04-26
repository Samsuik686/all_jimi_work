package cn.concox.app.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.report.BadRateReport;

public interface BadrateReportMapper<T> extends BaseSqlMapper<T> {

    public List<BadRateReport> badrateList(BadRateReport report);

    /**
     * @Title: listAllMcType
     * @Description:获取所有设备型号
     * @return
     * @author Wang Xirui
     * @date 2019年12月9日 下午4:23:31
     */
    List<String> listAllMcType();

    /**
     * @Title: updateGoods
     * @Description:更新机型出货数量
     * @param mcType
     * @param number
     * @return
     * @author Wang Xirui
     * @date 2019年12月9日 下午4:23:35
     */
    int updateGoods(@Param("mcType") String mcType, @Param("number") int number);

    /**
     * @Title: insertGoods
     * @Description:插入机型及其出货数量
     * @param mcType
     * @param number
     * @author Wang Xirui
     * @date 2019年12月9日 下午4:23:38
     */
    void insertGoods(@Param("mcType") String mcType, @Param("number") int number);
}

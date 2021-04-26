package cn.concox.app.basicdata.mapper;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.Tip;

/**
 * <pre>
 * FinalfailureMapper 数据访问接口
 * </pre>
 */
public interface TipMapper<T> extends BaseSqlMapper<T> {
	
	public Tip findTipCountByEnumWS(Tip tip);
}
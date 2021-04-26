/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2020.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2020-09-22             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.app.basicdata.mapper;

import cn.concox.comm.base.rom.BaseSqlMapper;
import cn.concox.vo.basicdata.TimeoutReason;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
public interface TimeoutReasonMapper extends BaseSqlMapper<TimeoutReason> {

    public List<TimeoutReason> selectTimeoutReason(@Param("po") TimeoutReason timeoutReason);


    public void deleteTimeoutReasonById(Long id);

    public void insertTimeoutReason(@Param("po") TimeoutReason timeoutReason);

    public void update(@Param("po")TimeoutReason timeoutReason);

    public Integer hasReason(String reason);

}

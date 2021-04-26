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


package cn.concox.app.basicdata.service;

import cn.concox.app.basicdata.mapper.TimeoutReasonMapper;
import cn.concox.vo.basicdata.TimeoutReason;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 * 超期原因
 * @author pengshoulong
 * @date 2020-09-22
 * @since 1.0.0
 */
@Service
public class TimeoutReasonService {
    @Resource
    private TimeoutReasonMapper reasonMapper;

    public List<TimeoutReason> getTimeoutReason(TimeoutReason timeoutReason){
       return reasonMapper.selectTimeoutReason(timeoutReason);
    }

    public boolean hasReason(String reason){
        Integer integer = reasonMapper.hasReason(reason);
        if( integer!=null && integer>0){
            return true;
        }
        return false;
    }

    public void modifyTimeoutReason(TimeoutReason timeoutReason){
        reasonMapper.update(timeoutReason);
    }
    /**
     * 根据ID删除超期原因
     * @param timeoutReason
     */
    public void removeTimeoutReason(TimeoutReason timeoutReason){
        if(timeoutReason.getId() != null) {
            reasonMapper.deleteTimeoutReasonById(timeoutReason.getId());
        }
    }


    /**
     * 添加超期原因
     * @param timeoutReason
     * @return
     */
    public void addTimeoutReason(TimeoutReason timeoutReason){
        timeoutReason.setId(null);
        timeoutReason.setReason(timeoutReason.getReason().trim());
        reasonMapper.insertTimeoutReason(timeoutReason);
    }


}

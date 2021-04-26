/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019年6月26日    Administrator         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.basicdata;

import java.io.Serializable;
import java.util.Date;

/**
 * @FileName ProlongInfo.java
 * @Description:延保记录
 *
 * @Date 2019年6月26日 上午9:49:56
 * @author Administrator
 * @version 1.0
 */
public class ProlongInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 字符串
     */
    private String imeiStr;

    /**
     * 设备IMEI
     */
    private String imei;

    /**
     * 批次号
     */
    private String repairNumber;

    /**
     * 延保时长（年）
     */
    private Integer prolongYear;

    /**
     * 延保费用
     */
    private String prolongCost;

    /**
     * 延保到期时间
     */
    private Date warrantyDate;
    
    /**
     * @Title: isWarranty 
     * @Description:判断延保后的包内保外
     * @return 
     * @author Wang Xirui
     * @date 2019年6月26日 下午5:12:13
     */
    public int isWarranty() {
        Date date = new Date();
        if (date.getTime() < this.warrantyDate.getTime()) {
            return 0;
        }
        return 1;
    }

    public String getImeiStr() {
        return imeiStr;
    }

    public void setImeiStr(String imeiStr) {
        this.imeiStr = imeiStr;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRepairNumber() {
        return repairNumber;
    }

    public void setRepairNumber(String repairNumber) {
        this.repairNumber = repairNumber;
    }

    public Integer getProlongYear() {
        return prolongYear;
    }

    public void setProlongYear(Integer prolongYear) {
        this.prolongYear = prolongYear;
    }

    public String getProlongCost() {
        return prolongCost;
    }

    public void setProlongCost(String prolongCost) {
        this.prolongCost = prolongCost;
    }

    public Date getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Date warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

}

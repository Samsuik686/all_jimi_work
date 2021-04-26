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
 * 2019年12月25日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.basicdata;

import java.util.Date;

import cn.concox.vo.commvo.CommonParam;

/**
 * @FileName DeviceInfo.java
 * @Description:
 *
 * @Date 2019年12月25日 下午2:36:16
 * @author Wang Xirui
 * @version 1.0
 */
public class DeviceInfo extends CommonParam {

    private String imei;

    private String imeis;

    /**
     * 主板软件版本
     */
    private String sfVersion;

    /**
     * 订单号
     */
    private String bill;

    /**
     * 公司名称
     */
    private String cpName;

    /**
     * 设备型号
     */
    private String mcType;

    /**
     * 主板类别
     */
//    private Integer zbxhId;

    /**
     * 主板类型名
     */
//    private Integer zhxhName;

    /**
     * 出货时间
     */
    private Date outDate;

    /**
     * 生产时间
     */
    private String productionDate;

    private String outDateStart;

    private String outDateEnd;

    private String productionDateStart;

    private String productionDateEnd;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImeis() {
        return imeis;
    }

    public void setImeis(String imeis) {
        this.imeis = imeis;
    }

    public String getSfVersion() {
        return sfVersion;
    }

    public void setSfVersion(String sfVersion) {
        this.sfVersion = sfVersion;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getMcType() {
        return mcType;
    }

    public void setMcType(String mcType) {
        this.mcType = mcType;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getOutDateStart() {
        return outDateStart;
    }

    public void setOutDateStart(String outDateStart) {
        this.outDateStart = outDateStart;
    }

    public String getOutDateEnd() {
        return outDateEnd;
    }

    public void setOutDateEnd(String outDateEnd) {
        this.outDateEnd = outDateEnd;
    }

    public String getProductionDateStart() {
        return productionDateStart;
    }

    public void setProductionDateStart(String productionDateStart) {
        this.productionDateStart = productionDateStart;
    }

    public String getProductionDateEnd() {
        return productionDateEnd;
    }

    public void setProductionDateEnd(String productionDateEnd) {
        this.productionDateEnd = productionDateEnd;
    }

//    public Integer getZbxhId() {
//        return zbxhId;
//    }
//
//    public void setZbxhId(Integer zbxhId) {
//        this.zbxhId = zbxhId;
//    }
//
//    public Integer getZhxhName() {
//        return zhxhName;
//    }
//
//    public void setZhxhName(Integer zhxhName) {
//        this.zhxhName = zhxhName;
//    }
}

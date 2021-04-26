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
 * 2019年8月16日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.basicdata;

import java.util.List;

import cn.concox.vo.commvo.CommonParam;

/**
 * @FileName WarrantyInfo.java
 * @Description:
 *
 * @Date 2019年8月16日 上午10:07:42
 * @author Wang Xirui
 * @version 1.0
 */
public class WarrantyInfo extends CommonParam {

    private String imei;

    private String imeis;

    private List<String> imeiList;

    private String sfVersion;

    private String bill;

    private String cpName;

    private String mcType;

    /**
     * 延保期限，年
     */
    private Integer prolongMonth;

    private String createTime;

    private String createBy;

    private String createTimeStart;

    private String createTimeEnd;

    private Integer prolongMonthStart;

    private Integer prolongMonthEnd;

    public WarrantyInfo() {

    }

    public WarrantyInfo(String imei) {
        this.imei = imei;
    }

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

    public List<String> getImeiList() {
        return imeiList;
    }

    public void setImeiList(List<String> imeiList) {
        this.imeiList = imeiList;
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

    public Integer getProlongMonth() {
        return prolongMonth;
    }

    public void setProlongMonth(Integer prolongMonth) {
        this.prolongMonth = prolongMonth;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Integer getProlongMonthStart() {
        return prolongMonthStart;
    }

    public void setProlongMonthStart(Integer prolongMonthStart) {
        this.prolongMonthStart = prolongMonthStart;
    }

    public Integer getProlongMonthEnd() {
        return prolongMonthEnd;
    }

    public void setProlongMonthEnd(Integer prolongMonthEnd) {
        this.prolongMonthEnd = prolongMonthEnd;
    }

}

package com.jimilab.uwclient.bean.pcb_bean;

import com.jimilab.uwclient.adapter.PcblmpDetaileAdapter;

import java.util.ArrayList;

public class mission_detail_bean {
    String no;
    String packingListItemId;
    String actuallyQuantity;
    String planQuantity;
    String specification;
    String cycle;
    String scanNum;
    String cutBoolean;
    String designator;
    String oldestPcbMaterialPosition;
    String lackQuantity;
    String oldestMaterialDate;
    String supplier;
    String storeQuantity;
    String infos;
    String cutString;

    public mission_detail_bean() {
    }

    public mission_detail_bean(String no, String packingListItemId, String actuallyQuantity, String planQuantity, String specification, String cycle, String scanNum, String cutBoolean, String designator, String oldestPcbMaterialPosition, String lackQuantity, String oldestMaterialDate, String supplier, String storeQuantity, String infos, String cutString, boolean spread, PcblmpDetaileAdapter adapter, ArrayList<mission_detail_son_bean> list) {
        this.no = no;
        this.packingListItemId = packingListItemId;
        this.actuallyQuantity = actuallyQuantity;
        this.planQuantity = planQuantity;
        this.specification = specification;
        this.cycle = cycle;
        this.scanNum = scanNum;
        this.cutBoolean = cutBoolean;
        this.designator = designator;
        this.oldestPcbMaterialPosition = oldestPcbMaterialPosition;
        this.lackQuantity = lackQuantity;
        this.oldestMaterialDate = oldestMaterialDate;
        this.supplier = supplier;
        this.storeQuantity = storeQuantity;
        this.infos = infos;
        this.cutString = cutString;
        this.spread = spread;
        this.adapter = adapter;
        this.list = list;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPackingListItemId() {
        return packingListItemId;
    }

    public void setPackingListItemId(String packingListItemId) {
        this.packingListItemId = packingListItemId;
    }

    public String getActuallyQuantity() {
        return actuallyQuantity;
    }

    public void setActuallyQuantity(String actuallyQuantity) {
        this.actuallyQuantity = actuallyQuantity;
    }

    public String getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(String planQuantity) {
        this.planQuantity = planQuantity;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getScanNum() {
        return scanNum;
    }

    public void setScanNum(String scanNum) {
        this.scanNum = scanNum;
    }

    public String getCutBoolean() {
        return cutBoolean;
    }

    public void setCutBoolean(String cutBoolean) {
        this.cutBoolean = cutBoolean;
    }

    public String getDesignator() {
        return designator;
    }

    public void setDesignator(String designator) {
        this.designator = designator;
    }

    public String getOldestPcbMaterialPosition() {
        return oldestPcbMaterialPosition;
    }

    public void setOldestPcbMaterialPosition(String oldestPcbMaterialPosition) {
        this.oldestPcbMaterialPosition = oldestPcbMaterialPosition;
    }

    public String getLackQuantity() {
        return lackQuantity;
    }

    public void setLackQuantity(String lackQuantity) {
        this.lackQuantity = lackQuantity;
    }

    public String getOldestMaterialDate() {
        return oldestMaterialDate;
    }

    public void setOldestMaterialDate(String oldestMaterialDate) {
        this.oldestMaterialDate = oldestMaterialDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(String storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getCutString() {
        return cutString;
    }

    public void setCutString(String cutString) {
        this.cutString = cutString;
    }

    boolean spread = false;
    PcblmpDetaileAdapter adapter;
    ArrayList<mission_detail_son_bean> list = new ArrayList<mission_detail_son_bean>();

    public PcblmpDetaileAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PcblmpDetaileAdapter adapter) {
        this.adapter = adapter;
    }


    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public ArrayList<mission_detail_son_bean> getList() {
        return list;
    }

    public void setList(ArrayList<mission_detail_son_bean> list) {
        this.list = list;
    }

}

/*
 * Created: 2016-7-12
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 **/
package cn.concox.comm.jbpm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CountersignInfo extends Serializable{
	
	/** 
     * 用户下达会签结论 
     *  
     * @param user 
     * @param conclusion 
     * @return 
     */  
    public boolean sign(String user, Conclusion conclusion);  
  
    /** 
     * 获取会签人员列表 
     *  
     * @return 
     */  
    public List<String> getUsers();  
  
    /** 
     * 获取会签会议结论 
     *  
     * @return 
     */  
    public Conclusion getConclusion();  
  
    /** 
     * 是否全部人员都已签完 
     *  
     * @return 
     */  
    public boolean isAllSigned();  
  
    /** 
     * 获取特定用户的会签结论 
     *  
     * @param userId 
     * @return 
     */  
    public Conclusion getUserConclusion(String userId);  
  
    /** 
     * 获取所有用户的会签结论 
     *  
     * @return 
     */  
    public Map<String, Conclusion> getConclusions();  
  
    /** 
     * 获取会签结论计算方式 
     *  
     * @return 
     */  
    public CountersignCalculator getConclusionCalculator();  

}

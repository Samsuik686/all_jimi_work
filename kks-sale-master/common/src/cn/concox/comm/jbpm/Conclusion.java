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

public enum Conclusion implements Serializable{
	
	// 通过  
    AGREE,  
    // 否决  
    DENY,  
    // 弃权  
    ABSTAIN,  
    // 继续（会签没有结束）  
    CONTINUE;  
  
    public static Conclusion getConclusion(String conclusion) {  
        if (AGREE.toString().equals(conclusion)) {  
            return Conclusion.AGREE;  
        } else if (DENY.toString().equals(conclusion)) {  
            return Conclusion.DENY;  
        } else if (ABSTAIN.toString().equals(conclusion)) {  
            return Conclusion.ABSTAIN;  
        } else if (CONTINUE.toString().equals(conclusion)) {  
            return Conclusion.CONTINUE;  
        }  
        return Conclusion.AGREE;  
    }  

}

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
 * 2019年7月1日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.exam;

import java.io.Serializable;

/**
 * @FileName Examinee.java
 * @Description:
 *
 * @Date 2019年7月1日 下午4:19:24
 * @author Wang Xirui
 * @version 1.0
 */
public class Examinee implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 考生ID
     */
    private String examineeId;

    /**
     * 考生姓名
     */
    private String examineeName;

    /**
     * 评阅完成状态（0-未完成，1-已完成）
     */
    private Integer gradeState;

    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    public String getExamineeName() {
        return examineeName;
    }

    public void setExamineeName(String examineeName) {
        this.examineeName = examineeName;
    }

    public Integer getGradeState() {
        return gradeState;
    }

    public void setGradeState(Integer gradeState) {
        this.gradeState = gradeState;
    }

}

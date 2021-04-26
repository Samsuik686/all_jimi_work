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
 * 2019年6月24日    Wang Xirui         Create the class
 * http://www.jimilab.com/
*/

package cn.concox.vo.basicdata;

import java.math.BigDecimal;

/**
 * @FileName ProlongCost.java
 * @Description:
 *
 * @Date 2019年6月24日 下午3:48:49
 * @author Wang Xirui
 * @version 1.0
 */
public class ProlongCost {


    /**
     * 一年延保价格
     */
    private BigDecimal oneYearPrice;

    /**
     * 二年延保价格
     */
    private BigDecimal twoYearPrice;
    
    /**
     * 三年延保价格
     */
    private BigDecimal threeYearPrice;

    public BigDecimal getOneYearPrice() {
        return oneYearPrice;
    }

    public void setOneYearPrice(BigDecimal oneYearPrice) {
        this.oneYearPrice = oneYearPrice;
    }

    public BigDecimal getTwoYearPrice() {
        return twoYearPrice;
    }

    public void setTwoYearPrice(BigDecimal twoYearPrice) {
        this.twoYearPrice = twoYearPrice;
    }

    public BigDecimal getThreeYearPrice() {
        return threeYearPrice;
    }

    public void setThreeYearPrice(BigDecimal threeYearPrice) {
        this.threeYearPrice = threeYearPrice;
    }

}

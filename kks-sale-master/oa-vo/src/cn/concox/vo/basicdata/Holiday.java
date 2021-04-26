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
 * 2020-10-15             pengshoulong   Create the class
 * http://www.jimilab.com/
 */


package cn.concox.vo.basicdata;

import java.util.Objects;

/**
 * TODO
 * 表示一个区间，从from到to都不上班
 * @author pengshoulong
 * @date 2020-10-15
 * @since 1.0.0
 */
public class Holiday {
        private WorkDate from;
        private WorkDate to;

        public Holiday() {
        }

        public Holiday(WorkDate from, WorkDate to){
            this.from = from;
            this.to = to;
        }

        public WorkDate getFrom() {
            return from;
        }

        public void setFrom(WorkDate from) {
            this.from = from;
        }

        public WorkDate getTo() {
            return to;
        }

        public void setTo(WorkDate to) {
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Holiday holiday = (Holiday) o;
            return Objects.equals(from, holiday.from) &&
                    Objects.equals(to, holiday.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

}

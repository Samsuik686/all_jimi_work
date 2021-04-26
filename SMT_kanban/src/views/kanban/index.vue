<template>
  <div id="kanban">
    <div class="up">
      <el-select
        class="bigsize"
        v-model="value"
        placeholder="请选择"
        @change="selectChanged"
      >
        <el-option
          v-for="item in options"
          :size="60000"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>
    <div class="down">
      <div class="down-up">
        <div id="xuxian" class="upleft">
          <div align="center" id="wishtP">工单信息</div>
          <div id="bigtxt">工单：{{ data.zhidan }}</div>
          <div id="bigtxt">机型：{{ data.machine_name }}</div>
          <div id="bigtxt">套料数：{{ data.nest_quantity }}</div>
          <div id="bigtxt">客户：{{ data.client }}</div>
          <div id="bigtxt">开始时间：{{ data.startTime }}</div>
        </div>
        <div id="xuxian" class="upcenter">
          <div align="center" id="wishtPred">不良项TOP</div>
          <div id="blueP">
            <label class="lable">1:{{ data.bad_record1 }}</label>
          </div>
          <div id="blueP">
            <label class="lable">2:{{ data.bad_record2 }}</label>
          </div>
          <div id="blueP">
            <label class="lable">3:{{ data.bad_record3 }}</label>
          </div>
        </div>
        <div id="xuxian" class="upright">
          <div class="top">
            <div align="center" id="wishtP">班别：{{ data.frequency }}</div>
          </div>
          <div class="down">
            <div class="persion">
              <el-image
                class="persion-img"
                fit="cover"
                :src="data.shife_leader_Pic"
              >
              </el-image>
              <div id="centertxt">生产领班</div>
              <div id="centertxt">{{ data.shife_leader }}</div>
            </div>
            <div class="persion">
              <el-image
                class="persion-img"
                fit="cover"
                :src="data.line_enginner_Pic"
              >
              </el-image>
              <div id="centertxt">跟线工程师</div>
              <div id="centertxt">{{ data.line_enginner }}</div>
            </div>
            <div class="persion">
              <el-image class="persion-img" fit="cover" :src="data.IPQC_Pic">
              </el-image>

              <div id="centertxt">IPQC</div>
              <div id="centertxt">{{ data.IPQC }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="down-down">
        <div id="xuxian" class="left">
          <div id="outputEcharts" style="width: 100%; height: 100%"></div>
        </div>
        <div id="xuxian" class="right">
          <div id="dfectiverateEcharts" style="width: 100%; height: 100%"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getboard } from "@/httpTools/globalUrl";
import { axiosFetch } from "@/httpTools/fetchData";
export default {
  data() {
    return {
      myTimeOut: "",
      isfirstopen: true,
      notgood1: "不良项A",
      notgood2: "不良项B",
      notgood3: "不良项C",
      workorder: "",
      model: "",
      number: "",
      customer: "",
      starttime: "",
      options: [
        {
          value: "301",
          label: "line 301",
        },
        {
          value: "302",
          label: "line 302",
        },
        {
          value: "303",
          label: "line 303",
        },
        {
          value: "304",
          label: "line 304",
        },
        {
          value: "305",
          label: "line 305",
        },
        {
          value: "306",
          label: "line 306",
        },
        {
          value: "307",
          label: "line 307",
        },
        {
          value: "308",
          label: "line 308",
        },
        {
          value: "309",
          label: "line 309",
        },
        {
          value: "310",
          label: "line 310",
        },
      ],
      value: "301",
      data: "",
      x: [],
      y1: [],
      y2: [],
      maxprodect:0,
      maxrate:0
    };
  },
  beforeDestroy() {
    clearTimeout(this.myTimeOut);
    this.isfirstopen = true;
  },
  created() {
    this.setMyTimeOut();
    this.fetchPage();
    this.isfirstopen = true;
  },
  mounted() {},
  methods: {
    selectChanged() {
      this.data = "";
      document.getElementById("outputEcharts").style.visibility = "hidden"; //隐藏
      document.getElementById("dfectiverateEcharts").style.visibility =
        "hidden"; //隐藏
    },
    setMyTimeOut: function () {
      let that = this;
      this.myTimeOut = setInterval(function () {
        that.fetchPage();
      }, 3000);
    },

    fetchPage: function () {
      console.log("fetchPage");

      let options = {
        url: getboard,
        data: {
          line: this.value,
        },
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            document.getElementById("outputEcharts").style.visibility =
              "visible"; //显示
            document.getElementById("dfectiverateEcharts").style.visibility =
              "visible"; //显示
            this.data = res.data.data;
            this.x = this.data.realTimedate.map((item) => item.dateTime);
            this.y1 = this.data.realTimedate.map((item) => item.num);
            this.y2 = this.data.realTimedate.map((item) => item.rate);
            this.creatoutputEcharts();
            this.creatdfectiverateEcharts();
            if(Math.max(...this.y1)>this.data.standard_product){
              this.maxprodect = Math.max(...this.y1)
            }else{
              this.maxprodect = this.data.standard_product
            }

            if(Math.max(...this.y2)>this.data.defects_rate){
              this.maxrate = Math.max(...this.y2)
            }else{
              this.maxrate = this.data.defects_rate
            }
            // this.data.realTimedate.forEach(item => {

            //   item.dateTime = item.dateTime.split(":")[0]
            //    console.log(item.dateTime);
            //   if(item[0]==0){
            //     item.substring()

            //   }
            // });
          } else {
            // this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },

    creatoutputEcharts() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(
        document.getElementById("outputEcharts")
      );
      // 指定图表的配置项和数据
      var option = {
        grid: {},
        title: {
          x: "center",
          text: "时段产量",
          textStyle: {
            //主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
            // fontFamily: 'Arial, Verdana, sans...',
            // fontStyle: 'normal',
            // fontWeight: 'bolder',
            color: "#749f83",
            fontSize: 30,
          },
        },

        // tooltip: {},
        // legend: {
        //     data:['销量']
        // },
        xAxis: {
          data: this.x,
          axisLabel: {
            interval: 0,
            fontSize: 15,
          },
        },
        yAxis: {
          type: "value",
          name: "标产" + this.data.standard_product,
          nameTextStyle: {
            fontSize: 20,
            color: "#749f83",
          },
          max: this.maxprodect,
          min: 0,

          position: "left",
          axisLine: {
            lineStyle: {},
          },
          axisLabel: {
            fontSize: 20,
          },
        },
        series: [
          {
            itemStyle: {
              normal: {
                //这里是重点
                color: function (params) {
                  //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                  //   var colorList = [
                  //     "#c23531",
                  //     "#2f4554",
                  //     "#61a0a8",
                  //     "#d48265",
                  //     "#91c7ae",
                  //     "#749f83",
                  //     "#ca8622",
                  //   ];
                  //   var index;
                  //   //给大于颜色数量的柱体添加循环颜色的判断
                  //   if (params.dataIndex >= colorList.length) {
                  //     index = params.dataIndex - colorList.length;
                  //     return colorList[index];
                  //   }
                  return "#749f83";
                },
              },
            },
            name: "销量",
            label: {
              //   show: true,
              //   position: "top",
              normal: {
                show: true,
                position: "inside",
                // formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比
                textStyle: {
                  align: "center",
                  baseline: "middle",
                  fontFamily: "微软雅黑",
                  fontSize: 15,
                  fontWeight: "bolder",
                },
              },
            },
            type: "bar",
            data: this.y1,
            markLine: {
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 5, //设置线条粗细
                  },
                },
              },
              symbol: "none",
              data: [
                {
                  silent: false, //鼠标悬停事件  true没有，false有
                  //   lineStyle: {
                  //     //警戒线的样式  ，虚实  颜色
                  //     type: "solid",
                  //     color: "#FA3934",
                  //   },
                  //   label: {
                  //     position: "end",
                  //     formatter: "表产(160)",
                  //     fontSize: "15",
                  //   },
                  yAxis: this.data.standard_product, // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值
                  label: {
                    fontSize: "20",
                  },
                },
              ],
            },
          },
        ],
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },

    creatdfectiverateEcharts() {
      // 基于准备好的dom，初始化echarts实例
      let myChart2 = this.$echarts.init(
        document.getElementById("dfectiverateEcharts")
      );
      // 指定图表的配置项和数据
      var option = {
        grid: {},
        title: {
          x: "center",
          text: "产品不良率",
          textStyle: {
            //主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
            // fontFamily: 'Arial, Verdana, sans...',
            // fontStyle: 'normal',
            // fontWeight: 'bolder',
            fontSize: 30,
            color: "#c23531",
          },
        },
 
        // tooltip: {},
        // legend: {
        //     data:['销量']
        // },
        xAxis: {
          data: this.x,
          axisLabel: {
            interval: 0,
            fontSize: 15,
          },
        },
        yAxis: {
          type: "value",
          name: "标准不良率:" + this.data.defects_rate + "%",
          max:this.maxrate,
          min: 0,
          nameTextStyle: {
            color: "#c23531",
            fontSize: 20,
          },

          position: "left",
          axisLine: {
            lineStyle: {},
          },
          axisLabel: {
            formatter: "{value} %",
            fontSize: 20,
          },
        },
        series: [
          {
            itemStyle: {
              normal: {
                //这里是重点
                color: function (params) {
                  //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                  //   var colorList = [
                  //     "#c23531",
                  //     "#2f4554",
                  //     "#61a0a8",
                  //     "#d48265",
                  //     "#91c7ae",
                  //     "#749f83",
                  //     "#ca8622",
                  //   ];
                  //   var index;
                  //   //给大于颜色数量的柱体添加循环颜色的判断
                  //   if (params.dataIndex >= colorList.length) {
                  //     index = params.dataIndex - colorList.length;
                  //     return colorList[index];
                  //   }
                  return "#c23531";
                },
              },
            },

            name: "销量",
            label: {
              //   show: true,
              //   position: "top",
              //   formatter: function (params) {
              //     //标签内容
              //     return params.value + "%";
              //   },
              normal: {
                show: true,
                position: "inside",
                // formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比
                formatter: function (params) {
                  //标签内容
                  return params.value + "%";
                },
                textStyle: {
                  align: "center",
                  baseline: "middle",
                  fontFamily: "微软雅黑",
                  fontSize: 15,
                  fontWeight: "bolder",
                },
              },
            },
            type: "bar",
            data: this.y2,
            markLine: {
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 5, //设置线条粗细
                  },
                },
              },
              symbol: "none",
              data: [
                {
                  silent: false, //鼠标悬停事件  true没有，false有
                  //   lineStyle: {
                  //     //警戒线的样式  ，虚实  颜色
                  //     type: "solid",
                  //     color: "#FA3934",
                  //   },

                  yAxis: this.data.defects_rate, // 警戒线的标注值，可以有多个yAxis,多条警示线   或者采用   {type : 'average', name: '平均值'}，type值有  max  min  average，分为最大，最小，平均值
                  label: {
                    fontSize: "20",
                  },
                },
              ],
            },
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart2.setOption(option);
    },
  },
};
</script>
<style scoped lang="scss">
#xuxian {
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #456b94;
}
p {
  margin-left: 28px;
  font-size: 22px;
  font-weight: bold;
}
#centertxt {
  margin-top: 5px;
  border-radius: 4px;
  font-size: 22px;
  font-weight: bold;
}
#bigtxt {
  margin-left: 45px;
  margin-top: 10px;
  width: 100%;

  border-radius: 4px;
  font-size: 22px;
  font-weight: bold;
}
#blueP {
  height: 20%;
  padding: 9px;
  color: white;
  margin-top: 15px;
  width: 350px;
  background: #c23531;
  border-radius: 4px;
  font-size: 22px;
  font-weight: bold;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .lable {
  }
}
#wishtP {
  color: #365b7c;
  margin-top: 10px;
  width: 350px;
  border-radius: 4px;
  font-size: 26px;
  font-weight: bold;
}
#wishtPred {
  color: #c23531;
  margin-top: 10px;
  width: 350px;
  border-radius: 4px;
  font-size: 26px;
  font-weight: bold;
}

#kanban {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  .up {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 10%;
  }
  .down {
    width: 100%;
    height: 90%;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    .down-up {
      padding: 5dp;
      display: flex;
      flex-direction: row;
      justify-content: space-between;

      width: 100%;
      height: 50%;
      .upleft {
        width: 30%;
        height: 100%;
        padding: 20px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
      }
      .upcenter {
        padding: 20px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        margin-left: 5px;
        margin-right: 5px;
        width: 30%;
        height: 100%;
      }
      .upright {
        padding: 20px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;
        width: 40%;
        height: 100%;
        .top {
          width: 100%;
          display: flex;
          flex-direction: column;
          align-items: center;
          height: 20%;
        }

        .down {
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          align-items: center;
          width: 100%;
          height: 80%;
          .persion {
            height: 100%;
            width: 33%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: center;
            .persion-img {
              height: 80%;
              width: 75%;
            }
          }
        }
      }
    }
    .down-down {
      padding: 5px;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
      height: 50%;
      .left {
        width: 50%;
        height: 100%;
      }
      .right {
        margin-left: 5px;
        width: 50%;
        height: 100%;
      }
    }
  }
}
</style>
<template>
  <div id="sys-log" ref="sys-log">
    <div class="query-comp">
      <div class="query-comp-btn">
        <label class="query-comp-btn">线号：</label>
        <el-select
          v-model="linenumber"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in options"
            :key="item.key"
            :label="item.label"
            :value="item.key"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">工单号：</label>
        <el-input
          :disabled="true"
          v-model="workordernum"
          placeholder="请选择产线"
        ></el-input>
      </div>
    </div>
    <div class="data-comp">
      <div
        v-for="(item, index) in tableData"
        :key="item.bad_reason"
        class="card"
      >
        <div class="card_item">
          <span class="blackbroderbigsize">{{ item.bad_reason }}</span>
          <span class="blackbroder">{{ item.num }}</span>
        </div>
        <div class="card_item-second">
          <el-input
            class="center-input"
            v-model="input[index]"
            placeholder="请输入数量"
          ></el-input>
        </div>
      </div>
      <div v-show="showcard" id="botton_card" class="card-center">
        <div class="card_item">
          <el-button type="primary" @click="submitall">提交</el-button>
          <el-button type="warning" @click="clear">清空</el-button>
        </div>
      </div>
    </div>
    <el-dialog
     title="提示"
      class="form-panel"
      :visible.sync="showtimetodonotgood"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <span class="bigsize_span">请登记当前小时不良件</span>
    </el-dialog>
  </div>
</template>

<script>
import { getList } from "@/api/table";
import { axiosFetch } from "@/httpTools/fetchData";
import { selectOptions } from "@/config/LogConfig";
import {
  getbedlinnerUrl,
  adderrorUrl,
  reduceerrorUrl,
  addallbadrecordUrl,
} from "@/httpTools/globalUrl";
export default {
  inject: ["reload"],
  data() {
    return {
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      showtimetodonotgood: false,
      options: JSON.parse(JSON.stringify(selectOptions[0].list)),
      linenumber: "", //产线
      workordernum: "", //工单号
      tableData: [],
      delec_add_thttp: {},
      input: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      showcard: false,
      selectline: 301,
    };
  },
  created() {
    this.timer = setInterval(() => {
      var min = new Date().getMinutes();
      if (min == 50) {
        console.log("sssss");
        this.showtimetodonotgood = true;
      }
    }, 59000);
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  mounted() {},
  methods: {
    submitall() {
      var numstr = "";
      for (var num = 0; num < this.input.length; num++) {
        if (this.input[num] < 0) {
          return false;
        } else {
          if (this.input[num] === null || this.input[num] === "")
            this.input[num] = "0";
          numstr += this.input[num] + ",";
        }
      }
      var str1 = numstr.substring(0, numstr.length - 1);
      let options = {
        url: addallbadrecordUrl,
        data: {
          line: this.linenumber,
          zhidan: this.workordernum,
          numStr: str1,
        },
      };
      axiosFetch(options)
        .then((res) => {
          if (res.data.code === 200) {
            this.$alertSuccess("操作成功！");
            this.selectSystemChanged(this.selectline);
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },
    clear() {
      this.input = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    },
    delectitem(row, index) {
      console.log(index);
      this.delecthttp = row;
      let options = {
        url: reduceerrorUrl,
        data: {
          line: this.linenumber,
          zhidan: this.workordernum,
          bad_reason: this.delecthttp.bad_reason,
          num: this.delecthttp.num,
        },
      };
      axiosFetch(options)
        .then((res) => {
          if (res.data.code === 200) {
            this.$alertSuccess("操作成功！");
            this.tableData[index].num--;
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },

    additem(row, index) {
      this.delecthttp = row;
      let options = {
        url: adderrorUrl,
        data: {
          line: this.linenumber,
          zhidan: this.workordernum,
          bad_reason: this.delecthttp.bad_reason,
          num: this.delecthttp.num,
        },
      };
      axiosFetch(options)
        .then((res) => {
          if (res.data.code === 200) {
            this.$alertSuccess("操作成功！");
            this.tableData[index].num++;
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },

    selectSystemChanged(value) {
      this.selectline = value;
      this.tableData = {};
      console.log("selectSystemChanged", value);
      // this.workordernum = value+"正在进行的任务单号"
      let options = {
        url: getbedlinnerUrl,
        data: {
          line: this.linenumber,
        },
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.workordernum = res.data.data.zhidan;
            this.tableData = res.data.data.bad_record;
            this.input = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
            this.showcard = true;
          } else {
            this.$alertWarning(res.data.data);
            this.showcard = false;
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },
  },
};
</script>

<style scoped lang="scss">
#sys-log {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  .input {
    margin-left: 20px;
    width: 100px;
    height: 60px;
    padding-bottom: 7px;
    box-sizing: border-box;
    display: flex;
    align-items: flex-end;
  }
}

.data-comp {
  overflow: auto;
  height: 110%;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  flex-wrap: wrap;
  width: 100%;
  .card-center {
    height: 110px;
    display: flex;
    align-items: center;
    flex-direction: row;
    justify-content: space-around;
    margin: 14px;
    // background: rgb(230, 244, 255);
    border: 1px solid rgba(203, 205, 206, 0.63);
    width: 22%;
  }
  .card {
    height: 110px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin: 14px;
    // background: rgb(230, 244, 255);
    border: 1px solid rgba(203, 205, 206, 0.63);
    width: 22%;
    .card_item-second {
      align-items: center;
      margin-top: 45dp;
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      width: 100%;
      height: 100%;
      .center-input {
        align-items: center;
        margin-top: 30dp;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        width: 50%;
        height: 30px;
      }
    }
    .card_item {
      align-items: center;
      margin: 30dp;
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      width: 100%;
      .blackbroderbigsize {
        font-size: 25px;
        margin-top: 10px;
        padding: 10px;
      }
      .blackbroder {
        border-radius: 10px;
        align-items: center;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        width: 70px;
        height: 30px;
        background: #456b94;
        color: white;
        margin-top: 10px;
        padding: 10px;
      }
    }
    .item_class {
      align-items: center;
      margin: 30dp;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      width: 100%;
      .item_button {
        height: 100%;
        width: 50%;
      }
    }
  }
}
</style>


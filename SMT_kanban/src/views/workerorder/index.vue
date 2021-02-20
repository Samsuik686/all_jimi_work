<template>
  <div id="sys-log" ref="sys-log">
    <div class="query-comp">
      <div
        v-for="(item, index) in selectOptions"
        :key="index"
        class="query-comp-container"
      >
        <div v-if="item.type === 'text'" class="query-comp-btn">
          <label class="query-comp-btn" :for="item.key + index">{{
            item.label
          }}</label>
          <el-input
            class="query-comp-btn-kuang"
            :id="item.key + index"
            ref="queryInput"
            v-model.trim="queryDetails[item.key]"
            :placeholder="'请输入' + item.label"
            autocomplete="false"
            clearable
          />
        </div>
        <div v-if="item.type === 'select'" class="query-comp-btn">
          <label class="query-comp-btn" :for="item.key + index">{{
            item.label
          }}</label>
          <el-select
            class="query-comp-btn-kuang"
            :id="item.key + index"
            v-model="queryDetails[item.key]"
            :placeholder="'请选择' + item.label"
            autocomplete="false"
            clearable
          >
            <el-option
              v-for="(iItem, iIndex) in item.list"
              :key="iIndex.key"
              :value="iItem.key"
              :label="iItem.label"
            />
          </el-select>
        </div>
      </div>
      <div class="query-comp-btn">
        <div class="block">
          <span class="demonstration">开始日期</span>
          <el-date-picker
            class="query-comp-btn-kuang"
            v-model="queryDetails.startTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd/00:00:00"
            format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
      </div>
      <div class="query-comp-btn-left">
        <div class="block">
          <span class="demonstration">结束日期</span>
          <el-date-picker
            class="query-comp-btn-kuang"
            v-model="queryDetails.endTime"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd/23:59:59"
            format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
      </div>

      <div class="query-comp-btn-left">
        <el-button type="primary" icon="el-icon-search" @click="queryData"
          >确定</el-button
        >
        <el-button type="info" icon="el-icon-close" @click="clearOptions"
          >重置</el-button
        >
        <el-button type="primary" @click="shownewitem">新建工单</el-button>
      </div>
    </div>

    <!-- 数据模块 -->
    <div class="data-comp">
      <el-table :data="tableData" style="width: 100%" height="100%" stripe>
        <el-table-column
          type="index"
          label="序号"
          header-align="center"
          width="50"
          :index="indexMethod"
        />
        <el-table-column
          v-for="(item, index) in tableTitle"
          :key="index + item.label"
          :label="item.label"
          :prop="item.key"
          :min-width="item['min-width']"
          :formatter="item.formatter"
        />
        <el-table-column label="详情" min-width="120">
          <template slot-scope="scope">
            <el-tooltip
              content="修改"
              placement="top"
              :open-delay="0"
              :enterable="false"
            >
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="openDetailPanel(scope.row)"
              />
            </el-tooltip>
            <el-tooltip
              content="设置状态"
              placement="top"
              :open-delay="0"
              :enterable="false"
            >
              <el-button
                type="text"
                icon="el-icon-s-order"
                @click="changePanelstate(scope.row)"
              />
            </el-tooltip>
            <el-tooltip
              content="删除"
              placement="top"
              :open-delay="0"
              :enterable="false"
            >
              <el-button
                type="text"
                icon="el-icon-delete"
                @click="delectorderstate(scope.row)"
              />
            </el-tooltip>
            <el-tooltip
              content="停止警报"
              placement="top"
              :open-delay="0"
              :enterable="false"
            >
              <el-button
                style="color: red"
                type="text"
                icon="el-icon-coordinate"
                @click="stopitem(scope.row)"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 修改标产，不良率 -->
    <el-dialog
      class="query-comp-dialog"
      title="修改"
      :visible.sync="showDetails"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="500px"
    >
      <div class="query-comp-container">
        <div class="query-comp-btn">
          <label class="query-comp-label">标产：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="changequery.standard_product"
            placeholder="请输入标产"
          ></el-input>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">连板数：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="changequery.consecutive_number"
            placeholder="请输入连板数"
          ></el-input>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">不良率：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="changequery.defects_rate"
            placeholder="请输入不良率"
          ></el-input>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="changeitem"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 修改状态 -->
    <el-dialog
      class="form-panel"
      title="修改状态"
      :visible.sync="panelstate"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <el-select v-model="changequery.state" placeholder="请选择状态">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="changeitem"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 删除 -->
    <el-dialog
      class="form-panel"
      :visible.sync="showdelect"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <span class="bigsize_span">是否删除</span>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button size="small" @click="cannaldelect">取消</el-button> -->
        <el-button size="small" type="primary" @click="delectitem"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 停止报警 -->
    <el-dialog
      class="form-panel"
      :visible.sync="showdstop"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <span class="bigsize_span">是否停止报警</span>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button size="small" @click="cannalstop">取消</el-button> -->
        <el-button size="small" type="primary" @click="stopwarning"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- z增加新的item -->
    <el-dialog
      class="query-comp-dialog"
      title="新建产线任务"
      :visible.sync="newitem"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="600px"
    >
      <div class="query-comp-container">
        <div class="query-comp-btn">
          <label class="query-comp-label">产线：</label>
          <!-- <el-select v-model="addquery.line" placeholder="请选择产线" >
        <el-option
          v-for="item in listoptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select> -->
          <el-select
            class="query-comp-btn-kuang"
            v-model="addquery.line"
            placeholder="请选择"
          >
            <el-option
              v-for="item in listoptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">工单：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.zhidan"
            placeholder="请输入工单："
          ></el-input>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">机型：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.machine_name"
            placeholder="请输入机型："
          ></el-input>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">客户：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.client"
            placeholder="请输入客户："
          ></el-input>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">套料数：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.nest_quantity"
            placeholder="请输入套料总数："
          ></el-input>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">不良率：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.defects_rate"
            placeholder="请输入不良率："
          ></el-input
          >%
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">标产：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.standard_product"
            placeholder="请输入标产："
          ></el-input>
        </div>

        <div class="query-comp-btn">
          <label class="query-comp-label">连板数：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="addquery.consecutive_number"
            placeholder="请输入连板数："
          ></el-input>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <!-- <el-button size="small" @click="closenewitem">取消</el-button> -->
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="startnewitem"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <!-- 翻页 -->
    <div class="paging">
      <el-pagination
        background
        small
        :current-page.sync="paginationOptions.currentPage"
        :page-sizes="[20, 40, 80]"
        :page-size.sync="paginationOptions.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="paginationOptions.total"
        class="page-pagination"
        @current-change="fetchPage"
        @size-change="queryData"
      />
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
import {
  selectOptions,
  tableTitle,
  formPanelOptions,
} from "@/config/LogConfig";
import { department, operationLog, user } from "@/api/system";
import { axiosFetch } from "@/httpTools/fetchData";
import {
  getworderorderUrl,
  addworkderorderUrl,
  changeworkderorderUrl,
  delectworkderorderUrl,
  stopalarmUrl,
} from "@/httpTools/globalUrl";
import { logger } from "runjs/lib/common";
// import { Loading } from 'element-ui'

export default {
  name: "SysLog",
  inject: ["reload"],
  data() {
    return {
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      showtimetodonotgood: false,
      selectid: "",
      options: [
        {
          value: "进行中",
          label: "进行中",
        },
        {
          value: "已完成",
          label: "已完成",
        },
      ],
      statesvalue: "",
      /* 新建线号项 */
      listoptions: [
        { value: "301", label: "301" },
        { value: "302", label: "302" },
        { value: "303", label: "303" },
        { value: "304", label: "304" },
        { value: "305", label: "305" },
        { value: "306", label: "306" },
        { value: "307", label: "307" },
        { value: "308", label: "308" },
        { value: "309", label: "309" },
        { value: "310", label: "310" },
      ],
      newlistvulue: "",
      /* 新建产线工单号 */
      newworkorder: "",
      /* 客户列表 */
      customerlist: "",
      customervalue: "",
      /* 用户项 */
      isPending: false,
      // 查询
      selectOptions: JSON.parse(JSON.stringify(selectOptions)),
      queryDetails: {
        line: "",
        zhidan: "",
        state: "",
        client: "",
        startTime: "",
        endTime: "",
        pageNo: "",
        pageSize: "",
      },
      addquery: {
        line: "",
        zhidan: "",
        machine_name: "",
        nest_quantity: "",
        client: "",
        already_product: "0",
        standard_product: "",
        defects_number: "0",
        defects_rate: "",
        consecutive_number: "",
        state: "未开始",
      },

      changequery: {
        line: "",
        zhidan: "",

        standard_product: "",
        defects_rate: "",
        consecutive_number: "",
        state: "",
      },

      // 数据
      tableTitle: tableTitle,
      deptList: [],
      tableData: [], // 数据
      treeData: [], // 部门列表
      userList: [], // 用户列表

      // 详情面板
      showDetails: false,
      panelstate: false,
      showdelect: false,
      showdstop: false,
      newitem: false,
      detailData: [],

      /* 分页*/
      paginationOptions: {
        currentPage: 1,
        pageSize: 20,
        total: 0,
      },
      standernumber: "",
      connect_num: "",
      badrate: "",
    };
  },
  mounted() {
    this.queryData();
    this.getDepartmentList();
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

  methods: {
    cannaldelect() {
      this.showdelect = false;
    },
    cannalstop() {
      this.showdstop = false;
    },
    stopwarning() {
      let options = {
        url: stopalarmUrl,
        data: {
          id: this.selectid,
        },
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.$alertSuccess("操作成功！");
            this.reload();
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },
    delectitem() {
      let options = {
        url: delectworkderorderUrl,
        data: {
          id: this.selectid,
        },
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.$alertSuccess("操作成功！");
            this.reload();
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },
    changeitem() {
      console.log(this.changequery);
      if (this.changequery.state == "" || this.changequery.state == null) {
        this.$alertDanger("请选择状态后才可点确定");
      } else if (
        this.changequery.standard_product === "" ||
        this.changequery.standard_product === null ||
        this.changequery.defects_rate === "" ||
        this.changequery.defects_rate === null ||
        this.changequery.consecutive_number === "" ||
        this.changequery.consecutive_number === null
      ) {
        this.$alertDanger("修改参数不能为空");
      } else {
        if (this.changequery.defects_rate.indexOf("%") == -1) {
          this.changequery.defects_rate = this.changequery.defects_rate + "%";
        }
        let options = {
          url: changeworkderorderUrl,
          data: this.changequery,
        };
        axiosFetch(options)
          .then((res) => {
            console.log(res);
            if (res.data.code === 200) {
              this.$alertSuccess("操作成功！");
              this.reload();
            } else {
              this.$alertWarning(res.data.data);
            }
          })
          .catch((err) => {
            this.$alertDanger(err);
          })
          .finally(() => {});
      }
    },

    // 新建工单
    startnewitem() {
      console.log(this.addquery);
      if (
        this.addquery.line === null ||
        this.addquery.zhidan === null ||
        this.addquery.machine_name === null ||
        this.addquery.nest_quantity === null ||
        this.addquery.client === null ||
        this.addquery.already_product === null ||
        this.addquery.standard_product === null ||
        this.addquery.defects_number === null ||
        this.addquery.defects_rate === null ||
        this.addquery.consecutive_number === null ||
        this.addquery.line === "" ||
        this.addquery.zhidan === "" ||
        this.addquery.machine_name === "" ||
        this.addquery.nest_quantity === "" ||
        this.addquery.client === "" ||
        this.addquery.already_product === "" ||
        this.addquery.standard_product === "" ||
        this.addquery.defects_number === "" ||
        this.addquery.defects_rate === "" ||
        this.addquery.consecutive_number === ""
      ) {
        this.$alertWarning("参数不能为空");
      } else {
        if (this.addquery.defects_rate.indexOf("%") == -1) {
          this.addquery.defects_rate = this.addquery.defects_rate + "%";
        }

        let options = {
          url: addworkderorderUrl,
          data: this.addquery,
        };
        axiosFetch(options)
          .then((res) => {
            console.log(res);
            if (res.data.code === 200) {
              this.$alertSuccess("操作成功！");
              this.reload();
            } else {
              this.$alertWarning(res.data.data);
            }
          })
          .catch((err) => {
            this.$alertDanger(err);
          })
          .finally(() => {});
      }
    },
    // 获取Table数据
    fetchPage() {
      // if(this.queryDetails.startTime!="" && this.queryDetails.startTime!=null){
      //   var str = this.queryDetails.startTime.substring(0,10)
      //   console.log(str)
      // }
      this.queryDetails.pageNo = this.paginationOptions.currentPage;
      this.queryDetails.pageSize = this.paginationOptions.pageSize;
      let options = {
        url: getworderorderUrl,
        data: this.queryDetails,
      };
      console.log(this.queryDetails);
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.tableData = res.data.data.list;

            this.paginationOptions.total = res.data.data.totalRow;
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
      // this.tableData = [
      //   {
      //     id: 1,
      //     wirenumber: 301,
      //     workorder: 123456,
      //     model: "AT4396",
      //     totalnesting: 2000,
      //     customer: "康凯斯",
      //     produced: 1,
      //     standard: 155,
      //     defectsnumber: 0,
      //     defectiverate: "5%",
      //     state: 1,
      //     connet_num: "100",
      //   },
      //
      // ];
    },
    // 查询
    queryData() {
      this.paginationOptions.currentPage = 1;
      this.fetchPage();
    },

    // 重置查询条件
    clearOptions() {
      this.queryDetails = {};
      this.deptList = [];
      this.queryData();
    },

    // 获取客户数据
    getDepartmentList() {
      this.customerlist = [
        { key: "1", label: "康凯斯" },
        { key: "2", label: "几米互联" },
      ];
    },

    // 选择操作人部门
    // handleOpDept(val) {
    //   console.log(val)
    //   if (val.length > 0) {
    //     user.getList({ deptId: val[val.length - 1] }).then(res => {
    //       if (res.code === 200) {
    //         this.userList = res.data
    //       }
    //     })
    //   } else {
    //     this.userList = []
    //     this.queryDetails.userId = ''
    //   }
    //   console.log(this.queryDetails)
    // },

    // 把部门父子关系的数组转换为树形结构
    translateDataToTree(data) {
      const treeData = [];
      for (let i = 0; i < data.length; i++) {
        const ele = data[i];
        if (!ele.parentId) {
          treeData.push(ele); // ele为根节点时，push到treeData中
          continue;
        }
        translateChildren(treeData, ele);
      }
      function translateChildren(arr, ele) {
        arr.forEach((item) => {
          if (ele.parentId === item.id) {
            if (!item.children) item.children = [];
            item.children.push(ele);
          }
          if (item.children) {
            translateChildren(item.children, ele); // 递归遍历
          }
        });
      }
      return treeData;
    },

    // 打开detail panel
    openDetailPanel(row) {
      if (row.state == "已完成") {
        this.$alertWarning("已完成任务不可修改信息");
        return false;
      }
      this.changequery.line = "";
      this.changequery.zhidan = "";
      this.changequery.standard_product = row.standard_product;
      this.changequery.defects_rate = row.defects_rate;
      this.changequery.consecutive_number = row.consecutive_number;
      this.changequery.state = row.state;
      console.log(row);

      this.changequery.line = row.line;
      this.changequery.zhidan = row.zhidan;
      this.showDetails = true;
      this.detailData = row;
    },

    changePanelstate(row) {
      if (row.state === "已完成") {
        this.$alertWarning("已完成任务不可修改状态");
      } else {
        this.changequery.standard_product = row.standard_product;
        this.changequery.defects_rate = row.defects_rate;
        this.changequery.consecutive_number = row.consecutive_number;
        this.changequery.state = row.state;
        this.changequery.line = "";
        this.changequery.zhidan = "";
        this.changequery.line = row.line;
        this.changequery.zhidan = row.zhidan;
        this.panelstate = true;
        this.detailData = row;
      }
    },

    delectorderstate(row) {
      if (row.state == "未开始") {
        this.selectid = "";
        this.showdelect = true;
        console.log(row);
        this.selectid = row.id;
      } else {
        this.$alertWarning("只有未开始的订单才可删除");
      }
    },

    stopitem(row) {
      if (row.state == "已完成") {
        this.$alertWarning("已完成任务不可进行该操作");
        return false;
      }
      this.selectid = "";
      this.showdstop = true;
      console.log(row);
      this.selectid = row.id;
    },

    shownewitem() {
      this.newitem = true;
    },

    // 关闭setting panel
    closeSettingPanel() {
      this.showDetails = false;
    },

    closenewitem() {
      this.newitem = false;
    },

    indexMethod: function (index) {
      return (
        index +
        (this.paginationOptions.currentPage - 1) *
          this.paginationOptions.pageSize +
        1
      );
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
}
.detail-panel {
  .detail-item {
    display: flex;
    justify-content: space-between;
    //align-items: center;
    width: 100%;
    font-size: 16px;
    margin-bottom: 10px;
    padding: 5px;
    span {
      width: 10%;
      text-align: right;
      line-height: 25px;
    }
    div {
      flex: 1;
      display: flex;
      align-items: center;
      line-height: 25px;
    }
    &:hover {
      background-color: #ddd;
    }
    &-content {
      max-height: 200px;
      overflow-y: auto;
    }
  }
}
</style>

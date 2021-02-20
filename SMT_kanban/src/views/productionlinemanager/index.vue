<template>
  <div id="sys-log" ref="sys-log">
    <div class="query-comp">
      <div class="query-comp-btn">
        <label class="input">姓名：</label>
        <el-input
          v-model="queryDetails.Name"
          placeholder="请输入内容"
        ></el-input>
      </div>
      <div class="query-comp-btn">
        <label class="input">线号：</label>
        <el-select
          v-model="queryDetails.line"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsline"
            :key="item.key"
            :label="item.label"
            :value="item.key"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">工位：</label>
        <el-select
          v-model="queryDetails.station"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsposition"
            :key="item.key"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">班次：</label>
        <el-select
          v-model="queryDetails.frequency"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsfrequency"
            :key="item.key"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn-left">
        <el-button type="primary" icon="el-icon-search" @click="queryData"
          >确定</el-button
        >
        <el-button type="info" icon="el-icon-close" @click="clearOptions"
          >重置</el-button
        >
        <el-button type="primary" @click="shownewitem">新建产线</el-button>
      </div>
    </div>
    <div class="data-comp">
      <el-table :data="tableData" border height="100%" style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          header-align="center"
          :index="indexMethod"
        >
        </el-table-column>
        <el-table-column prop="line" label="线号"> </el-table-column>
        <el-table-column prop="frequency" label="班次"> </el-table-column>
        <el-table-column prop="station" label="工位："> </el-table-column>
        <el-table-column prop="Name" label="姓名"> </el-table-column>

        <el-table-column label="操作" width="160">
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
              content="删除"
              placement="top"
              :open-delay="0"
              :enterable="false"
            >
              <el-button
                type="text"
                icon="el-icon-delete"
                @click="delectitem(scope.row)"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="paging">
      <el-pagination
        background
        small
        :current-page.sync="paginationOptions.currentPage"
        :page-sizes="[80]"
        :page-size.sync="paginationOptions.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="paginationOptions.total"
        class="page-pagination"
        @current-change="fetchPage"
        @size-change="queryData"
      />
    </div>
    <el-dialog
      class="form-panel"
      :title="typename"
      :visible.sync="newitem"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
      top="15px"
    >
      <div class="query-comp-btn">
        <label class="input">线号：</label>
        <el-select
          v-model="add_change.line"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsline"
            :key="item.key"
            :label="item.label"
            :value="item.key"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">班次：</label>
        <el-select
          v-model="add_change.frequency"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsfrequency"
            :key="item.key"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">工位：</label>
        <el-select
          v-model="add_change.station"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsposition"
            :key="item.key"
            :label="item.label"
            :value="item.label"
          >
          </el-option>
        </el-select>
      </div>

      <label class="input">姓名：</label>

      <el-autocomplete
        popper-class="my-autocomplete"
        v-model="add_change.Name"
        :fetch-suggestions="querySearch"
        placeholder="请输入内容"
        @select="handleSelect"
      >
        <i
          class="el-icon-edit el-input__icon"
          slot="suffix"
          @click="handleIconClick"
        >
        </i>
        <template slot-scope="{ item }">
          <div class="name">{{ item.value }}</div>
        </template>
      </el-autocomplete>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="change_add"
          >确定</el-button
        >
      </span>
    </el-dialog>

    <el-dialog
      class="form-panel"
      title="修改人员信息"
      :visible.sync="changeitem"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <label class="input">线号：</label>
      <el-input v-model="add_change.line" :disabled="true"> </el-input>
      <label class="input">班次：</label>
      <el-input v-model="add_change.frequency" :disabled="true"> </el-input>
      <label class="input">工位：</label>
      <el-input v-model="add_change.station" :disabled="true"> </el-input>

      <label class="input">姓名：</label>

      <el-autocomplete
        popper-class="my-autocomplete"
        v-model="add_change.Name"
        :fetch-suggestions="querySearch"
        placeholder="请输入内容"
        @select="handleSelect"
      >
        <i
          class="el-icon-edit el-input__icon"
          slot="suffix"
          @click="handleIconClick"
        >
        </i>
        <template slot-scope="{ item }">
          <div class="name">{{ item.value }}</div>
        </template>
      </el-autocomplete>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="change_add"
          >确定</el-button
        >
      </span>
    </el-dialog>
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
        <el-button size="small" type="primary" @click="suredelectitem"
          >确定</el-button
        >
      </span>
    </el-dialog>
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
import {
  getlinemanage,
  change_add_linemanager,
  delect_linemanager,
  getAllName,
} from "@/httpTools/globalUrl";
export default {
  inject: ["reload"],
  data() {
    return {
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      showtimetodonotgood: false,
      showdelect: false,
      queryDetails: {},
      changename: "",
      newitem: false,
      changeitem: false,
      optionsposition: [
        { key: "生产领班", label: "生产领班" },
        { key: "跟线工程师", label: "跟线工程师" },
        { key: "IPQC", label: "IPQC" },
        { key: "不良数登记员", label: "不良数记录员" },
      ],
      optionsfrequency: [
        { key: "白班", label: "白班" },
        { key: "夜班", label: "夜班" },
      ],
      optionsline: [
        { key: "301", label: "301" },
        { key: "302", label: "302" },
        { key: "303", label: "303" },
        { key: "304", label: "304" },
        { key: "305", label: "305" },
        { key: "306", label: "306" },
        { key: "307", label: "307" },
        { key: "308", label: "308" },
        { key: "309", label: "309" },
        { key: "310", label: "310" },
      ],

      linenumber: "", //产线
      positionnum: "", //工单号
      frequencyselect: "",
      tableData: [],
      /* 分页*/
      paginationOptions: {
        currentPage: 1,
        pageSize: 20,
        total: 0,
      },
      //打开提醒框类型1为新建2为修改
      type: "",
      typename: "",
      add_change: {
        //新建选项姓名，产线，工单号，班次
        Name: "",
        line: "",
        station: "",
        frequency: "",
      },
      delecthttp: {},
      //人员列表
      restaurants: [],
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
  mounted() {
    this.queryData();
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    selectSystemChanged(value) {},
    // 重置查询条件
    clearOptions() {
      this.queryDetails = {};
      this.queryData();
    },

    suredelectitem() {
      let options = {
        url: delect_linemanager,
        data: this.delecthttp,
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
    delectitem(row) {
      console.log(row);
      this.delecthttp = row;
      this.showdelect = true;
    },
    cannaldelect() {
      this.showdelect = false;
    },
    openDetailPanel(row) {
      this.add_change = {};
      this.changeitem = true;
      this.add_change = row;
      // this.add_change.Name = row.Name;
      // this.add_change. line= row.line;
      // this.add_change.station= row.station;
      // this.add_change.frequency= row.frequency;

      // num: '302',
      //     name: '孙小猛',
      //     frequency:'白班',
      //     station:"干饭人",
      //     number:'2'
    },
    shownewitem() {
      this.add_change = {};
      this.typename = "新建产线管理";
      this.newitem = true;
    },
    indexMethod: function (index) {
      return (
        index +
        (this.paginationOptions.currentPage - 1) *
          this.paginationOptions.pageSize +
        1
      );
    },
    // 获取Table数据

    fetchPage() {
      // this.queryDetails.pageNo = this.paginationOptions.currentPage
      // this.queryDetails.pageSize = this.paginationOptions.pageSize
      let options = {
        url: getlinemanage,
        data: this.queryDetails,
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.tableData = res.data.data;
            this.paginationOptions.total = res.data.totalRow;
          } else {
            this.$alertWarning(res.data.data);
          }
        })
        .catch((err) => {
          this.$alertDanger(err);
        })
        .finally(() => {});
    },
    // 查询
    queryData() {
      this.paginationOptions.currentPage = 1;
      this.fetchPage();
    },
    //增加修改

    change_add() {
      console.log(this.add_change.Name);
      // this.queryDetails.pageNo = this.paginationOptions.currentPage
      // this.queryDetails.pageSize = this.paginationOptions.pageSize
      if (
        this.add_change.Name == null ||
        this.add_change.line == null ||
        this.add_change.station == null ||
        this.add_change.frequency == null ||
        this.add_change.Name == "" ||
        this.add_change.line == "" ||
        this.add_change.station == "" ||
        this.add_change.frequency == ""
      ) {
        this.$alertWarning("参数不能为空");
      } else {
        let options = {
          url: change_add_linemanager,
          data: this.add_change,
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
    querySearch(queryString, cb) {
      var list = [{}];

      let options = {
        url: getAllName,
        data: {
          // userType: this.add_change.station,
        },
      };
      axiosFetch(options)
        .then((res) => {
          console.log(res);
          if (res.data.code === 200) {
            this.restaurants = res.data.data;
          } else {
          }
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          console.log(this.restaurants);
          var restaurants = this.restaurants;
          var results = queryString
            ? restaurants.filter(this.createFilter(queryString))
            : restaurants;
          // 调用 callback 返回建议列表的数据
          cb(results);
        });
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (
          restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) ===
          0
        );
      };
    },

    handleSelect(item) {
      console.log(item);
    },
    handleIconClick(ev) {
      console.log(ev);
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
   
    margin-left: 10px;
    width: 60px;
    height: 60px;
    padding-bottom: 7px;
    box-sizing: border-box;
    display: flex;
    align-items: flex-end;
  }
  .input-long {
    margin-left: 10px;
    width: 80px;
    height: 60px;
    padding-bottom: 7px;
    box-sizing: border-box;
    display: flex;
    align-items: flex-end;
  }
}
</style>


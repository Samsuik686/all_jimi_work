<template>
  <div id="sys-log" ref="sys-log">
    <div class="query-comp">
      <div class="query-comp-btn">
        <label class="input">姓名：</label>
        <el-input
          v-model="httpRequare.Name"
          placeholder="请输入内容"
        ></el-input>
      </div>
      <div class="query-comp-btn">
        <label class="input">线号：</label>
        <el-select
          v-model="httpRequare.line"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsline"
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
          v-model="httpRequare.station"
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
      <div class="button-father">
        <el-button type="primary" icon="el-icon-search" @click="queryData">
          确定</el-button
        >
        <el-button type="info" icon="el-icon-close" @click="clearOptions"
          >重置</el-button
        >
      </div>
    </div>
    <div class="data-comp">
      <el-table :data="tableData" border height="100%" style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          header-align="center"
          width="50"
          :index="indexMethod"
        />

        <el-table-column width="100" prop="Name" label="姓名">
        </el-table-column>
        <el-table-column prop="text" label="操作"> </el-table-column>
        <el-table-column width="350" prop="create_time" label="时间">
        </el-table-column>
      </el-table>
    </div>
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
      class="form-panel"
      title="新建日志"
      :visible.sync="newitem"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="400px"
    >
      <label class="input">姓名：</label>
      <el-input v-model="creatnew_name" placeholder="请输入姓名"></el-input>
      <label class="input">操作：</label>
      <el-input v-model="creatnew_name" placeholder="请输入操作内容"></el-input>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary">确定</el-button>
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
import { selectOptions } from "@/config/LogConfig";
import { getLogmessage } from "@/httpTools/globalUrl";
import { axiosFetch } from "@/httpTools/fetchData";
export default {
  data() {
    return {
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      showtimetodonotgood: false,
      //新建选项姓名，产线，工单号，班次
      creatnew_name: "",
      creatnew_linenumber: "",
      creatnew_positionnum: "",
      creatnew_frequencyselect: "",
      newitem: false,

      name: "",
      optionsposition: [
        { key: "1", label: "生产领班" },
        { key: "2", label: "跟线工程师" },
        { key: "3", label: "IPQC" },
        { key: "4", label: "不良数记录员" },
      ],
      optionsfrequency: [
        { key: "1", label: "白班" },
        { key: "2", label: "夜班" },
      ],
      optionsline: JSON.parse(JSON.stringify(selectOptions[0].list)),
      linenumber: "", //产线
      positionnum: "", //工单号

      tableData: [
        {
          num: "301",
          name: "张三",
          operation: "张三操作的具体内容",
          time: "2002-10-19 12:10:10",
        },
        {
          num: "302",
          name: "李四",
          operation: "李四操作的具体内容",
          time: "2002-10-19 12:10:10",
        },
        {
          num: "302",
          name: "王五",
          operation: "王五操作的具体内容",
          time: "2002-10-19 12:10:10",
        },
      ],
      /* 分页*/
      paginationOptions: {
        currentPage: 1,
        pageSize: 20,
        total: 0,
      },
      httpRequare: {},
      typename: "",
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
  mounted() {
    this.queryData();
  },
  methods: {
    selectSystemChanged(value) {},
    shownewitem() {
      this.newitem = true;
    },

    // 重置查询条件
    clearOptions() {
      this.httpRequare = {};
      this.queryData();
    },
    openDetailPanel(row) {
      this.typename = "人员管理修改";
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
      this.httpRequare.pageNo = this.paginationOptions.currentPage;
      this.httpRequare.pageSize = this.paginationOptions.pageSize;
      let options = {
        url: getLogmessage,
        data: this.httpRequare,
      };
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
    },
    // 查询
    queryData() {
      this.paginationOptions.currentPage = 1;
      this.fetchPage();
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
  .button-father {
    margin-left: 10px;
    height: 60px;
    padding-bottom: 7px;
    box-sizing: border-box;
    display: flex;
    align-items: flex-end;
  }
}
</style>


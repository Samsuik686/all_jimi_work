<template>
  <el-dialog
    title="修改状态"
    :close-on-click-modal="isCloseOnModal"
    :close-on-press-escape="isCloseOnModal"
    :visible.sync="editDialogVisible"
    width="400px"
  >
    <el-form label-width="50px" label-position="right">
      <el-form-item label="线号">
        <el-input
          v-model.trim="editData.lineName"
          size="large"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="工单">
        <el-input
          v-model.trim="editData.workOrder"
          size="large"
          :disabled="true"
        ></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select
          v-model.trim="editData.state"
          placeholder="状态"
          value=""
          style="width: 100%"
        >
          <el-option
            label="未开始"
            value="0"
            :disabled="loginUser.type !== ''"
          ></el-option>
          <el-option
            label="进行中"
            value="1"
            :disabled="loginUser.type === 6"
          ></el-option>
          <el-option
            label="已完成"
            value="2"
            :disabled="loginUser.type === 6"
          ></el-option>
          <el-option
            label="已作废"
            value="3"
            :disabled="loginUser.type === 4"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="editDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="edit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { mapGetters } from "vuex";
import Bus from "../../../../utils/bus";
import { programTip } from "./../../../../utils/formValidate";
import { axiosPost } from "./../../../../utils/fetchData";
import {
  programStartUrl,
  programFinishUrl,
  programCancelUrl,
  smtepsupdateState,
} from "./../../../../config/globalUrl";
import { errTip } from "./../../../../utils/errorTip";

export default {
  name: "programModal",
  data() {
    return {
      //点击 (modal || esc) 不退出
      isCloseOnModal: false,
      //修改模态框
      editDialogVisible: false,
      //修改信息
      editData: {
        id: "",
        lineName: "",
        workOrder: "",
        state: "",
        page: "",
      },
      //初始状态
      oldState: "",
    };
  },
  beforeDestroy() {
    //取消监听
    Bus.$off("editState");
  },
  computed: {
    ...mapGetters(["loginUser"]),
  },
  mounted() {
    //监听状态修改事件
    Bus.$on("editState", (row) => {
      console.log(row);
      if (row.boardType == 0) {
        this.editData.page = "C";
      } else if (row.boardType == 1) {
        this.editData.page = "AB";
      } else if (row.boardType == 2) {
        this.editData.page = "A";
      } else if (row.boardType == 3) {
        this.editData.page = "B";
      }
      this.editData.id = row.id;
      this.editData.lineName = row.lineName;
      this.editData.workOrder = row.workOrder;
      this.editData.state = row.state + "";
      this.oldState = row.state + "";
      this.editDialogVisible = true;
    });
  },
  methods: {
    fetchData: function (url) {
      let options = {
        url: url,
        data: {
          id: this.editData.id,
        },
      };
      axiosPost(options)
        .then((response) => {
          if (response.data) {
            let result = response.data.result;
            if (result === "succeed") {
              this.$alertSuccess("修改成功");
            } else {
              this.$alertWarning(errTip(result));
            }
            Bus.$emit("refreshProgram", true);
            this.editDialogVisible = false;
          }
        })
        .catch((err) => {
          this.$alertError("接口请求失败，请检查网络，再联系管理员");
        });
    },
    //编辑
    edit: function () {
      console.log(this.editData);
      let state = this.editData.state;
      let result = programTip(this.oldState, state);
      if (result !== "") {
        this.$alertWarning(result);
        return;
      }
      this.smtepsupdateState();
      switch (state) {
        case "1":
          this.setStart();
          break;
        case "2":
          this.setFinish();
          break;
        case "3":
          this.setCancel();
          break;
      }
     
    },
    smtepsupdateState: function () {
      let options = {
        url: smtepsupdateState,
        data: {
          line: this.editData.lineName,
          zhidan: this.editData.workOrder,
          Page: this.editData.page,
          state: this.editData.state,
        },
      };
      axiosPost(options)
        .then((response) => {
          if (response.data) {
            let result = response.data.code;
            if (code == "200") {
              this.$alertWarning(response.data.data);
            } else {
              this.$alertWarning(response.data.data);
            }
          }
        })
        .catch((err) => {
         
        });
    },
    //设置进行中
    setStart: function () {
      this.fetchData(programStartUrl);
    },
    //设置已完成
    setFinish: function () {
      this.fetchData(programFinishUrl);
    },
    //设置已作废
    setCancel: function () {
      this.fetchData(programCancelUrl);
    },
  },
};
</script>

<style scoped lang="scss">
</style>

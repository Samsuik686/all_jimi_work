<template>
  <div id="sys-log" ref="sys-log">
    <div class="query-comp">
      <div class="query-comp-btn">
        <label class="input">用户名：</label>
        <el-input
          v-model="httprequart.Name"
          placeholder="请输入内容"
        ></el-input>
      </div>
      <div class="query-comp-btn">
        <label class="input">是否启用：</label>
        <el-select
          v-model="httprequart.isUsed"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsline"
            :key="item.id"
            :label="item.state"
            :value="item.state"
          >
          </el-option>
        </el-select>
      </div>
      <div class="query-comp-btn">
        <label class="input">用户类型：</label>
        <el-select
          v-model="httprequart.userType"
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
      <div class="query-comp-btn-left">
      <el-button type="primary" icon="el-icon-search" @click="queryData"
        >确定</el-button
      >
      <el-button type="info" icon="el-icon-close" @click="clearOptions"
        >重置</el-button
      >
      <el-button type="primary" @click="shownewitem">新建人员</el-button>
    </div>
    </div>
    
    <div class="data-comp">
      <el-table :data="tableData" border height="100%" style="width: 100%">
        <el-table-column
          width="50px"
          type="index"
          label="序号"
          header-align="center"
          :index="indexMethod"
        >
        </el-table-column>
        <el-table-column prop="username" label="用户名"> </el-table-column>
        <el-table-column prop="Name" label="用户描述"> </el-table-column>
        <el-table-column prop="usertype" label="用户类型"> </el-table-column>
        <el-table-column prop="isused" label="是否启用"> </el-table-column>

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
                @click="delectDetailPanel(scope.row)"
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
      class="query-comp-dialog"
      :title="typename"
      :visible.sync="newitem"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="800px"
    >
      <div class="query-comp-container">
        <div class="query-comp-btn">
          <label class="query-comp-label">用户名：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="add_username"
            placeholder="请输入用户名"
          ></el-input>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">用户描述：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="add_Name"
            placeholder="请输入用户描述"
          ></el-input>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">密码：</label>
          <el-input
            class="query-comp-btn-kuang"
            v-model="add_password"
            placeholder="请设置次用户初始化密码"
          ></el-input>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">用户类型：</label>
          <el-select
            class="query-comp-btn-kuang"
            v-model="add_usertype"
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
          <label class="query-comp-label">是否启用：</label>
          <el-select
            class="query-comp-btn-kuang"
            v-model="add_isused"
            placeholder="请选择"
            @change="selectSystemChanged"
          >
            <el-option
              v-for="item in optionsline"
              :key="item.id"
              :label="item.state"
              :value="item.state"
            >
            </el-option>
          </el-select>
        </div>
        <div class="query-comp-btn">
          <label class="query-comp-label">上传照片：</label>
          <el-upload
            ref="orderUpload"
            class="orderUpload"
            action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
            :limit="2"
            :auto-upload="false"
            :before-upload="beforeAvatarUpload"
            :on-change="newhandleCheckFile"
          >
            <el-button slot="trigger" size="small" type="info"
              >选取文件</el-button
            >
          </el-upload>
        </div>
      </div>

      <span slot="footer" class="dialog-footer">
        <!-- <el-button size="small" @click="closeadd">取消</el-button> -->
        <el-button type="primary" size="small" @click="addpeople"
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
      top="10px"
    >
      <label class="input">用户名：</label>
      <el-input
        :disabled="true"
        v-model="change_username"
        placeholder="请输入用户名"
      ></el-input>
      <label class="input">用户描述：</label>
      <el-input v-model="change_Name" placeholder="请输入用户描述"></el-input>
      <label class="input">密码：</label>
      <el-input
        show-password
        v-model="change_password"
        placeholder="请修改此用户密码"
      ></el-input>
      <label class="input">上传照片：</label>

      <el-upload
        ref="orderUpload"
        class="orderUpload"
        action="https://www.mocky.io/v2/5185415ba171ea3a00704eed/posts/"
        :limit="2"
        :auto-upload="false"
        :before-upload="beforeAvatarUpload"
        :on-change="handleCheckFile"
      >
        <el-button slot="trigger" size="small" type="info">选取文件</el-button>
      </el-upload>

      <div class="query-comp-btn">
        <label class="input">用户类型：</label>
        <el-select
          v-model="change_usertype"
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
        <label class="input">是否启用：</label>
        <el-select
          v-model="change_isused"
          placeholder="请选择"
          @change="selectSystemChanged"
        >
          <el-option
            v-for="item in optionsline"
            :key="item.id"
            :label="item.state"
            :value="item.state"
          >
          </el-option>
        </el-select>
      </div>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button size="small" @click="closechangenamemethod">取消</el-button> -->
        <el-button size="small" type="primary" @click="changenamemethod"
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
import { axiosFetch } from "@/httpTools/fetchData";
import { post } from "@/utils/request";
import {
  getpeoplelest,
  add_people,
  change_people,
  delect_people,
} from "@/httpTools/globalUrl";
export default {
  inject: ["reload"],
  data() {
    return {
      timer: "", //定义一个定时器的变量
      currentTime: new Date(), // 获取当前时间
      showtimetodonotgood: false,
      showdelect: false,
      changename: "",
      newitem: false,
      changeitem: false,
      name: "",
      optionsposition: [
        { key: "1", label: "生产领班" },
        { key: "2", label: "跟线工程师" },
        { key: "3", label: "IPQC" },
        { key: "4", label: "不良数记录员" },
        { key: "5", label: "开发人员" },
        { key: "6", label: "管理员" },
      ],

      optionsline: [
        {
          state: "是",
          id: "0",
        },
        {
          state: "否",
          id: "1",
        },
      ],
      has_start: "", //产线
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
      httprequart: {},
      //新增人员管理
      imageUrl: "",
      add_username: "",
      add_Name: "",
      add_usertype: "",
      add_isused: "",
      add_password: "",
      add_file: {},

      //修改人员管理
      change_imageUrl: "",
      change_username: "",

      change_Name: "",
      change_usertype: "",
      change_isused: "",
      change_password: "",
      change_file: {},

      //
      delectname: "",
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
    suredelectitem() {
      let options = {
        url: delect_people,
        data: {
          userId: this.delectname,
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
    cannaldelect() {
      this.showdelect = false;
    },
    handleCheckFile(file, fileList) {
      this.change_file = file.raw;
    },

    newhandleCheckFile(file, fileList) {
      this.add_file = file.raw;
    },

    // handleAvatarSuccess(res, file,fileList) {
    //   console.log("fileList=="+fileList);
    //   console.log("res=="+JSON.stringify(res));
    //    console.log("file=="+file.raw);
    //   this.imageUrl = URL.createObjectURL(file.raw);
    //   this.add_file = file.raw;

    // },
    //  changehandleAvatarSuccess(res, file,fileList) {

    //   this.change_imageUrl = URL.createObjectURL(file.raw);
    //   this.change_file = file.raw;

    // },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg';
      // const isLt2M = file.size / 1024  < 500;

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!');
      // }
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 500k!');
      // }
      return true;
    },
    selectSystemChanged(value) {},
    // 重置查询条件
    clearOptions() {
      this.httprequart = {};
      this.queryData();
    },
    //更改名字
    changenamemethod() {
      if (
        this.change_username == "" ||
        this.change_Name == "" ||
        this.change_usertype == "" ||
        this.change_isused == "" ||
        this.change_password == "" ||
        this.change_username == null ||
        this.change_Name == null ||
        this.change_usertype == null ||
        this.change_isused == null ||
        this.change_password == null
      ) {
        this.$alertWarning("填写数据不能为空");
      } else {
        const uploadFileData = new FormData();
        if (this.change_file.name) {
          uploadFileData.append(
            "picFile",
            this.change_file,
            this.change_file.name
          );
        }
        uploadFileData.append("Type", sessionStorage.getItem("Type"));
        uploadFileData.append("userId", this.change_username);
        uploadFileData.append("Name", this.change_Name);
        uploadFileData.append("userType", this.change_usertype);
        uploadFileData.append("isUsed", this.change_isused);
        uploadFileData.append("password", this.change_password);
        uploadFileData.append("TOKEN", sessionStorage.getItem("token"));
        const config = {
          header: {
            "Content-Type": "multipart/form-data",
          },
        };
        post(change_people, uploadFileData, config)
          .then((res) => {
            // post(uploadFileData, config).then(res => {
            console.log(res);
            if (res.code == 200) {
              this.$alertSuccess("操作成功！");
              this.reload();
            } else {
              this.$alertWarning(res.data);
            }
          })
          .catch((err) => {
            this.$alertDanger(err);
          })
          .finally(() => {});
      }
    },
    handleSelect(item) {},
    delectDetailPanel(row) {
      console.log(row);
      this.delectname = row.username;
      this.showdelect = true;
      // num: '302',
      //     name: '孙小猛',
      //     frequency:'白班',
      //     station:"干饭人",
      //     number:'2'
    },
    openDetailPanel(row) {
      console.log("openDetailPanel");
      console.log(row);

      (this.change_imageUrl = ""),
        (this.change_username = ""),
        (this.change_Name = row.Name);
      (this.change_usertype = row.usertype),
        (this.change_isused = row.isused),
        (this.change_password = row.password),
        (this.change_file = {}),
        (this.changeitem = true);
      this.change_username = row.username;

      // num: '302',
      //     name: '孙小猛',
      //     frequency:'白班',
      //     station:"干饭人",
      //     number:'2'
    },
    closechangenamemethod() {
      this.changeitem = false;
    },
    shownewitem() {
      (this.imageUrl = ""),
        (this.add_username = ""),
        (this.add_Name = ""),
        (this.add_usertype = ""),
        (this.add_isused = ""),
        (this.add_password = ""),
        (this.add_file = {}),
        (this.typename = "新建人员管理");
      this.newitem = true;
    },
    closeadd() {
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
    addpeople() {
      if (
        this.add_username == "" ||
        this.add_Name == "" ||
        this.add_usertype == "" ||
        this.add_isused == "" ||
        this.add_password == "" ||
        this.add_username == null ||
        this.add_Name == null ||
        this.add_usertype == null ||
        this.add_isused == null ||
        this.add_password == null
      ) {
        this.$alertWarning("填写数据不能为空");
      } else {
        const uploadFileData = new FormData();

        if (this.add_file.name) {
          uploadFileData.append("picFile", this.add_file, this.add_file.name);
        }
        uploadFileData.append("Type", sessionStorage.getItem("Type"));
        uploadFileData.append("userId", this.add_username);
        uploadFileData.append("Name", this.add_Name);
        uploadFileData.append("userType", this.add_usertype);
        uploadFileData.append("isUsed", this.add_isused);
        uploadFileData.append("password", this.add_password);
        uploadFileData.append("TOKEN", sessionStorage.getItem("token"));
        const config = {
          header: {
            "Content-Type": "multipart/form-data",
          },
        };
        post(add_people, uploadFileData, config)
          .then((res) => {
            // post(uploadFileData, config).then(res => {
            console.log(res);
            if (res.code == 200) {
              console.log("操作成功！");
              this.$alertSuccess("操作成功！");
              this.reload();
            } else {
              console.log("!=200");
              this.$alertWarning(res.data);
            }
          })
          .catch((err) => {
            console.log(err);
          })
          .finally(() => {});
      }
    },
    // 获取Table数据
    fetchPage() {
      this.httprequart.pageNo = this.paginationOptions.currentPage;
      this.httprequart.pageSize = this.paginationOptions.pageSize;
      let options = {
        url: getpeoplelest,
        data: this.httprequart,
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
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
    width: 80px;
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


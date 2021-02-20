<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">SMT看板系统</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="Username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="Password"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>

      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
        >登录</el-button
      >

      <!-- <div class="tips">
        <span style="margin-right:20px;">username: admin</span>
        <span> password: any</span>
      </div> -->
    </el-form>
  </div>
</template>

<script>
import { validUsername } from "@/utils/validate";

import { loginUrl } from "@/httpTools/globalUrl";
import { axiosFetch } from "@/httpTools/fetchData";
import { mapActions } from "vuex";
export default {
  name: "Login",
  data() {
    const validateUsername = (rule, value, callback) => {
      callback();
    };
    const validatePassword = (rule, value, callback) => {
      callback();
    };
    return {
      loginForm: {
        username: "",
        password: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      token: "",
      loading: false,
      passwordType: "password",
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  mounted: function () {
    this.token = sessionStorage.getItem("token")
      ? sessionStorage.getItem("token")
      : "";
  },
  methods: {
    ...mapActions(["setLoginToken"]),
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      if (!this.loading) {
        this.loading = true;
        let options = {
          url: loginUrl,
          data: {
            user: this.loginForm.username,
            password: this.loginForm.password,
          },
        };
        axiosFetch(options)
          .then((res) => {
            console.log(res);
            if (res.data.code === 200) {
              // if(res.data.data['roleVO']['name'] === '超级管理员' && res.data.data['roleVO']['company'] === 28){   //测试环境 超级管理员 的company会变成28
              //   sessionStorage.setItem('factory', 1);//人员的所属工厂
              // }else{
              //   sessionStorage.setItem('factory', res.data.data['roleVO']['company']);//人员的所属工厂
              // // }
              // sessionStorage.setItem('UserRole',JSON.stringify({'roleName':res.data.data['roleVO'].name,'roleNo':res.data.data['roleVO'].no,"department":res.data.data['roleVO'].department}));
              sessionStorage.setItem("token", res.data.data.TOKEN);
              sessionStorage.setItem("Name", res.data.data.Name);
              sessionStorage.setItem("Type", res.data.data.Type);
              // this.setLoginToken(sessionStorage.getItem('token'));
              this.$store.dispatch("user/setLoginToken", res.data.data.TOKEN);

              // sessionStorage.setItem('UserName', res.data.data['name']);

              // /*生成权限列表*/
              // let map = new getCharactersFuncList();
              // let funcList = initCharactersFuncMap(map, res.data.data['webAuthorities']);
              // this.setCharactersFuncMap(funcList);
              // sessionStorage.setItem('charactersFuncMap', JSON.stringify(funcList));

              // this.$router.push({ path: this.redirect || '/' })
              this.$router.replace("/");
              this.loading = false;
            } else {
              this.loading = false;
              this.$alertWarning(res.data.data);
            }
          })
          .catch((err) => {
            this.loading = false;
             this.$alertDanger(err);
          })
          .finally(() => {
            this.loading = false;
          });
      }
    },
  },
};
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>

import axios from 'axios';
import store from "../store";
import index from '../router';
// axios.defaults.timeout = 5000;
// axios.defaults.baseURL = window.g.API_URL + '/mes_server/';

axios.interceptors.request.use(
  config => {
    //console.log(store.state.token)
    if (!config.header) {

      if (store.state.token !== '') {
        if (config.data === "") {
          config.data += ("TOKEN=" + store.getters.token);
        } else {
          config.data += ("&TOKEN=" + store.getters.token);
        }
        config.data += ("&Type=" + sessionStorage.getItem("Type"));
        //config.withCredentials = true;
        //console.log(config)
      }

    }
    return config;
  },
  error => {
    return Promise.reject(error)
  }
);


axios.interceptors.response.use(
  res => {
   
    if (res.data.code === 211) {

      store.commit('SET_TOKEN', '');
      sessionStorage.removeItem('token');
      index.replace({
        path: '/login',
        query: { redirect: index.currentRoute.fullPath }
      });

    }
    return res
  },
  error => {
    if (error.response) {
      console.log(JSON.stringify(error))
    }
    return Promise.reject(JSON.stringify(error))
  }
);

export default axios;

// submitOrderUpload: function () {
//   this.uploadFileData = new FormData();
//   this.$refs.orderUpload.submit();
//   this.uploadFileData.append('isRework', this.isReworkUpload);
//   this.uploadFileData.append('#TOKEN#', this.$store.state.token);
//   // this.uploadFileData.append('factory', 2);
//   let config = {
//     header: {
//       'Content-Type': 'multipart/form-data'
//     },
//   };
//   this.$axios.post(planOrderImportUrl, this.uploadFileData, config).then(response => {
//     if (response.data.result === 200) {
//       this.$alertSuccess(response.data.data);
//       this.partlyReload();
//       //this.reload();
//     } else if(response.data.data === '参数不能为空'){
//       this.$alertWarning('请先选取文件');
//     }else {
//       this.$alertWarning(response.data.data);
//     }

//   }).catch(err => {
//   }).finally(() => {
//     this.isPending = false;
//     this.$closeLoading();
//   });

// },
 // 获取测试项列表
//  fetchTestItem() {
//   axiosFetch({
//     url: testItemListUrl,
//   }).then(res => {
//     if (res.data.result === 200) {
//       this.testItemList = res.data.data
//       console.log()
//     } else {
//       this.$alertWarning(res.data.data)
//     }
//   }).finally(() => {
//     this.$closeLoading()
//   })
// },
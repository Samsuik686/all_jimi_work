import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  baseURL: process.env.NODE_ENV === 'production' ? window.g.API_URL : window.g.LOCAL_URL, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 1800000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      // let each request carry token
      config.headers.Authorization = 'Bearer ' + getToken()
      config.headers['Content-Type'] = 'application/json;charset=UTF-8'
      // ['X-Token'] is a custom headers key
      // config.headers['X-Token'] = getToken()
    }
    if (config.method === 'get') {
      // config.data = JSON.stringify(config.data)
      // config.paramsSerializer = function(params) {
      //   return qs.stringify(params, { arrayFormat: 'indices' })
      // }
      config.data = true
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response
    console.log(res)
    // if the custom code is not 200, it is juded as an error.
    if (res.data.code !== 200) {
      Message({
        // message: res.data.message || 'Error',
        message: res.data.data,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.data.message || 'Error'))
      // return res
      // return Promise.reject(new Error(JSON.stringify(res.data) || 'Error'))
    } else {
      console.log(res);
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)
export default service

export const axiosGet = (opt) => {
  return axios({
    method: 'get',
    url: opt.url,
    timeout: 90000,
    // withCredentials: true,
    headers: {
      'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    params: opt.data
  })
}

export const axiosPost = (opt) => {
  return axios({
    method: 'post',
    url: opt.url,
    timeout: 90000,
    // withCredentials: true,
    headers: {
      'Content-type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    data: opt.data
  })
}

export const axiosDownload = (opt) => {
  return axios({
    method: 'get',
    url: opt.url,
    timeout: 0,
    responseType: 'blob',
    params: opt.data
  })
}

/**
 * 获取返回值
 * 根据请求配置参数(responseHeader), 是否在响应结果中展示header等其他返回信息
 * @author hp
 * @version tuqiang 0.0.1
 * @param {Object} config 请求配置
 * @param {Object} res 响应结果
 */
function getResponse(config, res) {
  // 必须设置{responseHeader: true}
  return config.responseHeaders === true ? res : res.data
}

/**
 * get方法
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Object} config 请求的配置
 */
export function get(url, params, config = {}) {
  // let time = new Date().getTime();
  return new Promise((resolve, reject) => {
    service.get(`${url}`, {
      params,
      ...config
    }).then(res => {
      resolve(getResponse(config, res))
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * post方法
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Object} config 请求的配置
 */
export function post(url, params, config = {}) {
  return new Promise((resolve, reject) => {
    service.post(url, params, config).then(res => {
      resolve(getResponse(config, res))
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * put方法
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Object} config 请求的配置
 */
export function put(url, params, config = {}) {
  return new Promise((resolve, reject) => {
    service.put(url, params, config).then(res => {
      resolve(getResponse(config, res))
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * patch方法
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Object} config 请求的配置
 */
export function patch(url, params, config = {}) {
  return new Promise((resolve, reject) => {
    service.patch(url, params, config).then(res => {
      resolve(getResponse(config, res))
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 * delete方法
 * @param {String} url 请求地址
 * @param {Object} params 请求参数
 * @param {Object} config 请求的配置
 */
export function del(url, params, config = {}) {
  return new Promise((resolve, reject) => {
    service.delete(url, {
      params,
      ...config
    }).then(res => {
      resolve(getResponse(config, res))
    }).catch(err => {
      reject(err)
    })
  })
}

import { login } from '@/api/system'
import { getToken, setToken, removeToken, setName, getName, removeName, setUserId, getUserId, removeUserId, removeSidebarStatus } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: getName(),
    userId: getUserId()
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_USERID: (state, id) => {
    state.userId = id
  }
}

const actions = {
  setLoginToken({commit}, token) {
    commit('SET_TOKEN',token)
  },
  // user login
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login.login(userInfo).then(response => {
        if (response.code === 200) {
          // 登录返回的data就是token
          commit('SET_TOKEN', response.data) // 第一次获取不到 getToken 只能先直接存
          setName(userInfo.get('account'))
          setToken(response.data)
          resolve()
        } else {
          reject()
          console.log(response)
        }
      }).catch(error => {
        reject(error)
      })
    })
  },
  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      login.userInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('验证失败，请重新登录')
        }

        const { name, id } = data
        commit('SET_NAME', name)
        commit('SET_USERID', id)
        setUserId(id)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      removeToken() // must remove  token  first
      // removeName()
      // resetRouter()
      // removeUserId()
      // removeSidebarStatus()
      // commit('RESET_STATE')
      resolve()
      reject()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      removeName()
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}


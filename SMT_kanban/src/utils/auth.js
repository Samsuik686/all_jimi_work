import Cookies from 'js-cookie'

const TokenKey = 'token'
const Name = 'name'
const UserID = 'userId'
const Typename = 'Type'

export function getToken() {
  return sessionStorage.getItem(TokenKey)
}

export function setToken(token) {
  return sessionStorage.setItem(TokenKey, token)
}

export function getType() {
  return sessionStorage.getItem(TokenKey)
}

export function setType(Type) {
  return sessionStorage.setItem(Typename, Type)
}

export function removeToken() {
  return sessionStorage.removeItem(TokenKey)
}

export function getName() {
  return sessionStorage.getItem(Name)
}

export function setName(name) {
  return sessionStorage.setItem(Name, name)
}

export function removeName() {
  return sessionStorage.removeItem(Name)
}

export function getUserId() {
  return sessionStorage.getItem(UserID)
}

export function setUserId(userId) {
  return sessionStorage.setItem(UserID, userId)
}

export function removeUserId() {
  return sessionStorage.removeItem(UserID)
}
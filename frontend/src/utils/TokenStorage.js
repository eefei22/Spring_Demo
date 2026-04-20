const STORAGE = {
  tokenKey: 'spring_demo_auth_token',
  userIdKey: 'spring_demo_user_id',
}

export function setToken(token) {
  if (typeof token !== 'string' || token.trim() === '') return
  window.sessionStorage.setItem(STORAGE.tokenKey, token)
}

export function setUserId(userId) {
  if (userId === null || userId === undefined) return
  const value = String(userId).trim()
  if (!value) return
  window.sessionStorage.setItem(STORAGE.userIdKey, value)
}

export function getToken() {
  return window.sessionStorage.getItem(STORAGE.tokenKey)
}

export function getUserId() {
  const raw = window.sessionStorage.getItem(STORAGE.userIdKey)
  if (!raw) return null
  const n = Number.parseInt(raw, 10)
  return Number.isFinite(n) ? n : null
}

export function clearToken() {
  window.sessionStorage.removeItem(STORAGE.tokenKey)
  window.sessionStorage.removeItem(STORAGE.userIdKey)
}

export default {
  setToken,
  setUserId,
  getToken,
  getUserId,
  clearToken,
}

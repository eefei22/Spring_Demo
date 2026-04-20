const AUTH = {
  headerName: 'Authorization',
  scheme: 'Bearer',
}

const JSON = {
  accept: 'application/json',
  contentType: 'application/json',
}

function mergeHeaders(base, extra) {
  return { ...(base ?? {}), ...(extra ?? {}) }
}

export function jsonHeaders(extraHeaders) {
  return mergeHeaders(
    {
      Accept: JSON.accept,
      'Content-Type': JSON.contentType,
    },
    extraHeaders,
  )
}

export function bearerAuthHeaders(token, extraHeaders) {
  const t = typeof token === 'string' ? token.trim() : ''
  const auth = t ? { [AUTH.headerName]: `${AUTH.scheme} ${t}` } : {}
  return mergeHeaders(auth, extraHeaders)
}

export function authJsonHeaders(token, extraHeaders) {
  return mergeHeaders(jsonHeaders(), bearerAuthHeaders(token, extraHeaders))
}

export default {
  jsonHeaders,
  bearerAuthHeaders,
  authJsonHeaders,
}


const BASE_URL = 'http://localhost:8080'
const LOGIN_PATH = '/auth/login'
const LOGIN_URL = `${BASE_URL}${LOGIN_PATH}`

const HTTP = {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    Accept: 'application/json',
  },
}

export async function login({ username, password }) {
  const request = { username, password }
  let response
  
  try {
    response = await fetch(LOGIN_URL, {
      ...HTTP,
      body: JSON.stringify(request),
    })
  } catch {
    throw new Error('Unable to reach server. Is the backend running on :8080?')
  }

  const body = await readJsonSafe(response)

  if (!response.ok) {
    throw new Error(toErrorMessage(response, body))
  }

  return body ?? { message: 'Login successful' }
}

async function readJsonSafe(response) {
  try {
    return await response.json()
  } catch {
    return null
  }
}

function toErrorMessage(response, body) {
  if (body && typeof body === 'object' && typeof body.message === 'string') {
    return body.message
  }
  if (response.status) {
    return `${response.status} ${response.statusText || 'Request failed'}`
  }
  return 'Request failed'
}

/**
 * Calls Spring backend login endpoint.
 * Expected success shape:
 *   { userId, username, message, token }
 * Expected error shape:
 *   { timestamp, status, error, message, path }
 */


export default {
  login,
}

import TokenStorage from '../utils/TokenStorage.js'
import HttpHeaders from '../utils/HttpHeaders.js'


const BASE_URL = 'http://localhost:8080'
const JOURNALS_BY_USER_URL_TEMPLATE = `${BASE_URL}/users/{user_id}/journals`
const JOURNAL_BY_ID_URL_TEMPLATE =
  `${BASE_URL}/users/{user_id}/journals/{journal_id}`
/**
 *  endpoint templates:
 * http://localhost:8080/users/{user_id}/journals
 * http://localhost:8080/users/{user_id}/journals/{journal_id}
**/

const AUTH = {
  headerName: 'Authorization',
  scheme: 'Bearer',
}

const HTTP = {
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
  methods: {
    get: 'GET',
    post: 'POST',
    put: 'PUT',
    delete: 'DELETE',
  },
}


export async function getAllJournalsForUser({ userId } = {}) {
  const token = TokenStorage.getToken()
  if (!token) {
    const err = new Error('Please login first.')
    err.status = 401
    throw err
  }

  const resolvedUserId =
      userId !== null && userId !== undefined ? userId : TokenStorage.getUserId()
  if (!resolvedUserId) {
    const err = new Error('Please login first.')
    err.status = 401
    throw err
  }

  const request = buildJournalsByUserUrl(resolvedUserId)

  let response
  try {
    response = await fetch(request, {
      method: HTTP.methods.get,
      headers: HttpHeaders.authJsonHeaders(token),
    })
  } catch {
    throw new Error('Unable to reach server. Is the backend running on :8080?')
  }

  const body = await readJsonSafe(response)

  if (!response.ok) {
    const err = new Error(toErrorMessage(response, body))
    err.status = response.status
    throw err
  }

  return body ?? []
}


function buildJournalsByUserUrl(userId) {
  return JOURNALS_BY_USER_URL_TEMPLATE.replace('{user_id}', String(userId))
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
 * Phase 5 milestone: fetch all journals for a user.
 * Protected endpoint:
 *   GET /users/{user_id}/journals
 */


export {
  BASE_URL,
  JOURNALS_BY_USER_URL_TEMPLATE,
  JOURNAL_BY_ID_URL_TEMPLATE,
  AUTH,
  HTTP,
}

export default {
  BASE_URL,
  JOURNALS_BY_USER_URL_TEMPLATE,
  JOURNAL_BY_ID_URL_TEMPLATE,
  AUTH,
  HTTP,
  getAllJournalsForUser,
}

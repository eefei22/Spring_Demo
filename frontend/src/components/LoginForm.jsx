import { useId, useState } from 'react'
import AuthService from '../services/AuthService.js'
import TokenStorage from '../utils/TokenStorage.js'

const UI = {
  loginText: 'Login',
  loadingText: 'Logging in...',
  requiredUsername: 'Username must not be blank',
  requiredPassword: 'Password must not be blank',
  tokenStoredSuffix: 'Token stored in sessionStorage.',
}

export default function LoginForm({ onLoginSuccess }) {
  const messageId = useId()
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    if (isLoading) return

    const u = username.trim()
    const p = password.trim()

    setError('')
    setSuccess('')

    if (!u) {
      setError(UI.requiredUsername)
      return
    }
    if (!p) {
      setError(UI.requiredPassword)
      return
    }

    setIsLoading(true)
    try {
      const data = await AuthService.login({ username: u, password: p })

      if (typeof data?.token === 'string' && data.token.trim() !== '') {
        TokenStorage.setToken(data.token)
      }
      if (data?.userId !== null && data?.userId !== undefined) {
        TokenStorage.setUserId(data.userId)
      }

      const baseMessage =
        typeof data?.message === 'string' ? data.message : 'Login successful'
      const storedMsg =
        typeof data?.token === 'string' && data.token.trim() !== '' ? ` ${UI.tokenStoredSuffix}` : ''
      setSuccess(`${baseMessage}${storedMsg}`)

      if (typeof onLoginSuccess === 'function') {
        onLoginSuccess(data)
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Something went wrong.')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <form className="login-form" onSubmit={handleSubmit} aria-label="Login form">
      <div className="login-field">
        <label className="login-label" htmlFor="username">
          Username
        </label>
        <input
          className="login-input"
          id="username"
          name="username"
          type="text"
          autoComplete="username"
          placeholder="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          disabled={isLoading}
          aria-invalid={Boolean(error) && !username.trim()}
          aria-describedby={messageId}
        />
      </div>

      <div className="login-field">
        <label className="login-label" htmlFor="password">
          Password
        </label>
        <input
          className="login-input"
          id="password"
          name="password"
          type="password"
          autoComplete="current-password"
          placeholder="***************"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          disabled={isLoading}
          aria-invalid={Boolean(error) && !password.trim()}
          aria-describedby={messageId}
        />
      </div>

      <div
        id={messageId}
        className="login-messages"
        aria-live="polite"
        aria-atomic="true"
      >
        {error ? (
          <p className="login-message login-message--error">{error}</p>
        ) : null}
        {success ? (
          <p className="login-message login-message--success">{success}</p>
        ) : null}
      </div>

      <button className="login-button" type="submit" disabled={isLoading}>
        {isLoading ? UI.loadingText : UI.loginText}
      </button>
    </form>
  )
}

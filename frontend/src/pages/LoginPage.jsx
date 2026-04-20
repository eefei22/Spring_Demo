import LoginForm from '../components/LoginForm.jsx'
import '../styles/login.css'

export default function LoginPage({ onLoginSuccess }) {
  return (
    <main className="login-page">
      <section className="login-shell" aria-label="Login">
        <header className="login-header">
          <p className="login-eyebrow">Spring Demo</p>
          <h1 className="login-title">Sign in</h1>
          <p className="login-subtitle">
            Use your username and password to continue.
          </p>
        </header>

        <div className="login-card">
          <LoginForm onLoginSuccess={onLoginSuccess} />
        </div>

        <footer className="login-footer">
          <p className="login-footnote">
            Backend: <code>http://localhost:8080</code>
          </p>
        </footer>
      </section>
    </main>
  )
}

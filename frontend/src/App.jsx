import { useState } from 'react'
import LoginPage from './pages/LoginPage.jsx'
import JournalsPage from './pages/JournalsPage.jsx'
import TokenStorage from './utils/TokenStorage.js'

const VIEW = {
  login: 'login',
  journals: 'journals',
}

export default function App() {
  const [view, setView] = useState(() =>
    TokenStorage.getToken() ? VIEW.journals : VIEW.login,
  )

  function handleLoginSuccess() {
    setView(VIEW.journals)
  }

  function handleLogout() {
    TokenStorage.clearToken()
    setView(VIEW.login)
  }

  if (view === VIEW.journals) {
    return <JournalsPage onLogout={handleLogout} />
  }

  return <LoginPage onLoginSuccess={handleLoginSuccess} />
}


/**
  ===    strict equality
  !==    strict not equal
  ? :    inline if/else
  ?.     safe property access
  ${}    insert variable into string
  ??     fallback if null/undefined
 
 **/

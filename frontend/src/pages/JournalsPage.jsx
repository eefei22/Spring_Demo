import { useEffect, useState } from 'react'
import JournalService from '../services/JournalService.js'
import TokenStorage from '../utils/TokenStorage.js'
import JournalCard from '../components/JournalCard.jsx'
import '../styles/journals.css'

const UI = {
  title: 'Your journals',
  loading: 'Loading journals...',
  missingToken: 'Please login first.',
  unauthorized: 'Session expired. Please login again.',
  retry: 'Retry',
  Logout: 'Logout',
}

export default function JournalsPage({ onLogout }) {
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState('')
  const [journals, setJournals] = useState([])
  const [refreshKey, setRefreshKey] = useState(0)

  useEffect(() => {
    let cancelled = false

    async function run() {
      const token = TokenStorage.getToken()
      const userId = TokenStorage.getUserId()
      if (!token || !userId) {
        if (!cancelled) {
          setError(UI.missingToken)
          setJournals([])
        }
        return
      }

      if (!cancelled) {
        setIsLoading(true)
        setError('')
      }

      try {
        const data = await JournalService.getAllJournalsForUser({ userId })
        if (!cancelled) {
          setJournals(Array.isArray(data) ? data : [])
        }
      } catch (err) {
        const status = err && typeof err === 'object' ? err.status : undefined
        if (!cancelled) {
          if (status === 401) {
            setError(UI.unauthorized)
          } else {
            setError(err instanceof Error ? err.message : 'Something went wrong.')
          }
          setJournals([])
        }
      } finally {
        if (!cancelled) {
          setIsLoading(false)
        }
      }
    }

    void run()
    return () => {
      cancelled = true
    }
  }, [refreshKey])

  return (
    <main className="journals-page">
      <div className="journals-layout">
        {/* Future left navigation rail (20%). Kept in DOM to stabilize layout when implemented. */}
        <aside className="journals-rail journals-rail--left" aria-hidden="true" />

        <section className="journals-shell" aria-label="Journals">
          <header className="journals-header">
            <h1 className="journals-title">{UI.title}</h1>
            <div className="journals-actions">
              <button
                className="journals-action"
                type="button"
                onClick={() => setRefreshKey((k) => k + 1)}
                disabled={isLoading}
              >
                {UI.retry}
              </button>
              <button
                className="journals-action journals-action--secondary"
                type="button"
                onClick={onLogout}
              >
                {UI.Logout}
              </button>
            </div>
          </header>

          {isLoading ? <p className="journals-status">{UI.loading}</p> : null}

          {error ? (
            <p className="journals-status journals-status--error">{error}</p>
          ) : null}

          {!isLoading && !error ? (
            <div className="journals-grid">
              {journals.length ? (
                journals.map((j) => (
                  <JournalCard key={j?.id ?? JSON.stringify(j)} journal={j} />
                ))
              ) : (
                <p className="journals-status">No journals found.</p>
              )}
            </div>
          ) : null}
        </section>

        {/* Future right rail (30%). */}
        <aside className="journals-rail journals-rail--right" aria-hidden="true" />
      </div>
    </main>
  )
}

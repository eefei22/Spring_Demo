export default function JournalCard({ journal }) {
  const title = typeof journal?.title === 'string' ? journal.title : '(Untitled)'
  const content = typeof journal?.content === 'string' ? journal.content : ''
  const createdAt =
    typeof journal?.createdAt === 'string' ? journal.createdAt : null

  return (
    <article className="journal-card">
      <header className="journal-card__header">
        <h3 className="journal-card__title">{title}</h3>
        {createdAt ? (
          <p className="journal-card__meta">
            <span className="journal-card__metaLabel">Created:</span>{' '}
            <span className="journal-card__metaValue">{createdAt}</span>
          </p>
        ) : null}
      </header>
      <p className="journal-card__content">{content}</p>
    </article>
  )
}


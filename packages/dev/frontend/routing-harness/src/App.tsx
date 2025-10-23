import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { DiagramViewer } from './components/DiagramViewer';
import { loadFixture, loadManifest } from './lib/fixtureLoader';
import type { DiagramFixture, FixtureManifestEntry, RoutingMetrics } from './types';
import './styles/app.css';

const getInitialFixtureId = (manifest: FixtureManifestEntry[]): string | null => {
  const searchParams = new URLSearchParams(window.location.search);
  const requestedId = searchParams.get('fixture');
  if (requestedId && manifest.some((entry) => entry.id === requestedId)) {
    return requestedId;
  }
  return manifest.length > 0 ? manifest[0].id : null;
};

export default function App() {
  const [manifest, setManifest] = useState<FixtureManifestEntry[]>([]);
  const [activeFixture, setActiveFixture] = useState<DiagramFixture | null>(null);
  const [activeFixtureId, setActiveFixtureId] = useState<string | null>(null);
  const [metrics, setMetrics] = useState<RoutingMetrics | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const requestRef = useRef(0);

  useEffect(() => {
    let mounted = true;
    loadManifest()
      .then((entries) => {
        if (mounted) {
          setManifest(entries);
        }
      })
      .catch((err) => {
        if (mounted) {
          setError(err instanceof Error ? err.message : String(err));
        }
      });
    return () => {
      mounted = false;
    };
  }, []);

  const selectFixture = useCallback(async (fixtureId: string) => {
    setIsLoading(true);
    setError(null);
    setMetrics(null);
    const requestId = ++requestRef.current;
    try {
      const fixture = await loadFixture(fixtureId);
      if (requestRef.current !== requestId) {
        return;
      }
      setActiveFixture(fixture);
      setActiveFixtureId(fixtureId);
      const url = new URL(window.location.href);
      url.searchParams.set('fixture', fixtureId);
      window.history.replaceState({}, '', url);
    } catch (err) {
      if (requestRef.current === requestId) {
        setError(err instanceof Error ? err.message : String(err));
        setActiveFixture(null);
        setActiveFixtureId(null);
      }
    } finally {
      if (requestRef.current === requestId) {
        setIsLoading(false);
      }
    }
  }, []);

  useEffect(() => {
    if (manifest.length === 0) {
      return;
    }
    const initialId = getInitialFixtureId(manifest);
    if (initialId) {
      selectFixture(initialId);
    } else {
      setIsLoading(false);
    }
  }, [manifest, selectFixture]);

  const handleFixtureClick = useCallback(
    (fixtureId: string) => {
      if (fixtureId !== activeFixtureId) {
        selectFixture(fixtureId);
      }
    },
    [activeFixtureId, selectFixture]
  );

  const handleMetricsChange = useCallback((nextMetrics: RoutingMetrics) => {
    setMetrics(nextMetrics);
  }, []);

  const sidebarEntries = useMemo(
    () =>
      manifest.map((entry) => ({
        ...entry,
        isActive: entry.id === activeFixtureId,
      })),
    [manifest, activeFixtureId]
  );

  return (
    <div className="app">
      <aside className="app__sidebar">
        <h1 className="app__title">Routing Harness</h1>
        <div className="app__fixture-list">
          {sidebarEntries.map((entry) => (
            <button
              key={entry.id}
              className={`app__fixture-button${entry.isActive ? ' app__fixture-button--active' : ''}`}
              onClick={() => handleFixtureClick(entry.id)}>
              <div>{entry.name}</div>
              {entry.categories?.length ? (
                <small>{entry.categories.join(' • ')}</small>
              ) : (
                <small>{entry.id}</small>
              )}
            </button>
          ))}
        </div>
      </aside>
      <main className="app__main">
        {error ? <div className="app__status app__status--error">{error}</div> : null}
        {!error && isLoading ? <div className="app__status">Loading fixtures…</div> : null}
        {activeFixture ? (
          <>
            {activeFixture.description ? (
              <p className="app__description">{activeFixture.description}</p>
            ) : (
              <p className="app__description">{activeFixture.name}</p>
            )}
            <section className="app__viewer">
              <DiagramViewer fixture={activeFixture} onMetricsChange={handleMetricsChange} />
            </section>
            {metrics ? (
              <section className="app__metrics-panel">
                <article className="app__metric">
                  <h2 className="app__metric-title">Edges</h2>
                  <div className="app__metric-value">{metrics.totalEdges}</div>
                </article>
                <article className="app__metric">
                  <h2 className="app__metric-title">Total Bend Points</h2>
                  <div className="app__metric-value">{metrics.totalAutoBendPoints.toFixed(0)}</div>
                </article>
                <article className="app__metric">
                  <h2 className="app__metric-title">Average Bend Points</h2>
                  <div className="app__metric-value">{metrics.averageBendPoints.toFixed(2)}</div>
                </article>
                <article className="app__metric">
                  <h2 className="app__metric-title">Min / Max Bend Points</h2>
                  <div className="app__metric-value">
                    {metrics.minBendPoints} – {metrics.maxBendPoints}
                  </div>
                </article>
              </section>
            ) : null}
          </>
        ) : null}
      </main>
    </div>
  );
}

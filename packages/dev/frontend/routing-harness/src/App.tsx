import { useCallback, useEffect, useState } from 'react';
import { DiagramViewer } from './components/DiagramViewer';
import { loadFixture, loadManifest } from './lib/fixtureLoader';
import type { DiagramFixture, FixtureManifestEntry } from './types';
import './styles/app.css';
import './styles/edge-overlay.css';

interface LoadedFixture {
  entry: FixtureManifestEntry;
  fixture: DiagramFixture;
}

export default function App() {
  const [fixtures, setFixtures] = useState<LoadedFixture[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showDebug] = useState<boolean>(() => {
    if (typeof window === 'undefined') {
      return false;
    }
    const param = new URL(window.location.href).searchParams.get('debug');
    return param === 'spacing' || param === 'routing';
  });

  useEffect(() => {
    let mounted = true;

    const fetchFixtures = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const entries = await loadManifest();
        if (!mounted) {
          return;
        }

        if (entries.length === 0) {
          setFixtures([]);
          return;
        }

        const loadedFixtures = await Promise.all(
          entries.map(async (entry) => {
            const fixture = await loadFixture(entry.id);
            return { entry, fixture };
          })
        );

        if (!mounted) {
          return;
        }

        setFixtures(loadedFixtures);
      } catch (err) {
        if (mounted) {
          setError(err instanceof Error ? err.message : String(err));
          setFixtures([]);
        }
      } finally {
        if (mounted) {
          setIsLoading(false);
        }
      }
    };

    fetchFixtures();

    return () => {
      mounted = false;
    };
  }, []);

  useEffect(() => {
    const url = new URL(window.location.href);
    if (url.searchParams.has('fixture')) {
      url.searchParams.delete('fixture');
      window.history.replaceState({}, '', url);
    }
  }, []);

  const handleMetricsChange = useCallback(() => {}, []);

  return (
    <div className="app">
      {error ? <div className="app__status app__status--error">{error}</div> : null}
      {!error && isLoading ? <div className="app__status">Loading fixtures…</div> : null}
      {!isLoading && !error && fixtures.length === 0 ? (
        <div className="app__status">No fixtures available.</div>
      ) : null}
      {fixtures.map(({ entry, fixture }) => {
        const title = fixture.name ?? entry.name ?? entry.id;
        const description = fixture.description ?? entry.description;
        const key = fixture.id ?? entry.id;

        return (
          <section key={key} className="fixture">
            <header className="fixture__header">
              <h2 className="fixture__title">{title}</h2>
              {description ? <p className="fixture__description">{description}</p> : null}
            </header>
            <div className="fixture__viewer">
              <DiagramViewer fixture={fixture} onMetricsChange={handleMetricsChange} showDebug={showDebug} />
            </div>
          </section>
        );
      })}
    </div>
  );
}

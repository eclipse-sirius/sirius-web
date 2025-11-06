import { useCallback, useEffect, useRef, useState } from 'react';
import { DiagramViewer, type DiagramViewerHandle } from './components/DiagramViewer';
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
  const [isExporting, setIsExporting] = useState<boolean>(false);
  const [exportFeedback, setExportFeedback] = useState<{ type: 'success' | 'error'; message: string } | null>(null);
  const viewerHandlesRef = useRef<Map<string, DiagramViewerHandle>>(new Map());
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

  const registerViewerHandle = useCallback((fixtureId: string, handle: DiagramViewerHandle | null) => {
    const handles = viewerHandlesRef.current;
    if (handle) {
      handles.set(fixtureId, handle);
    } else {
      handles.delete(fixtureId);
    }
  }, []);

  const downloadImage = useCallback((dataUrl: string, fileName: string) => {
    const anchor = document.createElement('a');
    anchor.href = dataUrl;
    anchor.download = fileName;
    anchor.rel = 'noopener';
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);
  }, []);

  const handleExportAll = useCallback(async () => {
    if (isExporting || fixtures.length === 0 || error) {
      return;
    }

    setIsExporting(true);
    setExportFeedback(null);

    try {
      let exportedCount = 0;

      for (const { entry } of fixtures) {
        const handle = viewerHandlesRef.current.get(entry.id);
        if (!handle) {
          throw new Error(`Fixture "${entry.id}" is not ready for export.`);
        }

        const dataUrl = await handle.exportAsPng();
        const baseName = entry.fileName.replace(/\.json$/i, '');
        downloadImage(dataUrl, `${baseName}.png`);
        exportedCount += 1;
      }

      setExportFeedback({
        type: 'success',
        message: `Exported ${exportedCount} diagram${exportedCount === 1 ? '' : 's'}.`,
      });
    } catch (err) {
      setExportFeedback({
        type: 'error',
        message: err instanceof Error ? err.message : String(err),
      });
    } finally {
      setIsExporting(false);
    }
  }, [downloadImage, error, fixtures, isExporting]);

  useEffect(() => {
    if (!exportFeedback) {
      return;
    }
    const timeout = window.setTimeout(() => setExportFeedback(null), 3500);
    return () => window.clearTimeout(timeout);
  }, [exportFeedback]);

  const handleMetricsChange = useCallback(() => {}, []);

  return (
    <div className="app">
      <div className="app__actions">
        <button
          type="button"
          className="app__export-button"
          onClick={handleExportAll}
          data-testid="export-all-button"
          disabled={Boolean(error) || isLoading || isExporting || fixtures.length === 0}>
          {isExporting ? 'Exporting diagrams…' : `Export all diagrams (${fixtures.length})`}
        </button>
        {exportFeedback ? (
          <span className={`app__export-feedback app__export-feedback--${exportFeedback.type}`}>
            {exportFeedback.message}
          </span>
        ) : null}
      </div>
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
          <section
            key={key}
            className="fixture"
            data-fixture-id={key}
            data-fixture-file={entry.fileName ?? `${key}.json`}>
            <header className="fixture__header">
              <h2 className="fixture__title">{title}</h2>
              {description ? <p className="fixture__description">{description}</p> : null}
            </header>
            <div className="fixture__viewer">
              <DiagramViewer
                ref={(handle) => registerViewerHandle(key, handle)}
                fixture={fixture}
                onMetricsChange={handleMetricsChange}
                showDebug={showDebug}
              />
            </div>
          </section>
        );
      })}
    </div>
  );
}

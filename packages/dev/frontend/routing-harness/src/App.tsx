import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { DiagramViewer, type DiagramViewerHandle, type EdgeRoutingAlgorithm } from './components/DiagramViewer';
import { loadFixture, loadManifest } from './lib/fixtureLoader';
import type { DiagramFixture, FixtureManifestEntry } from './types';
import './styles/app.css';
import './styles/edge-overlay.css';

interface LoadedFixture {
  entry: FixtureManifestEntry;
  fixture: DiagramFixture;
}

const EDGE_ROUTING_ALGORITHMS: EdgeRoutingAlgorithm[] = ['manhattan', 'smartManhattan', 'oblique'];

const findEdgeRoutingAlgorithm = (value: string | null): EdgeRoutingAlgorithm | undefined => {
  if (!value) {
    return undefined;
  }
  const normalized = value.trim().toLowerCase();
  return EDGE_ROUTING_ALGORITHMS.find((candidate) => candidate.toLowerCase() === normalized);
};

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
  const edgeAlgorithmOverride = useMemo<EdgeRoutingAlgorithm | undefined>(() => {
    if (typeof window === 'undefined') {
      return undefined;
    }
    const url = new URL(window.location.href);
    return findEdgeRoutingAlgorithm(url.searchParams.get('algorithm'));
  }, []);
  const exportRange = useMemo<{ start: number; count: number | null }>(() => {
    if (typeof window === 'undefined') {
      return { start: 0, count: null };
    }
    const url = new URL(window.location.href);
    const startParam = Number.parseInt(url.searchParams.get('batchStart') ?? url.searchParams.get('exportStart') ?? '', 10);
    const countParam = Number.parseInt(url.searchParams.get('batchCount') ?? url.searchParams.get('exportCount') ?? '', 10);

    const start = Number.isFinite(startParam) && startParam > 0 ? Math.max(0, startParam) : 0;
    const count = Number.isFinite(countParam) && countParam > 0 ? Math.max(1, countParam) : null;

    return { start, count };
  }, []);

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

  const fixturesToDisplay = useMemo<LoadedFixture[]>(() => {
    if (fixtures.length === 0) {
      return [];
    }
    const startIndex = Math.min(exportRange.start, fixtures.length);
    if (startIndex >= fixtures.length) {
      return [];
    }
    const endIndex =
      exportRange.count !== null ? Math.min(startIndex + exportRange.count, fixtures.length) : fixtures.length;
    return fixtures.slice(startIndex, endIndex);
  }, [fixtures, exportRange]);
  const totalFixtures = fixtures.length;
  const hasExportRange = exportRange.start > 0 || exportRange.count !== null;
  const rangeStartIndex = Math.min(exportRange.start, totalFixtures);
  const rangeEndIndex =
    fixturesToDisplay.length > 0 ? rangeStartIndex + fixturesToDisplay.length : rangeStartIndex;
  const rangeSummary =
    hasExportRange && totalFixtures > 0
      ? fixturesToDisplay.length > 0
        ? `Showing fixtures ${rangeStartIndex + 1}\u2013${rangeEndIndex} of ${totalFixtures}`
        : `No fixtures in selected range (start ${rangeStartIndex + 1}).`
      : null;

  useEffect(() => {
    const handles = viewerHandlesRef.current;
    const allowed = new Set(fixturesToDisplay.map(({ entry }) => entry.id));
    for (const key of Array.from(handles.keys())) {
      if (!allowed.has(key)) {
        handles.delete(key);
      }
    }
  }, [fixturesToDisplay]);

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
    if (isExporting || fixturesToDisplay.length === 0 || error) {
      return;
    }

    setIsExporting(true);
    setExportFeedback(null);

    try {
      let exportedCount = 0;

      for (const { entry } of fixturesToDisplay) {
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
  }, [downloadImage, error, fixturesToDisplay, isExporting]);

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
          disabled={Boolean(error) || isLoading || isExporting || fixturesToDisplay.length === 0}>
          {isExporting ? 'Exporting diagrams…' : `Export diagrams (${fixturesToDisplay.length})`}
        </button>
        {rangeSummary ? <span className="app__range-summary">{rangeSummary}</span> : null}
        {edgeAlgorithmOverride ? (
          <span className="app__range-summary">Edge routing override: {edgeAlgorithmOverride}</span>
        ) : null}
        {exportFeedback ? (
          <span className={`app__export-feedback app__export-feedback--${exportFeedback.type}`}>
            {exportFeedback.message}
          </span>
        ) : null}
      </div>
      {error ? <div className="app__status app__status--error">{error}</div> : null}
      {!error && isLoading ? <div className="app__status">Loading fixtures…</div> : null}
      {!isLoading && !error && totalFixtures === 0 ? (
        <div className="app__status">No fixtures available.</div>
      ) : null}
      {!isLoading && !error && totalFixtures > 0 && fixturesToDisplay.length === 0 ? (
        <div className="app__status">No fixtures in the selected range.</div>
      ) : null}
      {fixturesToDisplay.map(({ entry, fixture }) => {
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
                edgeAlgorithmOverride={edgeAlgorithmOverride}
              />
            </div>
          </section>
        );
      })}
    </div>
  );
}

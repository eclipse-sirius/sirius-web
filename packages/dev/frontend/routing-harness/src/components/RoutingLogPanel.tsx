import { memo, useCallback, useEffect, useMemo, useState } from 'react';
import type { XYPosition } from '@xyflow/react';
import type { RoutingTraceEvent } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/RoutingTraceContext';

export type RoutingTraceLogEntry = RoutingTraceEvent & {
  sequence: number;
  timestamp: number;
};

interface RoutingLogPanelProps {
  fixtureId: string;
  events: RoutingTraceLogEntry[];
}

const formatPoint = (point: XYPosition): string => `(${point.x.toFixed(1)}, ${point.y.toFixed(1)})`;

const formatPolyline = (polyline: XYPosition[] | undefined): string =>
  polyline && polyline.length > 0 ? polyline.map(formatPoint).join(' → ') : '—';

const formatRoutingEvent = (event: RoutingTraceLogEntry): string => {
  const lines: string[] = [
    `#${event.sequence.toString().padStart(3, '0')} ${event.phase} edge=${event.edgeId}`,
    `time: ${new Date(event.timestamp).toISOString()}`,
    `message: ${event.message}`,
    `polyline: ${formatPolyline(event.polyline)}`,
  ];

  if (event.metadata) {
    const metadata = JSON.stringify(event.metadata, null, 2);
    lines.push(`metadata: ${metadata}`);
  }

  return lines.join('\n');
};

export const RoutingLogPanel = memo(({ fixtureId, events }: RoutingLogPanelProps) => {
  if (events.length === 0) {
    return (
      <details className="routing-log">
        <summary>Routing decisions ({fixtureId})</summary>
        <div className="routing-log__content routing-log__content--empty">No routing decisions recorded.</div>
      </details>
    );
  }

  const logText = useMemo(() => events.map(formatRoutingEvent).join('\n\n'), [events]);
  const [copyFeedback, setCopyFeedback] = useState<'idle' | 'copied' | 'error'>('idle');

  useEffect(() => {
    if (copyFeedback === 'idle') {
      return;
    }
    const handle = window.setTimeout(() => setCopyFeedback('idle'), 2000);
    return () => window.clearTimeout(handle);
  }, [copyFeedback]);

  const handleCopy = useCallback(async () => {
    if (!logText) {
      return;
    }
    try {
      if (navigator.clipboard?.writeText) {
        await navigator.clipboard.writeText(logText);
        setCopyFeedback('copied');
        return;
      }
    } catch (error) {
      // Fallback to legacy approach when clipboard API is unavailable or fails.
    }

    let tempTextarea: HTMLTextAreaElement | null = null;
    try {
      tempTextarea = document.createElement('textarea');
      tempTextarea.value = logText;
      tempTextarea.style.position = 'fixed';
      tempTextarea.style.left = '-9999px';
      document.body.appendChild(tempTextarea);
      tempTextarea.select();
      document.execCommand('copy');
      setCopyFeedback('copied');
    } catch (error) {
      setCopyFeedback('error');
    } finally {
      if (tempTextarea && tempTextarea.parentNode) {
        tempTextarea.parentNode.removeChild(tempTextarea);
      }
    }
  }, [logText]);

  return (
    <details className="routing-log">
      <summary>Routing decisions ({fixtureId})</summary>
      <div className="routing-log__content">
        <div className="routing-log__actions">
          <button type="button" className="routing-log__copy-button" onClick={handleCopy}>
            Copy log
          </button>
          <span className="routing-log__copy-feedback" role="status" aria-live="polite">
            {copyFeedback === 'copied' ? 'Copied!' : copyFeedback === 'error' ? 'Copy failed' : ''}
          </span>
        </div>
        <textarea
          className="routing-log__textarea"
          readOnly
          value={logText}
          aria-label={`Routing decisions for ${fixtureId}`}
        />
      </div>
    </details>
  );
});

RoutingLogPanel.displayName = 'RoutingLogPanel';

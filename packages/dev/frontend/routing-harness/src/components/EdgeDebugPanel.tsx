import { memo } from 'react';
import type { XYPosition } from '@xyflow/react';

export type SegmentInfo = {
  axis: 'horizontal' | 'vertical';
  coordinate: number;
  start: number;
  end: number;
};

export type EdgeDebugEntry = {
  id: string;
  path?: string;
  points: XYPosition[];
  segments: SegmentInfo[];
};

export type OverlapInfo = {
  edgeId: string;
  otherEdgeId: string;
  axis: 'horizontal' | 'vertical';
  coordinate: number;
  start: number;
  end: number;
};

interface EdgeDebugPanelProps {
  fixtureId: string;
  entries: EdgeDebugEntry[];
  overlaps: OverlapInfo[];
}

const formatPoint = (point: XYPosition): string => `(${point.x.toFixed(1)}, ${point.y.toFixed(1)})`;

const formatSegment = (segment: SegmentInfo): string => {
  const axisLabel = segment.axis === 'vertical' ? 'V' : 'H';
  return `${axisLabel}@${segment.coordinate.toFixed(1)} [${segment.start.toFixed(1)}, ${segment.end.toFixed(1)}]`;
};

export const EdgeDebugPanel = memo(({ fixtureId, entries, overlaps }: EdgeDebugPanelProps) => {
  if (entries.length === 0) {
    return null;
  }

  return (
    <details className="edge-debug" open>
      <summary>Edge routing debug ({fixtureId})</summary>
      <div className="edge-debug__content">
        {overlaps.length > 0 ? (
          <div className="edge-debug__overlaps edge-debug__overlaps--warn">
            <strong>Overlaps detected:</strong>
            <ul>
              {overlaps.map((overlap, index) => (
                <li key={`${overlap.edgeId}-${overlap.otherEdgeId}-${index}`}>
                  {overlap.edgeId} ↔ {overlap.otherEdgeId} &mdash;{' '}
                  {overlap.axis === 'vertical' ? 'V' : 'H'}@{overlap.coordinate.toFixed(1)} [
                  {overlap.start.toFixed(1)}, {overlap.end.toFixed(1)}]
                </li>
              ))}
            </ul>
          </div>
        ) : (
          <div className="edge-debug__overlaps edge-debug__overlaps--ok">No overlapping segments detected.</div>
        )}

        {entries.map((entry) => (
          <div key={entry.id} className="edge-debug__entry">
            <h4 className="edge-debug__entry-title">{entry.id}</h4>
            {entry.path ? (
              <div className="edge-debug__row">
                <span className="edge-debug__label">path</span>
                <code>{entry.path}</code>
              </div>
            ) : null}
            <div className="edge-debug__row">
              <span className="edge-debug__label">points</span>
              <code>{entry.points.map(formatPoint).join(' → ')}</code>
            </div>
            <div className="edge-debug__row">
              <span className="edge-debug__label">segments</span>
              <code>{entry.segments.map(formatSegment).join(' • ') || '—'}</code>
            </div>
          </div>
        ))}
      </div>
    </details>
  );
});

EdgeDebugPanel.displayName = 'EdgeDebugPanel';

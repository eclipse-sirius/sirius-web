import type { ReactFlowState } from '@xyflow/react';
import { useStore } from '@xyflow/react';
import { useCallback } from 'react';

interface HarnessMarkerProps {
  id: string;
  color: string;
}

const markerSelector = (state: ReactFlowState): HarnessMarkerProps[] => {
  const uniqueIds = new Set<string>();
  const markers: HarnessMarkerProps[] = [];

  state.edges.forEach((edge) => {
    if (typeof edge.markerEnd !== 'string') {
      return;
    }
    if (uniqueIds.has(edge.markerEnd)) {
      return;
    }

    const color =
      typeof edge.style?.stroke === 'string' && edge.style.stroke.trim().length > 0
        ? edge.style.stroke
        : '#0f0f0f';

    uniqueIds.add(edge.markerEnd);
    markers.push({
      id: edge.markerEnd,
      color,
    });
  });

  return markers;
};

const markerEquality = (prev: HarnessMarkerProps[], next: HarnessMarkerProps[]) => {
  if (prev.length !== next.length) {
    return false;
  }
  return prev.every((entry, index) => {
    const other = next[index];
    return !!other && entry.id === other.id && entry.color === other.color;
  });
};

export const HarnessMarkerDefinitions = () => {
  const selector = useCallback(markerSelector, []);
  const markers = useStore(selector, markerEquality);

  if (markers.length === 0) {
    return null;
  }

  return (
    <svg id="harness-edge-markers" style={{ position: 'absolute', inset: 0, width: 0, height: 0 }}>
      <defs>
        {markers.map(({ id, color }) => (
          <marker
            key={id}
            id={id}
            markerUnits="strokeWidth"
            markerWidth={6}
            markerHeight={6}
            refX={5}
            refY={3}
            orient="auto-start-reverse">
            <path d="M 0 0 L 5 3 L 0 6 z" fill={color} stroke={color} strokeWidth={1} />
          </marker>
        ))}
      </defs>
    </svg>
  );
};

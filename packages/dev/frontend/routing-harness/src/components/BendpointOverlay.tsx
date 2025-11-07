import type { Edge } from '@xyflow/react';
import { memo } from 'react';
import type { EdgeData } from '../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';

interface BendpointOverlayProps {
  edges: Edge<EdgeData>[];
}

const BendpointOverlayComponent = ({ edges }: BendpointOverlayProps) => {
  return (
    <svg className="bendpoint-overlay" width="100%" height="100%">
      {edges.map((edge) => {
        const points = edge.data?.bendingPoints;
        if (!points || points.length === 0) {
          return null;
        }
        return points.map((point, index) => (
          <circle key={`${edge.id}-bp-${index}`} cx={point.x} cy={point.y} r={4} fill="#ff3b30" stroke="#ffffff" strokeWidth={1} />
        ));
      })}
    </svg>
  );
};

export const BendpointOverlay = memo(BendpointOverlayComponent);

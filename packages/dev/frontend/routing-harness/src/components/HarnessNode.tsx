import { Handle, NodeProps, Position } from '@xyflow/react';
import type { ConnectionHandle } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/handles/ConnectionHandles.types';
import type { NodeData } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import '../styles/harness-node.css';

type HarnessNodeData = NodeData & { harnessLabel?: string };

const getHandleStyle = (handle: ConnectionHandle): React.CSSProperties | undefined => {
  if (!handle.XYPosition) {
    return undefined;
  }

  const { x, y } = handle.XYPosition;
  const style: React.CSSProperties = {};

  if (handle.position === Position.Top || handle.position === Position.Bottom) {
    style.left = `${Math.round(x)}px`;
    style.top = `${Math.round(y)}px`;
  }

  if (handle.position === Position.Left || handle.position === Position.Right) {
    style.top = `${Math.round(y)}px`;
    style.left = `${Math.round(x)}px`;
  }

  return style;
};

export const HarnessNode = ({ data }: NodeProps<HarnessNodeData>) => {
  const label = data.harnessLabel ?? data.targetObjectLabel ?? data.targetObjectId;
  const handles = data.connectionHandles ?? [];

  return (
    <div className="harness-node">
      <div className="harness-node__content">{label}</div>
      {handles.map((handle) => (
        <Handle
          key={`${handle.type}-${handle.id}`}
          id={handle.id}
          type={handle.type}
          position={handle.position}
          style={getHandleStyle(handle)}
        />
      ))}
    </div>
  );
};

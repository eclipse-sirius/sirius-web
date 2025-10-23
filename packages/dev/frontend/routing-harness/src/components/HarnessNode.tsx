import { Handle, NodeProps, Position } from '@xyflow/react';
import type { NodeData } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import '../styles/harness-node.css';

type HarnessNodeData = NodeData & { harnessLabel?: string };

export const HarnessNode = ({ data }: NodeProps<HarnessNodeData>) => {
  const label = data.harnessLabel ?? data.targetObjectLabel ?? data.targetObjectId;

  return (
    <div className="harness-node">
      <div className="harness-node__content">{label}</div>
      <Handle id="source-top" type="source" position={Position.Top} />
      <Handle id="target-top" type="target" position={Position.Top} />
      <Handle id="source-right" type="source" position={Position.Right} />
      <Handle id="target-right" type="target" position={Position.Right} />
      <Handle id="source-bottom" type="source" position={Position.Bottom} />
      <Handle id="target-bottom" type="target" position={Position.Bottom} />
      <Handle id="source-left" type="source" position={Position.Left} />
      <Handle id="target-left" type="target" position={Position.Left} />
    </div>
  );
};

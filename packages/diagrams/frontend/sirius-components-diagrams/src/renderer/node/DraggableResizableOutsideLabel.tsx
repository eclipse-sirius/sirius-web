/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { Theme, useTheme } from '@mui/material/styles';
import { ReactFlowState, useStore } from '@xyflow/react';
import React, { useContext, useState, useEffect } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { Resizable } from 'react-resizable';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { OutsideLabel } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { useLabelMove } from '../move/useLabelMove';
import { useLabelResize } from '../move/useLabelResize';
import { DraggableResizableOutsideLabelProps, LabelSize } from './DraggableResizableOutsideLabel.types';
import 'react-resizable/css/styles.css';

const zoomSelector = (state: ReactFlowState) => state.panZoom?.getViewport().zoom || 1;

const resizableLabelStyle = (
  theme: Theme,
  size: LabelSize,
  label: OutsideLabel,
  highlighted: boolean
): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'relative',
    height: `${size.height}px`,
    width: `${size.width}px`,
    boxSizing: 'content-box',
  };
  if (highlighted && (label.text.length > 0 || label.iconURL.length > 0)) {
    style.borderWidth = `1px`;
    style.borderColor = theme.palette.selected;
    style.borderStyle = 'dashed';
  }
  return style;
};

export const DraggableResizableOutsideLabel = ({
  id,
  label,
  faded,
  selected,
  hovered,
}: DraggableResizableOutsideLabelProps) => {
  const theme: Theme = useTheme();
  const { onNodeLabelMoveStop } = useLabelMove();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { onLabelResizeStop } = useLabelResize();
  const zoom = useStore(zoomSelector);
  const [size, setSize] = useState<LabelSize>({ width: label.width, height: label.height });

  useEffect(() => {
    setSize({ width: label.width, height: label.height });
  }, [label.width, label.height]);

  const onDragStop = (_e, eventData: DraggableData) => {
    onNodeLabelMoveStop(eventData, id);
  };

  const onResize = (_e: React.SyntheticEvent, { size: newSize }: { size: { width: number; height: number } }) => {
    setSize(newSize);
  };

  const onResizeStop = (_e: React.SyntheticEvent, { size: newSize }: { size: { width: number; height: number } }) => {
    onLabelResizeStop(id, newSize);
  };

  const nodeRef = React.useRef<HTMLDivElement>(null);

  const resizable: boolean = selected && !readOnly && label.overflowStrategy !== 'NONE' && label.text.length > 0;

  return (
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <Draggable
        position={label.position}
        onStop={onDragStop}
        scale={zoom}
        nodeRef={nodeRef}
        disabled={readOnly}
        cancel=".react-resizable-handle">
        <div ref={nodeRef} style={resizableLabelStyle(theme, size, label, selected || hovered)}>
          <Resizable
            width={size.width}
            height={size.height}
            onResize={onResize}
            onResizeStop={onResizeStop}
            resizeHandles={resizable ? ['se'] : []}
            transformScale={zoom}
            axis={label.overflowStrategy === 'ELLIPSIS' ? 'x' : 'both'}
            handle={(handleAxis, ref) => <CustomResizeHandle innerRef={ref} handleAxis={handleAxis} />}>
            <div style={{ width: '100%', height: '100%' }}>
              <Label diagramElementId={id} label={label} faded={faded} height={size.height} width={size.width} />
            </div>
          </Resizable>
        </div>
      </Draggable>
    </div>
  );
};

const CustomResizeHandle = (props) => {
  return (
    <div
      ref={props.innerRef}
      className={`react-resizable-handle react-resizable-handle-${props.handleAxis}`}
      style={{ padding: 0 }}
      onMouseDown={props.onMouseDown}
      onMouseUp={props.onMouseUp}
    />
  );
};

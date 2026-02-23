/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import React, { useRef, useState, useEffect } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { Resizable } from 'react-resizable';
import { Label } from '../Label';
import { DraggableResizableLabelProps, LabelSize } from './DraggableResizableLabel.types';
import { Theme, useTheme } from '@mui/material/styles';
import { EdgeLabel } from '../DiagramRenderer.types';

const resizableLabelStyle = (
  theme: Theme,
  size: LabelSize,
  label: EdgeLabel,
  selected: boolean
): React.CSSProperties => {
  const style: React.CSSProperties = {
    height: `${size.height}px`,
    width: `${size.width}px`,
    boxSizing: 'content-box',
    pointerEvents: 'all',
  };
  if (selected && (label.text.length > 0 || label.iconURL.length > 0)) {
    style.borderWidth = `1px`;
    style.borderColor = theme.palette.selected;
    style.borderStyle = 'dashed';
  }
  return style;
};

export const DraggableResizableLabel = ({
  id,
  label,
  position,
  positionOffset,
  onDragStop,
  onResizeStop,
  zoom,
  readOnly,
  selected,
  faded,
  resizeHandlePosition,
  transform,
}: DraggableResizableLabelProps) => {
  const theme: Theme = useTheme();
  const nodeRef = useRef<HTMLDivElement>(null);
  const [size, setSize] = useState<LabelSize>({ width: label.width ?? 0, height: label.height ?? 0 });

  useEffect(() => {
    setSize({ height: label.height, width: label.width });
  }, [label.width, label.height]);

  const handleResize = (_e: React.SyntheticEvent, { size: newSize }: { size: { width: number; height: number } }) => {
    setSize(newSize);
  };

  const resizable: boolean = selected && !readOnly && label.text.length > 0;

  const handleDragStop = (e: any, eventData: DraggableData) => {
    // Prevent a simple click on the label to trigger the dragStop event
    if (eventData.x !== 0 && eventData.y !== 0) {
      onDragStop(e, eventData);
    }
  };

  return (
    <Draggable
      position={position}
      positionOffset={positionOffset}
      onStop={handleDragStop}
      scale={zoom}
      nodeRef={nodeRef}
      disabled={readOnly}
      cancel={'.react-resizable-handle'}>
      <div ref={nodeRef} style={{ position: 'absolute', zIndex: 1001 }}>
        <div style={{ padding: 5, transform: `${transform}` }}>
          <div style={resizableLabelStyle(theme, size, label, selected)}>
            <Resizable
              width={size.width}
              height={size.height}
              onResizeStart={(e) => e.stopPropagation()}
              onResize={handleResize}
              onResizeStop={onResizeStop}
              resizeHandles={resizable ? [resizeHandlePosition] : []}
              transformScale={zoom}
              handle={(handleAxis, ref) => <CustomResizeHandle innerRef={ref} handleAxis={handleAxis} />}>
              <div style={{ width: '100%', height: '100%' }}>
                <Label diagramElementId={id} label={label} faded={faded} height={size.height} width={size.width} />
              </div>
            </Resizable>
          </div>
        </div>
      </div>
    </Draggable>
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

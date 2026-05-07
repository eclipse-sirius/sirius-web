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
import ArrowCircleLeft from '@mui/icons-material/ArrowCircleLeft';
import ArrowCircleRight from '@mui/icons-material/ArrowCircleRight';
import { CSSProperties, Theme, useTheme } from '@mui/material/styles';
import { Handle, Node, NodeProps, Position } from '@xyflow/react';
import { memo, useState } from 'react';
import { HandleNodeData, HandleNodeState } from './HandleNode.types';
import { NodeComponentsMap } from './NodeTypes';

const handleStyle: CSSProperties = {
  background: 'none',
  border: 'none',
  width: 0,
  height: 0,
  fontSize: 'large',
  position: 'relative',
};

const iconStyle = (theme: Theme, position: Position, state: HandleNodeState) => {
  const style: CSSProperties = {
    position: 'absolute',
    fontSize: '32px',
    width: '1em',
    height: '1em',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
  };
  if (position === Position.Top || position === Position.Bottom) {
    style.transform = 'translate(-50%, -50%) rotate(90deg)';
  }
  if (state.isHovered || state.isMouseDown) {
    style.color = theme.palette.selected;
  }
  return style;
};

export const HandleNode: NodeComponentsMap['handleNode'] = memo(({ data }: NodeProps<Node<HandleNodeData>>) => {
  const [state, setState] = useState<HandleNodeState>({
    isHovered: false,
    isMouseDown: false,
  });
  const theme = useTheme();
  let Icon = ArrowCircleRight;
  if (data.position === Position.Bottom) {
    Icon = ArrowCircleRight;
  } else if (data.position === Position.Top) {
    Icon = ArrowCircleLeft;
  } else if (data.position === Position.Left) {
    Icon = ArrowCircleLeft;
  }

  const handleOnMouseDown = () => {
    setState((prevState) => ({
      ...prevState,
      isMouseDown: true,
    }));
  };

  const handleOnMouseEnter = () => {
    setState((prevState) => ({
      ...prevState,
      isHovered: true,
    }));
  };

  const handleOnMouseLeave = () => {
    setState((prevState) => ({
      ...prevState,
      isHovered: false,
    }));
  };

  return (
    <div style={{ width: 1, height: 1 }}>
      <Handle
        id={`creationhandle--${data.nodeId || data.edgeId}--${data.position}`}
        position={data.position}
        type="source"
        style={handleStyle}
        isConnectableStart={true}
        isConnectableEnd={false}
        onMouseDown={handleOnMouseDown}
        onMouseEnter={handleOnMouseEnter}
        onMouseLeave={handleOnMouseLeave}
        data-testid={`creationhandle--${data.position}`}>
        <Icon style={iconStyle(theme, data.position, state)} />
      </Handle>
    </div>
  );
});

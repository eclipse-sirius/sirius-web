/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import IconButton from '@mui/material/IconButton';
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

const iconStyle = () => {
  const style: CSSProperties = {
    fontSize: 'large',
  };
  return style;
};

const iconButtonStyle = (theme: Theme, state: HandleNodeState, position: Position) => {
  const style: CSSProperties = {
    position: 'absolute',
    fontSize: 'large',
    width: '1em',
    height: '1em',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    transition: 'background 0.2s ease',
    '&:hover': {
      backgroundColor: theme.palette.selected,
    },
  };
  if (position === Position.Top || position === Position.Bottom) {
    style.transform = 'translate(-50%, -50%) rotate(90deg)';
  }
  if (state.isMouseDown) {
    style.backgroundColor = theme.palette.selected;
  }
  return style;
};

const initialState: HandleNodeState = {
  isMouseDown: false,
};

export const HandleNode: NodeComponentsMap['handleNode'] = memo(({ data }: NodeProps<Node<HandleNodeData>>) => {
  const [state, setState] = useState<HandleNodeState>(initialState);
  const theme = useTheme();

  let Icon = (
    <IconButton sx={iconButtonStyle(theme, state, data.position)}>
      <ArrowForwardIosIcon color="secondary" sx={iconStyle()} />
    </IconButton>
  );
  if (data.position === Position.Top || data.position === Position.Left) {
    Icon = (
      <IconButton sx={iconButtonStyle(theme, state, data.position)}>
        <ArrowBackIosNewIcon color="secondary" sx={iconStyle()} />
      </IconButton>
    );
  }

  const handleOnMouseDown = () => {
    setState((prevState) => ({
      ...prevState,
      isMouseDown: true,
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
        data-testid={`creationhandle--${data.position}`}>
        {Icon}
      </Handle>
    </div>
  );
});

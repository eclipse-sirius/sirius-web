/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { useContext, useEffect, useState } from 'react';
import { Handle, Position } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ConnectorContext } from '../connector/ConnectorContext';
import { ConnectorContextValue } from '../connector/ConnectorContext.types';
import { useConnector } from '../connector/useConnector';
import { useConnectionCandidatesQuery } from './useConnectionCandidatesQuery';

import { ConnectionCreationHandlesProps, ConnectionCreationHandlesState } from './ConnectionCreationHandles.types';

const connectionCreationHandleStyle = (
  position: Position,
  theme: Theme,
  isHovered: Position | null,
  isMouseDown: Position | null
): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'absolute',
    borderRadius: '0',
    border: 'solid black',
    borderWidth: '0 2px 2px 0',
    display: 'inline-block',
    padding: '2px',
    borderColor: theme.palette.secondary.light,
    backgroundColor: 'transparent',
    width: 12,
    height: 12,
    zIndex: 9999,
  };
  switch (position) {
    case Position.Left:
      style.left = '-15px';
      style.transform = 'translateY(-50%) rotate(135deg)';
      break;
    case Position.Right:
      style.right = '-15px';
      style.transform = 'translateY(-50%) rotate(-45deg)';
      break;
    case Position.Top:
      style.top = '-15px';
      style.transform = 'translateX(-50%) rotate(-135deg)';
      break;
    case Position.Bottom:
      style.bottom = '-15px';
      style.transform = 'translateX(-50%) rotate(45deg)';
      break;
  }
  if (isHovered === position || isMouseDown === position) {
    style.borderColor = theme.palette.primary.main;
  }
  return style;
};

export const ConnectionCreationHandles = ({ nodeId }: ConnectionCreationHandlesProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { onConnectionStartElementClick } = useConnector();
  const { setCandidates } = useContext<ConnectorContextValue>(ConnectorContext);

  const [state, setState] = useState<ConnectionCreationHandlesState>({
    isHovered: null,
    isMouseDown: null,
  });

  const candidates = useConnectionCandidatesQuery(editingContextId, diagramId, nodeId);
  const shouldRender = candidates !== null && candidates.length > 0;
  useEffect(() => {
    if (candidates !== null) {
      setCandidates(candidates);
    }
  }, [candidates]);

  const handleOnMouseDown = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, position: Position) => {
    onConnectionStartElementClick(event);
    setState((prevState) => ({
      ...prevState,
      isMouseDown: position,
    }));
  };

  const handleOnMouseEnter = (position: Position) => {
    setState((prevState) => ({
      ...prevState,
      isHovered: position,
    }));
  };

  const handleOnMouseLeave = () => {
    setState((prevState) => ({
      ...prevState,
      isHovered: null,
    }));
  };

  const theme = useTheme();
  return (
    <>
      {shouldRender
        ? Object.values(Position).map((position) => {
            return (
              <Handle
                id={`handle--${nodeId}--${position}`}
                type="source"
                position={position}
                style={connectionCreationHandleStyle(position, theme, state.isHovered, state.isMouseDown)}
                onMouseDown={(event) => handleOnMouseDown(event, position)}
                onMouseEnter={() => handleOnMouseEnter(position)}
                onMouseLeave={handleOnMouseLeave}
                isConnectableStart={true}
                key={position}
              />
            );
          })
        : null}
    </>
  );
};

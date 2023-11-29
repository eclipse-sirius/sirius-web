/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { memo, useEffect, useState } from 'react';
import { ConnectionLineComponentProps, Position, getSmoothStepPath } from 'reactflow';
import { ConnectionLineState } from './ConnectonLine.types';
import { useConnector } from './useConnector';

export const ConnectionLine = memo(({ fromX, fromY, toX, toY }: ConnectionLineComponentProps) => {
  const { isFrozen } = useConnector();

  const [state, setState] = useState<ConnectionLineState>({
    frozenToX: 0,
    frozenToY: 0,
  });

  useEffect(() => {
    if (isFrozen) {
      setState((prevState) => ({ ...prevState, frozenToX: toX, frozenToY: toY }));
    }
  }, [isFrozen]);

  const [edgePath] = getSmoothStepPath({
    sourceX: fromX,
    sourceY: fromY,
    sourcePosition: Position.Bottom,
    targetX: isFrozen ? state.frozenToX : toX,
    targetY: isFrozen ? state.frozenToY : toY,
    targetPosition: Position.Top,
  });

  return (
    <g>
      <path fill="none" stroke={'black'} strokeWidth={1.5} className="animated" d={edgePath} />
    </g>
  );
});

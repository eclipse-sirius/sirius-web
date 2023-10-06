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

import { useEffect, useRef } from 'react';
import { useUpdateNodeInternals } from 'reactflow';
import { ConnectionHandle } from '../../DiagramRenderer.types';
import { UseConnectionHandlesValue } from './useConnectionHandles.types';

export const useConnectionHandles = (id: string, connectionHandles: ConnectionHandle[]): UseConnectionHandlesValue => {
  const updateNodeInternals = useUpdateNodeInternals();
  const firstUpdate = useRef<boolean>(true);

  const connectionHandlesIdentity = connectionHandles
    .map((handle) => `${handle.edgeId}#${handle.position}#${handle.nodeId}`)
    .join(', ');

  useEffect(() => {
    if (firstUpdate.current && firstUpdate) {
      firstUpdate.current = false;
    } else {
      updateNodeInternals(id);
    }
  }, [connectionHandlesIdentity]);

  return {
    useConnectionHandles,
  };
};

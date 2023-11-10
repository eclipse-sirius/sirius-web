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

import { useEffect } from 'react';
import { useUpdateNodeInternals } from 'reactflow';
import { ConnectionHandle } from '../../DiagramRenderer.types';
import { UseConnectionHandlesValue } from './useConnectionHandles.types';

export const useConnectionHandles = (id: string, connectionHandles: ConnectionHandle[]): UseConnectionHandlesValue => {
  const updateNodeInternals = useUpdateNodeInternals();

  const connectionHandlesIdentity = connectionHandles
    .map((handle) => `${handle.edgeId}#${handle.position}#${handle.nodeId}`)
    .join(', ');

  useEffect(() => {
    console.log('UPDATE NODE INTERNALS');
    console.log(connectionHandles);
    updateNodeInternals(id);
  }, [connectionHandlesIdentity]);

  return {
    useConnectionHandles,
  };
};

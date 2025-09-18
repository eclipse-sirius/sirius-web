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

import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

// @technical-debt
// This hook is used only in DiagramRenderer
// It is use to reset the connectionClickStartHandle after ending a connection on a creation handle
// see https://github.com/eclipse-sirius/sirius-web/issues/5519
// This should be removed in favor of a less "hacky" solution
export const useResetXYFlowConnection = () => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  useEffect(() => {
    if (!store.getState().connection.inProgress && store.getState().connectionClickStartHandle) {
      store.setState((xyFlowState) => {
        return {
          ...xyFlowState,
          connectionClickStartHandle: null,
        };
      });
    }
  }, [store.getState().connectionClickStartHandle]);
};

/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { useContext } from 'react';
import { StoreContext } from './StoreContext';
import { StoreContextValue } from './StoreContext.types';
import { useStoreValue } from './useStore.types';

export const useStore = (): useStoreValue => {
  const { getEdges, getNodes, onEdgesChange, onNodesChange, setEdges, setNodes, getEdge, getNode } =
    useContext<StoreContextValue>(StoreContext);

  return {
    getEdges,
    getNodes,
    onEdgesChange,
    onNodesChange,
    setEdges,
    setNodes,
    getEdge,
    getNode,
  };
};

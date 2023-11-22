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

import React, { useState } from 'react';
import { DropNodeContextProviderProps, DropNodeContextState, DropNodeContextValue } from './DropNodeContext.types';

const defaultValue: DropNodeContextValue = {
  initialParentId: null,
  draggedNode: null,
  targetNodeId: null,
  compatibleNodeIds: [],
  droppableOnDiagram: false,
  initializeDrop: () => {},
  setTargetNodeId: () => {},
  resetDrop: () => {},
};

export const DropNodeContext = React.createContext<DropNodeContextValue>(defaultValue);

export const DropNodeContextProvider = ({ children }: DropNodeContextProviderProps) => {
  const [state, setState] = useState<DropNodeContextState>({
    initialParentId: null,
    draggedNode: null,
    targetNodeId: null,
    compatibleNodeIds: [],
    droppableOnDiagram: false,
  });

  const initializeDrop = (dropData: DropNodeContextState) => {
    setState((prevState) => ({ ...prevState, ...dropData }));
  };

  const setTargetNodeId = (targetNodeId: string | null) => {
    setState((prevState) => ({ ...prevState, targetNodeId }));
  };

  const resetDrop = () => {
    setState((prevState) => ({
      ...prevState,
      initialParentId: null,
      draggedNode: null,
      targetNodeId: null,
      droppableOnDiagram: false,
      compatibleNodeIds: [],
    }));
  };

  return (
    <DropNodeContext.Provider
      value={{
        initialParentId: state.initialParentId,
        draggedNode: state.draggedNode,
        targetNodeId: state.targetNodeId,
        compatibleNodeIds: state.compatibleNodeIds,
        droppableOnDiagram: state.droppableOnDiagram,
        initializeDrop,
        setTargetNodeId,
        resetDrop,
      }}>
      {children}
    </DropNodeContext.Provider>
  );
};

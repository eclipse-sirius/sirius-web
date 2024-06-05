/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
  initialPosition: null,
  initialPositionAbsolute: null,
  droppableOnDiagram: false,
  draggedNodeId: '',
  initializeDrop: () => {},
  resetDrop: () => {},
};

export const DropNodeContext = React.createContext<DropNodeContextValue>(defaultValue);

export const DropNodeContextProvider = ({ children }: DropNodeContextProviderProps) => {
  const [state, setState] = useState<DropNodeContextState>(defaultValue);

  const initializeDrop = (dropData: DropNodeContextState) => {
    setState((prevState) => ({ ...prevState, ...dropData }));
  };

  const resetDrop = () => {
    setState((prevState) => ({
      ...prevState,
      initialPosition: null,
      initialPositionAbsolute: null,
      droppableOnDiagram: false,
      draggedNodeId: '',
    }));
  };

  return (
    <DropNodeContext.Provider
      value={{
        initialPosition: state.initialPosition,
        initialPositionAbsolute: state.initialPositionAbsolute,
        droppableOnDiagram: state.droppableOnDiagram,
        draggedNodeId: state.draggedNodeId,
        initializeDrop,
        resetDrop,
      }}>
      {children}
    </DropNodeContext.Provider>
  );
};

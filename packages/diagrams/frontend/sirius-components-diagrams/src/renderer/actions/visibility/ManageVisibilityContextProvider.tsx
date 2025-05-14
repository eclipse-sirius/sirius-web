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
import React, { useState } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { ManageVisibilityContextState, ManageVisibilityContextValue } from './ManageVisibilityContextProvider.types';
import { ManageVisibilityNodeAction } from './ManageVisibilityNodeAction';

const defaultContextValue: ManageVisibilityContextValue = {
  openDialog: () => {},
  closeDialog: () => {},
};

const defaultStateValue: ManageVisibilityContextState = {
  isOpen: false,
  diagramElementId: '',
  initialPosition: { x: 0, y: 0 },
};

export const ManageVisibilityContext = React.createContext<ManageVisibilityContextValue>(defaultContextValue);

export const ManageVisibilityContextProvider = ({ children }) => {
  const [state, setState] = useState<ManageVisibilityContextState>(defaultStateValue);
  const reactFlowStore = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const openDialog = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, diagramElementId: string) => {
    const { domNode } = reactFlowStore.getState();
    const bounds = domNode?.getBoundingClientRect();
    const initialPosition = {
      x: bounds ? event.clientX - bounds.left : event.clientX,
      y: bounds ? event.clientY - bounds.top : event.clientX,
    };

    setState((prevState) => ({
      ...prevState,
      isOpen: true,
      diagramElementId,
      initialPosition: initialPosition,
    }));
  };

  const closeDialog = () =>
    setState((prevState) => ({
      ...prevState,
      isOpen: false,
      diagramElementId: '',
    }));

  return (
    <>
      <ManageVisibilityContext.Provider value={{ openDialog, closeDialog }}>
        {children}
      </ManageVisibilityContext.Provider>
      {state.isOpen ? (
        <ManageVisibilityNodeAction
          diagramElementId={state.diagramElementId}
          initialPosition={state.initialPosition}
          closeDialog={closeDialog}
        />
      ) : null}
    </>
  );
};

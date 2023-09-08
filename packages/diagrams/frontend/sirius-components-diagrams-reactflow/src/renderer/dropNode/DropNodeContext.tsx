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
import { DropNodeContextProviderProps, DropNodeContextValue } from './DropNodeContext.types';
import { NodeDropData } from './useDropNode.types';

const defaultValue: DropNodeContextValue = {
  dropData: {
    draggedNodeId: null,
    targetNodeId: null,
    compatibleNodeIds: [],
    droppableOnDiagram: false,
  },
  setDropData: () => {},
};

export const DropNodeContext = React.createContext<DropNodeContextValue>(defaultValue);

export const DropNodeContextProvider = ({ children }: DropNodeContextProviderProps) => {
  const [dropData, setDropData] = useState<NodeDropData>(defaultValue.dropData);
  return <DropNodeContext.Provider value={{ dropData, setDropData }}>{children}</DropNodeContext.Provider>;
};

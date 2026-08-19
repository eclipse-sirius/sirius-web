/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import React from 'react';
import {
  TreeItemToolExecutorContextProviderProps,
  TreeItemToolExecutorContextValue,
} from './TreeItemToolExecutorContext.types';
import { useInvokeTreeItemTool } from './useInvokeTreeItemTool';

const defaultValue: TreeItemToolExecutorContextValue = {
  invokeTreeItemTool: () => {},
};

export const TreeItemToolExecutorContext = React.createContext<TreeItemToolExecutorContextValue>(defaultValue);

export const TreeItemToolExecutorContextProvider = ({ children }: TreeItemToolExecutorContextProviderProps) => {
  const { invokeTreeItemTool } = useInvokeTreeItemTool();

  return (
    <TreeItemToolExecutorContext.Provider value={{ invokeTreeItemTool }}>
      {children}
    </TreeItemToolExecutorContext.Provider>
  );
};

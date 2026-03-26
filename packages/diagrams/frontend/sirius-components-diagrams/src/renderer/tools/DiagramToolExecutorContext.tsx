/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useSingleClickOnTwoDiagramElementTool } from '../connector/useSingleClickOnTwoDiagramElementTool';
import { GQLTool } from '../palette/Palette.types';
import {
  DiagramToolExecutorContextProviderProps,
  DiagramToolExecutorContextValue,
} from './DiagramToolExecutorContext.types';
import { useInvokePaletteTool } from './useInvokePaletteTool';

const defaultValue: DiagramToolExecutorContextValue = {
  executeTool: () => {},
  invokeConnectorTool: () => {},
};

export const DiagramToolExecutorContext = React.createContext<DiagramToolExecutorContextValue>(defaultValue);

export const DiagramToolExecutorContextProvider = ({ children }: DiagramToolExecutorContextProviderProps) => {
  const { invokeTool } = useInvokePaletteTool();
  const { invokeConnectorTool } = useSingleClickOnTwoDiagramElementTool();
  const executeTool = (
    x: number,
    y: number,
    diagramElementIds: string[],
    targetObjectId: string,
    onDirectEditClick: () => void,
    tool: GQLTool
  ) => {
    invokeTool(x, y, diagramElementIds, targetObjectId, onDirectEditClick, tool);
  };

  return (
    <DiagramToolExecutorContext.Provider value={{ executeTool, invokeConnectorTool }}>
      {children}
    </DiagramToolExecutorContext.Provider>
  );
};

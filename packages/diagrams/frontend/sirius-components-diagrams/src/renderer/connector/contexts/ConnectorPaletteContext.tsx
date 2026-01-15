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

import { ReactFlowState, useStore } from '@xyflow/react';
import React, { useCallback, useState } from 'react';
import { GQLTool } from '../../palette/Palette.types';
import {
  ConnectorPaletteContextProviderProps,
  ConnectorPaletteContextProviderState,
  ConnectorPaletteContextValue,
  PaletteWithLastTool,
} from './ConnectorPaletteContext.types';

const defaultValue: ConnectorPaletteContextValue = {
  x: null,
  y: null,
  isOpened: false,
  sourceDiagramElementId: null,
  targetDiagramElementId: null,
  candidateDescriptionIds: [],
  isConnectionInProgress: false,
  setCandidateDescriptionIds: () => {},
  hideConnectorPalette: () => {},
  showConnectorPalette: () => {},
  getLastToolInvoked: () => null,
  setLastToolInvoked: () => {},
};

const isConnectionInProgressSelector = (state: ReactFlowState) =>
  state.connection.inProgress && !!state.connection.fromHandle.id?.startsWith('creation');
export const ConnectorPaletteContext = React.createContext<ConnectorPaletteContextValue>(defaultValue);

export const ConnectorPaletteContextProvider = ({ children }: ConnectorPaletteContextProviderProps) => {
  const [state, setState] = useState<ConnectorPaletteContextProviderState>({
    x: null,
    y: null,
    isOpened: false,
    sourceDiagramElementId: null,
    targetDiagramElementId: null,
    candidateDescriptionIds: [],
    lastToolsInvoked: [],
  });

  const showPalette = useCallback(
    (x: number, y: number, sourceDiagramElementId: string, targetDiagramElementId: string) => {
      setState((prevState) => ({ ...prevState, x, y, isOpened: true, sourceDiagramElementId, targetDiagramElementId }));
    },
    []
  );

  const hidePalette = useCallback(() => {
    if (state.isOpened) {
      setState((prevState) => ({
        ...prevState,
        x: null,
        y: null,
        isOpened: false,
        sourceDiagramElementId: null,
        targetDiagramElementId: null,
      }));
    }
  }, [state.isOpened]);

  const getLastToolInvoked = (paletteId: string): GQLTool | null => {
    return (
      state.lastToolsInvoked.find((toolSectionWithDefaultTool) => toolSectionWithDefaultTool.paletteId === paletteId)
        ?.lastTool || null
    );
  };

  const setLastToolInvoked = (paletteId: string, tool: GQLTool) => {
    const lastToolsInvoked: PaletteWithLastTool[] = state.lastToolsInvoked;
    if (lastToolsInvoked.some((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId)) {
      lastToolsInvoked.splice(
        lastToolsInvoked.findIndex((toolSectionWithLastTool) => toolSectionWithLastTool.paletteId === paletteId),
        1
      );
    }
    lastToolsInvoked.push({ paletteId, lastTool: tool });
  };

  const setCandidateDescriptionIds = (candidateDescriptionIds: string[]) => {
    setState((prevState) => ({
      ...prevState,
      candidateDescriptionIds,
    }));
  };

  const isConnectionInProgress = useStore((state) => isConnectionInProgressSelector(state));
  return (
    <ConnectorPaletteContext.Provider
      value={{
        x: state.x,
        y: state.y,
        isOpened: state.isOpened,
        sourceDiagramElementId: state.sourceDiagramElementId,
        targetDiagramElementId: state.targetDiagramElementId,
        candidateDescriptionIds: state.candidateDescriptionIds,
        isConnectionInProgress,
        showConnectorPalette: showPalette,
        hideConnectorPalette: hidePalette,
        setCandidateDescriptionIds,
        getLastToolInvoked,
        setLastToolInvoked,
      }}>
      {children}
    </ConnectorPaletteContext.Provider>
  );
};

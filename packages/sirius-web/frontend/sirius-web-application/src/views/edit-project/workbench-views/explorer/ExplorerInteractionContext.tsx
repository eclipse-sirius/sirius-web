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

import { FilterBarContext, FilterBarContextValue } from '@eclipse-sirius/sirius-components-trees';
import React, { useContext, useEffect, useState } from 'react';
import {
  ExplorerInteractionContextProviderProps,
  ExplorerInteractionContextState,
  ExplorerInteractionContextValue,
} from './ExplorerInteractionContext.types';

const defaultValue: ExplorerInteractionContextValue = {
  activeTreeDescriptionId: null,
  expanded: [],
  collapsed: [],
  maxDepth: 0,
  setActiveDescriptionId: () => {},
  onExpandedElementChange: () => {},
};

export const ExplorerInteractionContext = React.createContext<ExplorerInteractionContextValue>(defaultValue);

export const ExplorerInteractionContextProvider = ({
  activeTreeDescriptionId,
  explorerDescriptions,
  children,
}: ExplorerInteractionContextProviderProps) => {
  const { filterBarText, filterBarTreeFiltering } = useContext<FilterBarContextValue>(FilterBarContext);

  const [state, setState] = useState<ExplorerInteractionContextState>({
    activeTreeDescriptionId,
    expanded: {},
    collapsed: {},
    maxDepth: {},
  });

  const setActiveDescriptionId = (activeTreeDescriptionId: string) => {
    setState((prevState) => {
      return {
        ...prevState,
        activeTreeDescriptionId,
      };
    });
  };

  const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
    setState((prevState) => {
      if (prevState.activeTreeDescriptionId) {
        const activeTreeDescriptionId: string = prevState.activeTreeDescriptionId;

        if (filterBarText && filterBarTreeFiltering) {
          return {
            ...prevState,
            collapsed: {
              ...prevState.collapsed,
              [activeTreeDescriptionId]: newExpandedIds,
            },
          };
        }
        return {
          ...prevState,
          expanded: {
            ...prevState.expanded,
            [activeTreeDescriptionId]: newExpandedIds,
          },
          maxDepth: {
            ...prevState.maxDepth,
            [activeTreeDescriptionId]: Math.max(newMaxDepth, prevState.maxDepth[activeTreeDescriptionId] ?? 1),
          },
        };
      } else {
        return prevState;
      }
    });
  };

  useEffect(() => {
    if (explorerDescriptions && explorerDescriptions.length > 0) {
      const expandedInitiated: { [key: string]: string[] } = {};
      const collapsedInitiated: { [key: string]: string[] } = {};
      const maxDepthInitiated: { [key: string]: number } = {};
      explorerDescriptions.forEach((explorerDescription) => {
        expandedInitiated[explorerDescription.id] = [];
        collapsedInitiated[explorerDescription.id] = [];
        maxDepthInitiated[explorerDescription.id] = 1;
      });

      setState((prevState) => ({
        ...prevState,
        activeTreeDescriptionId: prevState.activeTreeDescriptionId ?? explorerDescriptions[0].id,
        expanded: expandedInitiated,
        collapsed: collapsedInitiated,
        maxDepth: maxDepthInitiated,
      }));
    }
  }, [explorerDescriptions]);

  return (
    <ExplorerInteractionContext.Provider
      value={{
        activeTreeDescriptionId: state.activeTreeDescriptionId,
        expanded: state.activeTreeDescriptionId ? state.expanded[state.activeTreeDescriptionId] ?? [] : [],
        collapsed: state.activeTreeDescriptionId ? state.collapsed[state.activeTreeDescriptionId] ?? [] : [],
        maxDepth: state.activeTreeDescriptionId ? state.maxDepth[state.activeTreeDescriptionId] ?? 1 : 1,
        setActiveDescriptionId,
        onExpandedElementChange,
      }}>
      {children}
    </ExplorerInteractionContext.Provider>
  );
};

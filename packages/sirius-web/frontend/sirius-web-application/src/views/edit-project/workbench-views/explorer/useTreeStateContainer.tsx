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

import { useEffect, useState } from 'react';
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';
import { UseTreeStateContainerState, UseTreeStateContainerValue } from './useTreeStateContainer.types';

export const useTreeStateContainer = (
  configuredActiveTreeDescriptionId: string | null,
  explorerDescriptions: TreeDescriptionMetadata[]
): UseTreeStateContainerValue => {
  const [state, setState] = useState<UseTreeStateContainerState>({
    activeTreeDescriptionId: configuredActiveTreeDescriptionId,
    expanded: {},
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
      const maxDepthInitiated: { [key: string]: number } = {};
      explorerDescriptions.forEach((explorerDescription) => {
        expandedInitiated[explorerDescription.id] = [];
        maxDepthInitiated[explorerDescription.id] = 1;
      });

      setState((prevState) => ({
        ...prevState,
        activeTreeDescriptionId: prevState.activeTreeDescriptionId ?? explorerDescriptions[0].id,
        expanded: expandedInitiated,
        maxDepth: maxDepthInitiated,
      }));
    }
  }, [explorerDescriptions]);

  return {
    activeTreeDescriptionId: state.activeTreeDescriptionId,
    expanded: state.activeTreeDescriptionId ? state.expanded[state.activeTreeDescriptionId] ?? [] : [],
    maxDepth: state.activeTreeDescriptionId ? state.maxDepth[state.activeTreeDescriptionId] ?? 1 : 1,
    setActiveDescriptionId,
    onExpandedElementChange,
  };
};

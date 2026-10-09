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
import { useContext, useEffect, useState } from 'react';
import { ExplorerContext } from './ExplorerContext';
import { ExplorerFilterContext } from './ExplorerFilterContext';
import { ExplorerFilterContextValue } from './ExplorerFilterContext.types';
import { ExplorerInteractionContext } from './ExplorerInteractionContext';
import { ExplorerInteractionContextValue } from './ExplorerInteractionContext.types';
import {
  ExplorerSubscriptionContainerProps,
  ExplorerSubscriptionContainerState,
} from './ExplorerSubscriptionContainer.types';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload && payload.__typename === 'TreeRefreshedEventPayload';

export const ExplorerSubscriptionContainer = ({ editingContextId, children }: ExplorerSubscriptionContainerProps) => {
  const [state, setState] = useState<ExplorerSubscriptionContainerState>({
    tree: null,
  });

  const { activeTreeDescriptionId, expanded, collapsed, maxDepth } =
    useContext<ExplorerInteractionContextValue>(ExplorerInteractionContext);
  const { treeFilters } = useContext<ExplorerFilterContextValue>(ExplorerFilterContext);
  const { filterBarTreeFiltering, filterBarText } = useContext<FilterBarContextValue>(FilterBarContext);

  const activeTreeFilterIds = treeFilters.filter((filter) => filter.state).map((filter) => filter.id);

  const { payload } = useExplorerSubscription(
    editingContextId,
    activeTreeDescriptionId,
    activeTreeFilterIds,
    expanded,
    collapsed,
    filterBarTreeFiltering ? filterBarText : '',
    maxDepth
  );

  useEffect(() => {
    if (!!payload && isTreeRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, tree: payload.tree }));
    }
  }, [payload]);

  const resetTree = () => setState((prevState) => ({ ...prevState, tree: null }));

  return <ExplorerContext.Provider value={{ tree: state.tree, resetTree }}>{children}</ExplorerContext.Provider>;
};

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
import { ExplorerContext } from './ExplorerContext';
import {
  ExplorerSubscriptionContainerProps,
  ExplorerSubscriptionContainerState,
} from './ExplorerSubscriptionContainer.types';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload && payload.__typename === 'TreeRefreshedEventPayload';

export const ExplorerSubscriptionContainer = ({
  editingContextId,
  activeTreeDescriptionId,
  activeTreeFilterIds,
  expanded,
  maxDepth,
  children,
}: ExplorerSubscriptionContainerProps) => {
  const [state, setState] = useState<ExplorerSubscriptionContainerState>({
    tree: null,
  });

  const { payload } = useExplorerSubscription(
    editingContextId,
    activeTreeDescriptionId,
    activeTreeFilterIds,
    expanded,
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

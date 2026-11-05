/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import {
  ViewAccordion,
  ViewAccordionContent,
  ViewAccordionToolbar,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import { FilterBarContextProvider } from '@eclipse-sirius/sirius-components-trees';
import { ForwardedRef, forwardRef } from 'react';
import { ExplorerSelectionContextProvider } from './ExplorerSelectionContext';
import { ExplorerSubscriptionContainer } from './ExplorerSubscriptionContainer';
import { ExplorerToolbarRenderer } from './ExplorerToolbarRenderer';
import { ExplorerTreeRenderer } from './ExplorerTreeRenderer';
import { ExplorerViewConfiguration } from './ExplorerView.types';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useTreeFiltering } from './useTreeFiltering';
import { useTreeStateContainer } from './useTreeStateContainer';

export const ExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { editingContextId, id, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const initialExplorerViewConfiguration: ExplorerViewConfiguration =
      initialConfiguration as unknown as ExplorerViewConfiguration;

    const configuredActiveTreeDescriptionId = initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null;
    const configuredTreeFilters = initialExplorerViewConfiguration?.activeTreeFilters ?? [];

    const { explorerDescriptions, loading: descriptionsLoading } = useExplorerDescriptions(editingContextId);
    const { activeTreeDescriptionId, expanded, maxDepth, onExpandedElementChange, setActiveDescriptionId } =
      useTreeStateContainer(configuredActiveTreeDescriptionId, explorerDescriptions);

    const {
      treeFilters,
      loading: treeFiltersLoading,
      setTreeFilters,
    } = useTreeFiltering(editingContextId, activeTreeDescriptionId, configuredTreeFilters);

    const activeTreeFilterIds = treeFilters?.filter((filter) => filter.state).map((filter) => filter.id) ?? [];

    const shouldStartSubscription =
      !!treeFilters && !!activeTreeDescriptionId && !descriptionsLoading && !treeFiltersLoading;

    return (
      <FilterBarContextProvider>
        <ExplorerSubscriptionContainer
          editingContextId={editingContextId}
          activeTreeDescriptionId={activeTreeDescriptionId}
          activeTreeFilterIds={activeTreeFilterIds}
          expanded={expanded}
          maxDepth={maxDepth}
          skip={!shouldStartSubscription}>
          <ExplorerSelectionContextProvider
            id={id}
            activeTreeDescriptionId={activeTreeDescriptionId}
            treeFilters={treeFilters}
            editingContextId={editingContextId}
            refHandle={ref}
            expanded={expanded}
            onExpandedElementChange={onExpandedElementChange}>
            <ViewAccordion id={id} title="Explorer">
              <ViewAccordionToolbar>
                <ExplorerToolbarRenderer
                  editingContextId={editingContextId}
                  readOnly={readOnly}
                  activeTreeDescriptionId={activeTreeDescriptionId}
                  explorerDescriptions={explorerDescriptions}
                  treeFilters={treeFilters}
                  setTreeFilters={setTreeFilters}
                  setActiveDescriptionId={setActiveDescriptionId}
                />
              </ViewAccordionToolbar>
              <ViewAccordionContent>
                <ExplorerTreeRenderer
                  editingContextId={editingContextId}
                  readOnly={readOnly}
                  expanded={expanded}
                  maxDepth={maxDepth}
                  onExpandedElementChange={onExpandedElementChange}
                />
              </ViewAccordionContent>
            </ViewAccordion>
          </ExplorerSelectionContextProvider>
        </ExplorerSubscriptionContainer>
      </FilterBarContextProvider>
    );
  }
);

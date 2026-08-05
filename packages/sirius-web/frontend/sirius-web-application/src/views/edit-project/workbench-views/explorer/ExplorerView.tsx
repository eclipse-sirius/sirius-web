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
import Box from '@mui/material/Box';
import { ForwardedRef, forwardRef, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ExplorerSelectionContextProvider } from './ExplorerSelectionContext';
import { ExplorerSubscriptionContainer } from './ExplorerSubscriptionContainer';
import { ExplorerToolbarRenderer } from './ExplorerToolbarRenderer';
import { ExplorerTreeRenderer } from './ExplorerTreeRenderer';
import { ExplorerViewConfiguration } from './ExplorerView.types';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useTreeFiltering } from './useTreeFiltering';
import { useTreeStateContainer } from './useTreeStateContainer';

const useStyles = makeStyles()(() => ({
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto minmax(0, 1fr)',
    justifyItems: 'stretch',
    overflow: 'hidden',
  },
}));

export const ExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { editingContextId, id, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const { classes: styles } = useStyles();

    const initialExplorerViewConfiguration: ExplorerViewConfiguration =
      initialConfiguration as unknown as ExplorerViewConfiguration;

    const configuredActiveTreeDescriptionId = initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null;
    const { explorerDescriptions } = useExplorerDescriptions(editingContextId);
    const { activeTreeDescriptionId, expanded, maxDepth, onExpandedElementChange, setActiveDescriptionId } =
      useTreeStateContainer(configuredActiveTreeDescriptionId, explorerDescriptions);

    const { treeFilters, setTreeFilters } = useTreeFiltering(
      editingContextId,
      activeTreeDescriptionId,
      initialExplorerViewConfiguration?.activeTreeFilters ?? []
    );

    const activeTreeFilterIds = treeFilters.filter((filter) => filter.state).map((filter) => filter.id);

    const treeElement = useRef<HTMLDivElement>(null);

    return (
      <FilterBarContextProvider containerRef={treeElement}>
        <ExplorerSubscriptionContainer
          editingContextId={editingContextId}
          activeTreeDescriptionId={activeTreeDescriptionId}
          activeTreeFilterIds={activeTreeFilterIds}
          expanded={expanded}
          maxDepth={maxDepth}>
          <ExplorerSelectionContextProvider
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
                <Box className={styles.treeView} ref={treeElement}>
                  <ExplorerTreeRenderer
                    editingContextId={editingContextId}
                    readOnly={readOnly}
                    target={treeElement?.current}
                    expanded={expanded}
                    maxDepth={maxDepth}
                    onExpandedElementChange={onExpandedElementChange}
                  />
                </Box>
              </ViewAccordionContent>
            </ViewAccordion>
          </ExplorerSelectionContextProvider>
        </ExplorerSubscriptionContainer>
      </FilterBarContextProvider>
    );
  }
);

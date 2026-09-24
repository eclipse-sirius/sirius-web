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
import { ExplorerFilterContextProvider } from './ExplorerFilterContext';
import { ExplorerInteractionContextProvider } from './ExplorerInteractionContext';
import { ExplorerSelectionContextProvider } from './ExplorerSelectionContext';
import { ExplorerSubscriptionContainer } from './ExplorerSubscriptionContainer';
import { ExplorerToolbarRenderer } from './ExplorerToolbarRenderer';
import { ExplorerTreeRenderer } from './ExplorerTreeRenderer';
import { ExplorerViewConfiguration } from './ExplorerView.types';
import { useExplorerDescriptions } from './useExplorerDescriptions';
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

    const { explorerDescriptions } = useExplorerDescriptions(editingContextId);

    const treeElement = useRef<HTMLDivElement>(null);

    return (
      <FilterBarContextProvider containerRef={treeElement}>
        <ExplorerInteractionContextProvider
          activeTreeDescriptionId={initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null}
          explorerDescriptions={explorerDescriptions}>
          <ExplorerFilterContextProvider
            editingContextId={editingContextId}
            initialTreeFilters={initialExplorerViewConfiguration?.activeTreeFilters ?? []}>
            <ExplorerSubscriptionContainer editingContextId={editingContextId}>
              <ExplorerSelectionContextProvider id={id} editingContextId={editingContextId} refHandle={ref}>
                <ViewAccordion id={id} title="Explorer">
                  <ViewAccordionToolbar>
                    <ExplorerToolbarRenderer
                      editingContextId={editingContextId}
                      readOnly={readOnly}
                      explorerDescriptions={explorerDescriptions}
                    />
                  </ViewAccordionToolbar>
                  <ViewAccordionContent>
                    <Box className={styles.treeView} ref={treeElement}>
                      <ExplorerTreeRenderer
                        editingContextId={editingContextId}
                        readOnly={readOnly}
                        target={treeElement?.current}
                      />
                    </Box>
                  </ViewAccordionContent>
                </ViewAccordion>
              </ExplorerSelectionContextProvider>
            </ExplorerSubscriptionContainer>
          </ExplorerFilterContextProvider>
        </ExplorerInteractionContextProvider>
      </FilterBarContextProvider>
    );
  }
);

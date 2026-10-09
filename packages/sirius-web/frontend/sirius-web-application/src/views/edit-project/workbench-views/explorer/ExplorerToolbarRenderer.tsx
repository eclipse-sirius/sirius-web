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
import {
  FilterBarContext,
  FilterBarContextValue,
  TreeToolBar,
  TreeToolBarContext,
  TreeToolBarContextValue,
} from '@eclipse-sirius/sirius-components-trees';
import { useContext } from 'react';
import { ExplorerContext } from './ExplorerContext';
import { ExplorerContextValue } from './ExplorerContext.types';
import { ExplorerFilterContext } from './ExplorerFilterContext';
import { ExplorerFilterContextValue } from './ExplorerFilterContext.types';
import { ExplorerInteractionContext } from './ExplorerInteractionContext';
import { ExplorerInteractionContextValue } from './ExplorerInteractionContext.types';
import { ExplorerSelectionContext } from './ExplorerSelectionContext';
import { ExplorerSelectionContextValue } from './ExplorerSelectionContext.types';
import { ExplorerToolbarRendererProps } from './ExplorerToolbarRenderer.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';

export const ExplorerToolbarRenderer = ({
  editingContextId,
  explorerDescriptions,
  readOnly,
}: ExplorerToolbarRendererProps) => {
  const { activeTreeDescriptionId, setActiveDescriptionId } =
    useContext<ExplorerInteractionContextValue>(ExplorerInteractionContext);
  const { treeFilters, setTreeFilters } = useContext<ExplorerFilterContextValue>(ExplorerFilterContext);
  const { toggleFilter } = useContext<FilterBarContextValue>(FilterBarContext);
  const { onRevealSelection } = useContext<ExplorerSelectionContextValue>(ExplorerSelectionContext);
  const { resetTree } = useContext<ExplorerContextValue>(ExplorerContext);

  const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
    (contribution) => contribution.props.component
  );

  if (!activeTreeDescriptionId) {
    return null;
  }

  const treeDescriptionSelector: JSX.Element = explorerDescriptions.length > 1 && (
    <TreeDescriptionsMenu
      treeDescriptions={explorerDescriptions}
      activeTreeDescriptionId={activeTreeDescriptionId}
      onTreeDescriptionChange={(treeDescription) => {
        setActiveDescriptionId(treeDescription.id);
        resetTree();
      }}
    />
  );

  return (
    <TreeToolBar
      editingContextId={editingContextId}
      readOnly={readOnly}
      treeFilters={treeFilters}
      onRevealSelection={onRevealSelection}
      onTreeFilterMenuItemClick={setTreeFilters}
      onFilter={toggleFilter}
      treeToolBarContributionComponents={treeToolBarContributionComponents}>
      {treeDescriptionSelector}
    </TreeToolBar>
  );
};

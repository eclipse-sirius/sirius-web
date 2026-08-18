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
import { TreeToolBar, TreeToolBarContext, TreeToolBarContextValue } from '@eclipse-sirius/sirius-components-trees';
import { useContext } from 'react';
import { ExplorerTreeToolbarRendererProps } from './ExplorerTreeToolbarRenderer.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';

export const ExplorerTreeToolbarRenderer = ({
  editingContextId,
  activeTreeDescriptionId,
  explorerDescriptions,
  readOnly,
  treeFilters,
  resetTree,
  setTreeFilters,
  setActiveDescriptionId,
  onRevealSelection,
  onFilter,
}: ExplorerTreeToolbarRendererProps) => {
  if (!activeTreeDescriptionId) {
    return null;
  }

  const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
    (contribution) => contribution.props.component
  );

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
    <>
      <TreeToolBar
        editingContextId={editingContextId}
        readOnly={readOnly}
        treeFilters={treeFilters}
        onRevealSelection={onRevealSelection}
        onTreeFilterMenuItemClick={setTreeFilters}
        onFilter={onFilter}
        treeToolBarContributionComponents={treeToolBarContributionComponents}>
        {treeDescriptionSelector}
      </TreeToolBar>
    </>
  );
};

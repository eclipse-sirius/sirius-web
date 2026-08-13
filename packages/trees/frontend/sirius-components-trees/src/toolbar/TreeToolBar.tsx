/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import React from 'react';
import { TreeFiltersMenu } from '../views/TreeFiltersMenu';
import { FilterButton } from './FilterButton';
import { RevealSelectionButton } from './RevealSelectionButton';
import { TreeToolBarProps } from './TreeToolBar.types';
import { TreeToolBarContributionComponentProps } from './TreeToolBarContribution.types';

export const TreeToolBar = ({
  editingContextId,
  treeFilters,
  onRevealSelection,
  onTreeFilterMenuItemClick,
  onFilter,
  treeToolBarContributionComponents,
  readOnly,
  children,
}: TreeToolBarProps) => {
  return (
    <>
      {treeToolBarContributionComponents.map((component, index) => {
        const props: TreeToolBarContributionComponentProps = {
          editingContextId: editingContextId,
          disabled: readOnly,
          key: index.toString(),
        };
        const element = React.createElement(component, props);
        return element;
      })}
      {treeFilters.length > 0 ? (
        <TreeFiltersMenu filters={treeFilters} onTreeFilterMenuItemClick={onTreeFilterMenuItemClick} />
      ) : null}
      {children}
      <FilterButton onClick={onFilter} />
      <RevealSelectionButton editingContextId={editingContextId} onClick={onRevealSelection} />
    </>
  );
};

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
  fuzzyMatch,
  PaletteToolOverriddenContributionComponentProps,
  ToolListItemText,
} from '@eclipse-sirius/sirius-components-palette';
import {
  GQLGetExpandAllTreePathVariables,
  TreePaletteContext,
  TreePaletteContextValue,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, Fragment, useContext, useEffect } from 'react';

export const ExpandAllToolContribution = forwardRef(
  (
    { onInvoked, searchedValue, tool }: PaletteToolOverriddenContributionComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { editingContextId, treeId, item, onExpandedElementChange, expanded, onClose } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    useEffect(() => {
      if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
        const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
          expandAllTreePathData.viewer.editingContext.expandAllTreePath;
        const idsToExpand = new Set(treeItemIdsToExpand ?? []);
        const isSearching = new URLSearchParams(treeId.split('?')[1] ?? '').has('searchedValue');
        if (isSearching) {
          // During search, the expanded prop carries the collapsed item IDs.
          const newCollapsed = expanded.filter((id) => !idsToExpand.has(id));
          onExpandedElementChange(newCollapsed, expandedMaxDepth);
        } else {
          const newExpanded = [...new Set([...expanded, ...idsToExpand])];
          onExpandedElementChange(newExpanded, expandedMaxDepth);
        }
        onClose();
      }
    }, [expandAllTreePathData]);

    const onExpandAll = () => {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId,
        treeItemId: item.id,
      };
      getExpandAllTreePath({ variables });
    };

    const handleClick = () => {
      onInvoked();
      onExpandAll();
    };

    const matchResult = searchedValue ? fuzzyMatch(tool.label, searchedValue) : null;
    if (!!searchedValue && !matchResult?.matches) {
      return null;
    }

    return (
      <Fragment key="expand-all-tree-item-context-menu-contribution">
        <MenuItem key="expand-all" data-testid="expand-all" onClick={handleClick} ref={ref}>
          <ListItemIcon>
            <UnfoldMore fontSize="small" />
          </ListItemIcon>
          <ToolListItemText label={tool.label} searchedValue={searchedValue} />
        </MenuItem>
      </Fragment>
    );
  }
);

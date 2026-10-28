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
import UnfoldLess from '@mui/icons-material/UnfoldLess';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, Fragment, useContext, useEffect } from 'react';

export const CollapseAllToolContribution = forwardRef(
  (
    { onInvoked, searchedValue, tool }: PaletteToolOverriddenContributionComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { editingContextId, treeId, item, onExpandedElementChange, expanded, onClose } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    useEffect(() => {
      if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
        const { treeItemIdsToExpand: treeItemIdsToCollapse } =
          expandAllTreePathData.viewer.editingContext.expandAllTreePath;

        const toCollapse = new Set(treeItemIdsToCollapse);
        const newExpanded = expanded.filter((id) => !toCollapse.has(id));

        onExpandedElementChange(newExpanded, 0);
        onClose();
      }
    }, [expandAllTreePathData]);

    const onCollapseAll = () => {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId,
        treeItemId: item.id,
      };
      getExpandAllTreePath({ variables });
    };

    const handleClick = () => {
      onInvoked();
      onCollapseAll();
    };

    const matchResult = searchedValue ? fuzzyMatch(tool.label, searchedValue) : null;
    if (!!searchedValue && !matchResult?.matches) {
      return null;
    }

    return (
      <Fragment key="collapse-all-tree-item-context-menu-contribution">
        <MenuItem key="collapse-all" data-testid="collapse-all" onClick={handleClick} ref={ref}>
          <ListItemIcon>
            <UnfoldLess fontSize="small" />
          </ListItemIcon>
          <ToolListItemText label={tool.label} searchedValue={searchedValue} />
        </MenuItem>
      </Fragment>
    );
  }
);

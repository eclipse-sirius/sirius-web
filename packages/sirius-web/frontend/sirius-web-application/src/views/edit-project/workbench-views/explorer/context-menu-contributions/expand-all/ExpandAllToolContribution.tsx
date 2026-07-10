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
  ({ onInvoked, tool }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { editingContextId, treeId, item, onExpandedElementChange, expanded, onClose } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    useEffect(() => {
      if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
        const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
          expandAllTreePathData.viewer.editingContext.expandAllTreePath;
        const newExpanded: string[] = [...expanded];

        treeItemIdsToExpand?.forEach((itemToExpand) => {
          if (!expanded.includes(itemToExpand)) {
            newExpanded.push(itemToExpand);
          }
        });
        onExpandedElementChange(newExpanded, expandedMaxDepth);
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

    return (
      <Fragment key="expand-all-tree-item-context-menu-contribution">
        <MenuItem key="expand-all" data-testid="expand-all" onClick={handleClick} ref={ref}>
          <ListItemIcon>
            <UnfoldMore fontSize="small" />
          </ListItemIcon>
          <ToolListItemText label={tool.label} searchedValue={null} />
        </MenuItem>
      </Fragment>
    );
  }
);

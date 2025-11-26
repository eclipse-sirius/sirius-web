/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreeItemContextMenuComponentProps,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useTranslation } from 'react-i18next';
import { Fragment, forwardRef, useEffect } from 'react';

export const ExpandAllTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, treeId, item, onExpandedElementChange, expanded, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'expandAllTreeItemContextMenuContribution' });
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

    const onExpandAll = (treeItem: GQLTreeItem) => {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId,
        treeItemId: treeItem.id,
      };
      getExpandAllTreePath({ variables });
    };

    return (
      <Fragment key="expand-all-tree-item-context-menu-contribution">
        <MenuItem
          key="expand-all"
          data-testid="expand-all"
          onClick={() => {
            onExpandAll(item);
          }}
          ref={ref}
          aria-disabled>
          <ListItemIcon>
            <UnfoldMore fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('expandAll')} />
        </MenuItem>
      </Fragment>
    );
  }
);

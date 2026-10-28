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
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreeItemContextMenuComponentProps,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldLess from '@mui/icons-material/UnfoldLess';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

export const CollapseAllTreeItemContextMenuContribution = forwardRef(
  (
    {
      editingContextId,
      treeId,
      item,
      onExpandedElementChange,
      expanded,
      maxDepth,
      onClose,
    }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'collapseAllTreeItemContextMenuContribution' });
    useEffect(() => {
      if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
        const { treeItemIdsToExpand: treeItemIdsToCollapse } =
          expandAllTreePathData.viewer.editingContext.expandAllTreePath;

        const toCollapse = new Set(treeItemIdsToCollapse);
        const newExpanded = expanded.filter((id) => !toCollapse.has(id));

        onExpandedElementChange(newExpanded, maxDepth);
        onClose();
      }
    }, [expandAllTreePathData]);

    const onCollapseAll = (treeItem: GQLTreeItem) => {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId,
        treeItemId: treeItem.id,
      };
      getExpandAllTreePath({ variables });
    };

    return (
      <Fragment key="collapse-all-tree-item-context-menu-contribution">
        <MenuItem
          key="collapse-all"
          data-testid="collapse-all"
          onClick={() => {
            onCollapseAll(item);
          }}
          ref={ref}>
          <ListItemIcon>
            <UnfoldLess fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('collapseAll')} />
        </MenuItem>
      </Fragment>
    );
  }
);

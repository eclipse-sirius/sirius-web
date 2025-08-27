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
import { PaletteToolOverriddenContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import {
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreePaletteContext,
  TreePaletteContextValue,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useContext, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

export const ExpandAllToolContribution = forwardRef(
  ({}: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'expandAllTreeItemContextMenuContribution' });
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

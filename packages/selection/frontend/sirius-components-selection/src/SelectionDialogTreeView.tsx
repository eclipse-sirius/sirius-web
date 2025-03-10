/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { DiagramDialogVariable } from '@eclipse-sirius/sirius-components-diagrams';
import {
  GQLGetExpandAllTreePathVariables,
  GQLTreeItem,
  TreeItemActionProps,
  TreeView,
  useExpandAllTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import IconButton from '@mui/material/IconButton';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SelectionDialogTreeViewProps, SelectionDialogTreeViewState } from './SelectionDialogTreeView.types';
import { useSelectionDialogTreeSubscription } from './useSelectionDialogTreeSubscription';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

const useTreeStyle = makeStyles()((theme) => ({
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.divider,
    height: 600,
    overflow: 'auto',
  },
}));

const initialState: SelectionDialogTreeViewState = {
  expanded: [],
  maxDepth: 1,
};

export const SelectionDialogTreeView = ({
  editingContextId,
  treeDescriptionId,
  variables,
  onTreeItemClick,
  selectedTreeItemIds,
}: SelectionDialogTreeViewProps) => {
  const { classes } = useTreeStyle();
  const [state, setState] = useState<SelectionDialogTreeViewState>(initialState);

  const treeId = `selection://?treeDescriptionId=${encodeURIComponent(treeDescriptionId)}${encodeVariables(variables)}`;
  const { tree } = useSelectionDialogTreeSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const { getExpandAllTreePath, data: expandAllTreePathData } = useExpandAllTreePath();

  useEffect(() => {
    if (expandAllTreePathData && expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
      const { expanded, maxDepth } = state;
      const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
        expandAllTreePathData.viewer.editingContext.expandAllTreePath;
      const newExpanded: string[] = [...expanded];

      treeItemIdsToExpand?.forEach((itemToExpand) => {
        if (!expanded.includes(itemToExpand)) {
          newExpanded.push(itemToExpand);
        }
      });
      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(expandedMaxDepth, maxDepth),
      }));
    }
  }, [expandAllTreePathData]);

  const onExpand = (id: string, depth: number) => {
    const { expanded, maxDepth } = state;

    if (expanded.includes(id)) {
      const newExpanded = [...expanded];
      newExpanded.splice(newExpanded.indexOf(id), 1);

      setState((prevState) => ({
        ...prevState,
        expanded: newExpanded,
        maxDepth: Math.max(maxDepth, depth),
      }));
    } else {
      setState((prevState) => ({ ...prevState, expanded: [...expanded, id], maxDepth: Math.max(maxDepth, depth) }));
    }
  };

  const onExpandAll = (treeItem: GQLTreeItem) => {
    if (tree) {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        treeItemId: treeItem.id,
      };
      getExpandAllTreePath({ variables });
    }
  };

  return (
    <div className={classes.borderStyle}>
      {tree ? (
        <TreeView
          editingContextId={editingContextId}
          readOnly={true}
          treeId={treeId}
          tree={tree}
          textToFilter={''}
          textToHighlight={''}
          treeItemActionRender={(props) => <SelectionDialogTreeItemAction {...props} />}
          onExpand={onExpand}
          onExpandAll={onExpandAll}
          onTreeItemClick={onTreeItemClick}
          selectedTreeItemIds={selectedTreeItemIds}
        />
      ) : null}
    </div>
  );
};

const SelectionDialogTreeItemAction = ({ onExpandAll, item, isHovered }: TreeItemActionProps) => {
  if (!onExpandAll || !item || !item.hasChildren || !isHovered) {
    return null;
  }
  return (
    <IconButton
      size="small"
      data-testid="expand-all"
      title="expand all"
      onClick={() => {
        onExpandAll(item);
      }}>
      <UnfoldMoreIcon style={{ fontSize: 12 }} />
    </IconButton>
  );
};

const encodeVariables = (variables: DiagramDialogVariable[]): string => {
  let encodedVariables = '';
  if (variables.length > 0) {
    encodedVariables =
      '&' + variables.map((variable) => variable.name + '=' + encodeURIComponent(variable.value)).join('&');
  }
  return encodedVariables;
};

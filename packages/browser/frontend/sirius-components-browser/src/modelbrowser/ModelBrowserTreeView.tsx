/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { FilterBar } from '@eclipse-sirius/sirius-components-core';
import { TreeItemActionProps, TreeView } from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import IconButton from '@mui/material/IconButton';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ModelBrowserTreeViewProps, ModelBrowserTreeViewState } from './ModelBrowserTreeView.types';
import { useModelBrowserSubscription } from './useModelBrowserSubscription';

const useTreeStyle = makeStyles()((theme) => ({
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
    overflow: 'auto',
  },
}));

export const ModelBrowserTreeView = ({
  editingContextId,
  referenceKind,
  ownerId,
  descriptionId,
  isContainment,
  markedItemIds,
  enableMultiSelection,
  title,
  leafType,
  ownerKind,
}: ModelBrowserTreeViewProps) => {
  const { classes } = useTreeStyle();

  const [state, setState] = useState<ModelBrowserTreeViewState>({
    filterBarText: '',
    expanded: [],
    maxDepth: 1,
  });

  const treeId: string = `modelBrowser://${leafType}?ownerKind=${encodeURIComponent(
    ownerKind
  )}&targetType=${encodeURIComponent(referenceKind)}&ownerId=${ownerId}&descriptionId=${encodeURIComponent(
    descriptionId
  )}&isContainment=${isContainment}`;
  const { tree } = useModelBrowserSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const onExpandedElementChange = (expanded: string[], maxDepth: number) => {
    setState((prevState) => ({ ...prevState, expanded, maxDepth }));
  };

  return (
    <>
      <FilterBar
        onTextChange={(event) => setState((prevState) => ({ ...prevState, filterBarText: event.target.value }))}
        onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
        text={state.filterBarText}
      />
      <span className={classes.title}>{title}</span>
      <div className={classes.borderStyle}>
        {tree !== null ? (
          <TreeView
            editingContextId={editingContextId}
            readOnly={true}
            treeId={treeId}
            tree={tree}
            enableMultiSelection={enableMultiSelection}
            synchronizedWithSelection={true}
            textToFilter={state.filterBarText}
            textToHighlight={state.filterBarText}
            markedItemIds={markedItemIds}
            treeItemActionRender={(props) => <ExpandAllTreeItemAction {...props} />}
            onExpandedElementChange={onExpandedElementChange}
            expanded={state.expanded}
            maxDepth={state.maxDepth}
          />
        ) : null}
      </div>
    </>
  );
};

const ExpandAllTreeItemAction = ({ onExpandAll, item, isHovered }: TreeItemActionProps) => {
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

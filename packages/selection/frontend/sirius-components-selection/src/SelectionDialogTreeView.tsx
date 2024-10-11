/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { TreeItemActionProps, TreeView } from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import IconButton from '@mui/material/IconButton';
import { useState } from 'react';
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
}: SelectionDialogTreeViewProps) => {
  const { classes } = useTreeStyle();
  const [state, setState] = useState<SelectionDialogTreeViewState>(initialState);

  const treeId = `selection://?treeDescriptionId=${encodeURIComponent(treeDescriptionId)}${encodeVariables(variables)}`;
  const { tree } = useSelectionDialogTreeSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

  const onExpandedElementChange = (expanded: string[], maxDepth: number) => {
    setState((prevState) => ({ ...prevState, expanded, maxDepth }));
  };

  return (
    <div className={classes.borderStyle}>
      {tree ? (
        <TreeView
          editingContextId={editingContextId}
          readOnly={true}
          treeId={treeId}
          enableMultiSelection={false}
          synchronizedWithSelection={true}
          tree={tree}
          textToFilter={''}
          textToHighlight={''}
          treeItemActionRender={(props) => <SelectionDialogTreeItemAction {...props} />}
          onExpandedElementChange={onExpandedElementChange}
          expanded={state.expanded}
          maxDepth={state.maxDepth}
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

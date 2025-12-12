/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { DiagramDialogComponentProps, GQLToolVariable } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLTree, GQLTreeItem, useTreeSelection } from '@eclipse-sirius/sirius-components-trees';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';
import { SelectionDialogState } from './SelectionDialog.types';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';
import { useSelectionDescription } from './useSelectionDescription';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  variables,
  onClose,
  onFinish,
}: DiagramDialogComponentProps) => {
  const [state, setState] = useState<SelectionDialogState>({
    selectedObjectIds: [],
  });

  const { selectionDescription } = useSelectionDescription({
    editingContextId,
    selectionDescriptionId: dialogDescriptionId,
    variables,
  });

  const { treeItemClick } = useTreeSelection();

  const message: string = selectionDescription?.message ?? '';
  const treeDescriptionId: string | null = selectionDescription?.treeDescription.id ?? null;
  const multiple: boolean = selectionDescription?.multiple ?? false;

  const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
    var newSelection = treeItemClick(event, tree, item, state.selectedObjectIds, multiple);
    setState((prevState) => ({
      ...prevState,
      selectedObjectIds: newSelection.selectedTreeItemIds,
      singleTreeItemSelected: newSelection.singleTreeItemSelected,
    }));
  };

  const handleClick = () => {
    let variables: GQLToolVariable[] = [];
    if (state.selectedObjectIds.length > 0) {
      if (multiple) {
        variables = [{ name: 'selectedObjects', value: state.selectedObjectIds.join(','), type: 'OBJECT_ID_ARRAY' }];
      } else {
        const selectedObjectId = state.selectedObjectIds[0] ?? '';
        variables = [{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }];
      }
      onFinish(variables);
    }
  };

  let content: JSX.Element | null = null;
  if (treeDescriptionId !== null) {
    content = (
      <SelectionDialogTreeView
        editingContextId={editingContextId}
        variables={variables}
        treeDescriptionId={treeDescriptionId}
        onTreeItemClick={onTreeItemClick}
        selectedTreeItemIds={state.selectedObjectIds}
      />
    );
  }

  return (
    <Dialog
      open
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="md"
      fullWidth
      data-testid="selection-dialog">
      <DialogTitle id="selection-dialog-title">Selection Dialog</DialogTitle>
      <DialogContent>
        <DialogContentText data-testid="selection-dialog-message">{message}</DialogContentText>
        {content}
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.selectedObjectIds.length == 0}
          data-testid="finish-action"
          color="primary"
          onClick={handleClick}>
          Finish
        </Button>
      </DialogActions>
    </Dialog>
  );
};

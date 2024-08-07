/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { DiagramDialogComponentProps } from '@eclipse-sirius/sirius-components-diagrams';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SelectionDialogListView } from './SelectionDialogListView';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';

import {
  SchemaValue,
  SelectionDialogContext,
  SelectionDialogEvent,
  selectionDialogMachine,
} from './SelectionDialogMachine';

const useSelectionObjectModalStyles = makeStyles()((_theme) => ({
  root: {
    width: '100%',
    position: 'relative',
    overflow: 'auto',
    maxHeight: 300,
  },
}));

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  targetObjectId,
  onClose,
  onFinish,
}: DiagramDialogComponentProps) => {
  const [{ value, context }] = useMachine<SelectionDialogContext, SelectionDialogEvent>(selectionDialogMachine);
  const { selectionDialog } = value as SchemaValue;
  const { selection, selectedObjectId } = context;

  useEffect(() => {
    if (selectionDialog === 'complete') {
      onClose();
    }
  }, [selectionDialog, onClose]);

  let content: JSX.Element;

  if (selection?.displayedAsTree) {
    content = <SelectionDialogTreeView />;
  } else {
    content = (
      <SelectionDialogListView
        editingContextId={editingContextId}
        onClose={onClose}
        targetObjectId={targetObjectId}
        selectionRepresentationId={dialogDescriptionId}
      />
    );
  }
  return (
    <Dialog
      open
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="xs"
      fullWidth
      data-testid="selection-dialog">
      <DialogTitle id="selection-dialog-title">Selection Dialog</DialogTitle>
      <DialogContent>
        <DialogContentText data-testid="selection-dialog-message">{selection?.message}</DialogContentText>
        {content}
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={selectedObjectId === null}
          data-testid="finish-action"
          color="primary"
          onClick={() => {
            if (selectedObjectId) {
              onFinish([{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }]);
            }
          }}>
          Finish
        </Button>
      </DialogActions>
    </Dialog>
  );
};

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
import { SelectionDialogListView } from './SelectionDialogListView';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';

import { gql } from '@apollo/client';
import { useSubscription } from '@apollo/client/react/hooks/useSubscription';
import { Selection, SelectionContext, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  HandleCompleteEvent,
  HandleSelectionUpdatedEvent,
  HandleSubscriptionResultEvent,
  SchemaValue,
  SelectionDialogContext,
  SelectionDialogEvent,
  selectionDialogMachine,
} from './SelectionDialogMachine';
import { GQLSelectionEventSubscription } from './SelectionEvent.types';

const selectionEventSubscription = gql`
  subscription selectionEvent($input: SelectionEventInput!) {
    selectionEvent(input: $input) {
      __typename
      ... on SelectionRefreshedEventPayload {
        selection {
          id
          targetObjectId
          message
          displayedAsTree
          objects {
            id
            label
            iconURL
            isSelectable
            parentId
          }
        }
      }
    }
  }
`;

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  targetObjectId,
  onClose,
  onFinish,
}: DiagramDialogComponentProps) => {
  const [{ value, context }, dispatch] = useMachine<SelectionDialogContext, SelectionDialogEvent>(
    selectionDialogMachine
  );
  const { selectionDialog } = value as SchemaValue;
  const { selection, id, selectedObjects } = context;
  const { addErrorMessage } = useMultiToast();

  const setDialogSelection = (selectedObjects: Selection) => {
    dispatch({ type: 'HANDLE_SELECTION_UPDATED', selectedObjects } as HandleSelectionUpdatedEvent);
  };

  const { error } = useSubscription<GQLSelectionEventSubscription>(selectionEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        selectionId: dialogDescriptionId,
        targetObjectId,
      },
    },
    fetchPolicy: 'no-cache',
    skip: selectionDialog === 'complete',
    onData: ({ data }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: data,
      };
      dispatch(handleDataEvent);
    },
    onComplete: () => {
      const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error, addErrorMessage]);

  useEffect(() => {
    if (selectionDialog === 'complete') {
      onClose();
    }
  }, [selectionDialog, onClose]);

  let content: JSX.Element | null = null;

  if (selection?.displayedAsTree) {
    content = (
      <SelectionDialogTreeView
        editingContextId={editingContextId}
        descriptionId={dialogDescriptionId}
        targetObjectId={targetObjectId}
      />
    );
  } else if (selection) {
    content = <SelectionDialogListView selection={selection} />;
  }
  return (
    <SelectionContext.Provider value={{ selection: selectedObjects, setSelection: setDialogSelection }}>
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
            disabled={selectedObjects.entries.length == 0}
            data-testid="finish-action"
            color="primary"
            onClick={() => {
              if (selectedObjects.entries.length > 0) {
                var selectedObjectId = selectedObjects.entries.map((entry) => entry.id)[0] ?? '';
                onFinish([{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }]);
              }
            }}>
            Finish
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};

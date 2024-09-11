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
import { gql, useSubscription } from '@apollo/client';
import { IconOverlay, Toast } from '@eclipse-sirius/sirius-components-core';
import { DiagramDialogComponentProps } from '@eclipse-sirius/sirius-components-diagrams';
import CropDinIcon from '@mui/icons-material/CropDin';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import {
  HandleCompleteEvent,
  HandleSelectionUpdatedEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  SelectionDialogContext,
  SelectionDialogEvent,
  SelectionDialogStateSchema,
  ShowToastEvent,
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
          objects {
            id
            label
            iconURL
          }
        }
      }
    }
  }
`;

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
  const { classes } = useSelectionObjectModalStyles();

  const [{ value, context }, dispatch] =
    useMachine<StateMachine<SelectionDialogContext, SelectionDialogStateSchema, SelectionDialogEvent>>(
      selectionDialogMachine
    );
  const { toast, selectionDialog } = value as SchemaValue;
  const { id, selection, message, selectedObjectId } = context;

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
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  useEffect(() => {
    if (selectionDialog === 'complete') {
      onClose();
    }
  }, [selectionDialog, onClose]);

  const handleListItemClick = (selectedObjectId: string) => {
    dispatch({ type: 'HANDLE_SELECTION_UPDATED', selectedObjectId } as HandleSelectionUpdatedEvent);
  };

  return (
    <>
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
          <List className={classes.root}>
            {selection?.objects.map((selectionObject) => (
              <ListItem
                button
                key={`item-${selectionObject.id}`}
                selected={selectedObjectId === selectionObject.id}
                onClick={() => handleListItemClick(selectionObject.id)}
                data-testid={selectionObject.label}>
                <ListItemIcon>
                  {selectionObject.iconURL.length > 0 ? (
                    <IconOverlay
                      iconURL={selectionObject.iconURL}
                      alt={selectionObject.label}
                      customIconHeight={24}
                      customIconWidth={24}
                    />
                  ) : (
                    <CropDinIcon style={{ fontSize: 24 }} />
                  )}
                </ListItemIcon>
                <ListItemText primary={selectionObject.label} />
              </ListItem>
            ))}
          </List>
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
      <Toast
        message={message ?? ''}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};

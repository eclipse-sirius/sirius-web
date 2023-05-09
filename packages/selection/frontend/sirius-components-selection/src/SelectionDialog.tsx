/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { ServerContext, Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { createStyles, makeStyles } from '@material-ui/core/styles';
import CropDinIcon from '@material-ui/icons/CropDin';
import { useMachine } from '@xstate/react';
import { useContext, useEffect } from 'react';
import { SelectionDialogProps } from './SelectionDialog.types';
import {
  HandleCompleteEvent,
  HandleSelectionUpdatedEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  SelectionDialogContext,
  SelectionDialogEvent,
  selectionDialogMachine,
  ShowToastEvent,
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

const useSelectionObjectModalStyles = makeStyles((theme) =>
  createStyles({
    root: {
      width: '100%',
      position: 'relative',
      overflow: 'auto',
      maxHeight: 300,
    },
  })
);

export const SelectionDialog = ({
  editingContextId,
  selectionRepresentationId,
  targetObjectId,
  onClose,
  onFinish,
}: SelectionDialogProps) => {
  const classes = useSelectionObjectModalStyles();
  const { httpOrigin } = useContext(ServerContext);

  const [{ value, context }, dispatch] = useMachine<SelectionDialogContext, SelectionDialogEvent>(
    selectionDialogMachine
  );

  const { toast, selectionDialog } = value as SchemaValue;
  const { id, selection, message, selectedObjectId } = context;

  const { error } = useSubscription<GQLSelectionEventSubscription>(selectionEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        selectionId: selectionRepresentationId,
        targetObjectId: targetObjectId,
      },
    },
    fetchPolicy: 'no-cache',
    skip: selectionDialog === 'complete',
    onSubscriptionData: ({ subscriptionData }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: subscriptionData,
      };
      dispatch(handleDataEvent);
    },
    onSubscriptionComplete: () => {
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
                  {selectionObject.iconURL ? (
                    <img
                      height="24"
                      width="24"
                      alt={selectionObject.label}
                      src={httpOrigin + selectionObject.iconURL}
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
              onFinish(selectedObjectId);
            }}>
            Finish
          </Button>
        </DialogActions>
      </Dialog>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};

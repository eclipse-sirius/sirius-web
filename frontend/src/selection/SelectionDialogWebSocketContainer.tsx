/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { useSubscription } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Snackbar from '@material-ui/core/Snackbar';
import { createStyles, makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { httpOrigin } from 'common/URL';
import gql from 'graphql-tag';
import { NoIcon } from 'icons';
import React, { useEffect } from 'react';
import { SelectionDialogWebSocketContainerProps } from './SelectionDialogWebSocketContainer.types';
import {
  HandleCompleteEvent,
  HandleSelectionUpdatedEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  SelectionDialogWebSocketContainerContext,
  SelectionDialogWebSocketContainerEvent,
  selectionDialogWebSocketContainerMachine,
  ShowToastEvent,
} from './SelectionDialogWebSocketContainerMachine';
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

/**
 * Connect the SelectionDialog component to the GraphQL API over Web Socket.
 */
export const SelectionDialogWebSocketContainer = ({
  editingContextId,
  selectionRepresentationId,
  targetObjectId,
  onClose,
  onFinish,
}: SelectionDialogWebSocketContainerProps) => {
  const classes = useSelectionObjectModalStyles();

  const [{ value, context }, dispatch] = useMachine<
    SelectionDialogWebSocketContainerContext,
    SelectionDialogWebSocketContainerEvent
  >(selectionDialogWebSocketContainerMachine);

  const { toast, selectionDialogWebSocketContainer } = value as SchemaValue;
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
    skip: selectionDialogWebSocketContainer === 'complete',
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
    if (selectionDialogWebSocketContainer === 'complete') {
      onClose();
    }
  }, [selectionDialogWebSocketContainer, onClose]);

  const handleListItemClick = (selectedObjectId: string) => {
    dispatch({ type: 'HANDLE_SELECTION_UPDATED', selectedObjectId } as HandleSelectionUpdatedEvent);
  };

  return (
    <>
      <Dialog open onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="selection-dialog-title">Selection Dialog</DialogTitle>
        <DialogContent>
          <DialogContentText>{selection?.message}</DialogContentText>
          <List className={classes.root}>
            {selection?.objects.map((selectionObject) => (
              <ListItem
                button
                key={`item-${selectionObject.id}`}
                selected={selectedObjectId === selectionObject.id}
                onClick={() => handleListItemClick(selectionObject.id)}>
                <ListItemIcon>
                  {selectionObject.iconURL ? (
                    <img
                      height="24"
                      width="24"
                      alt={selectionObject.label}
                      src={httpOrigin + selectionObject.iconURL}
                    />
                  ) : (
                    <NoIcon title={selectionObject.label} />
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
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};

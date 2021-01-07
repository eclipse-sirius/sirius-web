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
import { useMutation } from '@apollo/client';
import { Snackbar } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { IconButton } from 'core/button/Button';
import gql from 'graphql-tag';
import PropTypes from 'prop-types';
import React, { useEffect } from 'react';
import {
  HandleChangedNameEvent,
  HandleRenameModelerEvent,
  HandleResponseEvent,
  HideToastEvent,
  RenameModelerEvent,
  RenameModelerModalContext,
  renameModelerModalMachine,
  SchemaValue,
  ShowToastEvent,
} from './RenameModelerModalMachine';

const renameModelerMutation = gql`
  mutation renameModeler($input: RenameModelerInput!) {
    renameModeler(input: $input) {
      __typename
      ... on RenameModelerSuccessPayload {
        modeler {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const propTypes = {
  modelerId: PropTypes.string.isRequired,
  initialModelerName: PropTypes.string.isRequired,
  onModelerRenamed: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const RenameModelerModal = ({ modelerId, initialModelerName, onModelerRenamed, onClose }) => {
  const [{ value, context }, dispatch] = useMachine<RenameModelerModalContext, RenameModelerEvent>(
    renameModelerModalMachine,
    {
      context: {
        name: initialModelerName,
      },
    }
  );
  const { renameModelerModal, toast } = value as SchemaValue;
  const { name, nameIsInvalid, message } = context;

  const onNewName = (event) => {
    const newName = event.target.value;
    dispatch({ type: 'HANDLE_CHANGED_NAME', name: newName } as HandleChangedNameEvent);
  };

  const onRenameModeler = (event) => {
    event.preventDefault();
    dispatch({ type: 'HANDLE_RENAME_MODELER' } as HandleRenameModelerEvent);
    const input = {
      modelerId: modelerId,
      newName: name,
    };
    renameModeler({ variables: { input } });
  };

  const [renameModeler, { loading, data, error }] = useMutation(renameModelerMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: error.message,
        };
        dispatch(showToastEvent);
      } else if (data?.renameModeler) {
        const { renameModeler } = data;
        if (renameModeler.__typename === 'RenameModelerSuccessPayload') {
          const successEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: renameModeler };
          dispatch(successEvent);
          onModelerRenamed();
        } else if (renameModeler.__typename === 'ErrorPayload') {
          const { message } = renameModeler;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, onModelerRenamed, dispatch]);

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="dialog-title">Rename the modeler</DialogTitle>
      <DialogContent>
        <DialogContentText></DialogContentText>
        <TextField
          autoFocus
          error={nameIsInvalid}
          helperText="The name must contain between 3 and 20 characters"
          label="Name"
          placeholder="Enter the new modeler name"
          value={name}
          onChange={onNewName}
          data-testid="name"
          fullWidth
        />
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
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={nameIsInvalid || renameModelerModal === 'renamingModeler'}
          onClick={onRenameModeler}
          color="primary"
          data-testid="rename-modeler">
          Rename
        </Button>
      </DialogActions>
    </Dialog>
  );
};
RenameModelerModal.propTypes = propTypes;

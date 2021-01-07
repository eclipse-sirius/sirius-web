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
import { Checkbox, Snackbar } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import PropTypes from 'prop-types';
import React, { useEffect } from 'react';
import {
  ConfirmPublicationEvent,
  HandleResponseEvent,
  HideToastEvent,
  PublishModelerEvent,
  PublishModelerModalContext,
  PublishModelerModalEvent,
  publishModelerModalMachine,
  SchemaValue,
  ShowToastEvent,
  UnconfirmPublicationEvent,
} from './PublishModelerModalMachine';

const publishModelerMutation = gql`
  mutation publishModeler($input: PublishModelerInput!) {
    publishModeler(input: $input) {
      __typename
      ... on PublishModelerSuccessPayload {
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
  onModelerPublished: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const PublishModelerModal = ({ modelerId, onModelerPublished, onClose }) => {
  const [{ value, context }, dispatch] = useMachine<PublishModelerModalContext, PublishModelerModalEvent>(
    publishModelerModalMachine
  );
  const { publishModelerModal } = value as SchemaValue;
  const { publicationError } = context;

  const onToggleConfirmation = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.checked) {
      dispatch({ type: 'CONFIRM' } as ConfirmPublicationEvent);
    } else {
      dispatch({ type: 'UNCONFIRM' } as UnconfirmPublicationEvent);
    }
  };

  const [publishModeler, { loading, data, error }] = useMutation(publishModelerMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: error.message,
        };
        dispatch(showToastEvent);
      } else if (data?.publishModeler) {
        const { publishModeler } = data;
        if (publishModeler.__typename === 'PublishModelerSuccessPayload') {
          const successEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: publishModeler };
          dispatch(successEvent);
          onModelerPublished();
        } else if (publishModeler.__typename === 'ErrorPayload') {
          const { message } = publishModeler;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, onModelerPublished, dispatch]);

  const onPublishModeler = (event) => {
    event.preventDefault();
    dispatch({ type: 'PUBLISH' } as PublishModelerEvent);
    const input = {
      modelerId: modelerId,
    };
    publishModeler({ variables: { input } });
  };

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="dialog-title">Publish the modeler</DialogTitle>
      <DialogContent>
        <DialogContentText>
          The new version will automatically apply to all user data which uses this modeler.
          <br /> Are you sure?
        </DialogContentText>
        <FormGroup row>
          <FormControlLabel
            control={
              <Checkbox
                checked={publishModelerModal === 'confirmed' || publishModelerModal === 'publishingModeler'}
                onChange={onToggleConfirmation}
                name="publication-confirmed"
              />
            }
            label="Yes I am sure"
          />
        </FormGroup>
        <Snackbar
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'right',
          }}
          open={publicationError !== null}
          autoHideDuration={3000}
          onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          message={publicationError}
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
          disabled={publishModelerModal !== 'confirmed'}
          onClick={onPublishModeler}
          color="primary"
          data-testid="rename-modeler">
          Publish
        </Button>
      </DialogActions>
    </Dialog>
  );
};
PublishModelerModal.propTypes = propTypes;

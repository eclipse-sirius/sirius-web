/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import EditNoteIcon from '@mui/icons-material/EditNote';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import React, { forwardRef, Fragment, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata';
import {
  CreateForkedStudioPayload,
  CreateProjectSuccessPayload,
  ForkTreeItemContextMenuContributionState,
  GQLCreateForkedStudioInput,
  GQLCreateForkedStudioMutationData,
  GQLErrorPayload,
} from './ForkTreeItemContextMenuContribution.types';

const forkViewMutation = gql`
  mutation createForkedStudio($input: CreateForkedStudioInput!) {
    createForkedStudio(input: $input) {
      __typename
      ... on CreateProjectSuccessPayload {
        project {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: CreateForkedStudioPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: CreateForkedStudioPayload): payload is CreateProjectSuccessPayload =>
  payload.__typename === 'CreateProjectSuccessPayload';

export const ForkTreeItemContextMenuContribution = forwardRef(
  ({ editingContextId, item, readOnly }: TreeItemContextMenuComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const [createProject, { data, error }] = useMutation<GQLCreateForkedStudioMutationData>(forkViewMutation);
    const { addErrorMessage } = useMultiToast();
    const navigate = useNavigate();

    const [state, setState] = useState<ForkTreeItemContextMenuContributionState>({
      isOpen: false,
    });

    const representationIds = item.kind.startsWith('siriusComponents://representation') ? [item.id] : [];
    const { data: representationMetaData } = useRepresentationMetadata({ editingContextId, representationIds });

    const handleClickOpen = () => setState((prevState) => ({ ...prevState, isOpen: true }));

    const handleClose = () => setState((prevState) => ({ ...prevState, isOpen: false }));

    const forkViewMutationHandler = () => {
      const input: GQLCreateForkedStudioInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: item.id,
        tableId: '',
      };
      createProject({ variables: { input } });
    };

    useEffect(() => {
      if (data) {
        if (isErrorPayload(data.createForkedStudio)) {
          addErrorMessage(data.createForkedStudio.message);
        }
        if (isSuccessPayload(data.createForkedStudio)) {
          navigate(`/projects/${data.createForkedStudio.project.id}/edit`);
          navigate(0);
        }
      }

      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
    }, [data, error]);

    if (!representationMetaData) {
      return null;
    } else {
      if (representationMetaData.viewer.editingContext.representations.edges.find((edge) => !edge.node.isViewBased)) {
        return null;
      }
    }

    return (
      <Fragment key="fork-tree-item-context-menu-contribution">
        <MenuItem onClick={handleClickOpen} ref={ref} disabled={readOnly} key="new-fork" data-testid="fork-action">
          <ListItemIcon>
            <EditNoteIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText>Fork View Model</ListItemText>
        </MenuItem>
        {state.isOpen ? (
          <Dialog
            open
            onClose={handleClose}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description">
            <DialogTitle id="alert-dialog-title">{'Switch to a new view model ?'}</DialogTitle>
            <DialogContent>
              <DialogContentText id="alert-dialog-description">
                You're about to create a new view model based on the one currently used by the representation. The
                current representation will use this new view model and you'll be redirected to a new studio.
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose} data-testid="fork-action-close">
                Cancel
              </Button>
              <Button onClick={forkViewMutationHandler} data-testid="fork-action-open">
                Redirect to new studio
              </Button>
            </DialogActions>
          </Dialog>
        ) : null}
      </Fragment>
    );
  }
);

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
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import CropDinIcon from '@mui/icons-material/CropDin';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';

import { SelectionDialogListViewProps } from './SelectionDialogListView.types';
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
          expendedAtOpening
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

const useSelectionObjectModalStyles = makeStyles()((_theme) => ({
  root: {
    width: '100%',
    position: 'relative',
    overflow: 'auto',
    maxHeight: 300,
  },
}));

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

export const SelectionDialogListView = ({
  editingContextId,
  selectionRepresentationId,
  targetObjectId,
  onClose,
}: SelectionDialogListViewProps) => {
  const { addErrorMessage } = useMultiToast();

  const { classes } = useSelectionObjectModalStyles();

  const [{ value, context }, dispatch] = useMachine<SelectionDialogContext, SelectionDialogEvent>(
    selectionDialogMachine
  );
  const { selectionDialog } = value as SchemaValue;
  const { id, selection, selectedObjectId } = context;

  const { error } = useSubscription<GQLSelectionEventSubscription>(selectionEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        selectionId: selectionRepresentationId,
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

  const handleListItemClick = (selectedObjectId: string) => {
    dispatch({ type: 'HANDLE_SELECTION_UPDATED', selectedObjectId } as HandleSelectionUpdatedEvent);
  };

  return (
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
  );
};

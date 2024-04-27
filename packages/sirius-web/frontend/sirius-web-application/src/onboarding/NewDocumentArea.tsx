/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { Toast } from '@eclipse-sirius/sirius-components-core';
import NoteAdd from '@mui/icons-material/NoteAdd';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { useEffect, useState } from 'react';
import {
  GQLErrorPayload,
  GQLInvokeEditingContextActionData,
  GQLInvokeEditingContextActionInput,
  GQLInvokeEditingContextActionVariables,
  NewDocumentAreaProps,
  NewDocumentAreaState,
} from './NewDocumentArea.types';

const useNewDocumentAreaStyles = makeStyles()((theme) => ({
  cardContent: {
    overflowY: 'auto',
    maxHeight: theme.spacing(50),
  },
  item: {
    padding: 0,
  },
}));

const invokeEditingContextActionMutation = gql`
  mutation invokeEditingContextAction($input: InvokeEditingContextActionInput!) {
    invokeEditingContextAction(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const NewDocumentArea = ({ editingContextId, editingContextActions, readOnly }: NewDocumentAreaProps) => {
  const { classes } = useNewDocumentAreaStyles();
  const [state, setState] = useState<NewDocumentAreaState>({
    message: null,
  });

  // EditingContext Action invocation
  const [
    invokeEditingContextAction,
    { loading: loadingEditingContextAction, data: dataEditingContextAction, error: errorEditingContextAction },
  ] = useMutation<GQLInvokeEditingContextActionData, GQLInvokeEditingContextActionVariables>(
    invokeEditingContextActionMutation
  );

  const onInvokeEditingContextAction = (actionId: string) => {
    const input: GQLInvokeEditingContextActionInput = {
      id: crypto.randomUUID(),
      editingContextId,
      actionId,
    };
    const variables: GQLInvokeEditingContextActionVariables = {
      input,
    };
    invokeEditingContextAction({ variables });
  };

  useEffect(() => {
    if (!loadingEditingContextAction) {
      if (errorEditingContextAction) {
        setState({ message: 'An unexpected error has occurred, please refresh the page' });
      }
      if (dataEditingContextAction) {
        const { invokeEditingContextAction } = dataEditingContextAction;
        if (isErrorPayload(invokeEditingContextAction)) {
          setState({ message: invokeEditingContextAction.message });
        }
      }
    }
  }, [loadingEditingContextAction, errorEditingContextAction, dataEditingContextAction]);

  return (
    <>
      <Card data-testid="actions">
        <CardContent className={classes.cardContent}>
          <Typography variant="h6">{'Create a new Model'}</Typography>
          <Typography color="textSecondary">
            {readOnly ? 'You need edit access to create models' : 'Select the model to create'}
          </Typography>
          <List dense={true}>
            {readOnly
              ? null
              : editingContextActions.map((editingContextAction) => {
                  return (
                    <ListItem
                      className={classes.item}
                      dense
                      disableGutters
                      button
                      key={editingContextAction.id}
                      data-testid={editingContextAction.id}
                      onClick={() => {
                        onInvokeEditingContextAction(editingContextAction.id);
                      }}>
                      <ListItemIcon>
                        <NoteAdd htmlColor="primary" fontSize="small" />
                      </ListItemIcon>
                      <ListItemText primary={editingContextAction.label} />
                    </ListItem>
                  );
                })}
          </List>
        </CardContent>
      </Card>
      <Toast message={state.message} open={!!state.message} onClose={() => setState({ message: null })} />
    </>
  );
};

/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { Toast, useSelection } from '@eclipse-sirius/sirius-components-core';
import Collections from '@mui/icons-material/Collections';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { NewDocumentAreaState } from './NewDocumentArea.types';
import {
  GQLCreateRepresentationData,
  GQLCreateRepresentationInput,
  GQLCreateRepresentationVariables,
  GQLErrorPayload,
  NewRepresentationAreaProps,
} from './NewRepresentationArea.types';

const useNewRepresentationAreaStyles = makeStyles()((theme) => ({
  subtitles: {
    textOverflow: 'ellipsis " [..]";',
  },
  cardContent: {
    overflowY: 'auto',
    maxHeight: theme.spacing(50),
  },
  item: {
    padding: 0,
  },
}));

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
          label
          kind
          __typename
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const NewRepresentationArea = ({
  editingContextId,
  representationDescriptions,
  readOnly,
}: NewRepresentationAreaProps) => {
  const [state, setState] = useState<NewDocumentAreaState>({
    message: null,
  });
  const { classes } = useNewRepresentationAreaStyles();
  const { selection, setSelection } = useSelection();

  const selectedItem = selection.entries.length > 0 ? selection.entries[0] : null;

  // Representation creation
  const [createRepresentation, { loading, data, error }] = useMutation<
    GQLCreateRepresentationData,
    GQLCreateRepresentationVariables
  >(createRepresentationMutation);

  useEffect(() => {
    if (!loading) {
      if (error) {
        setState({ message: 'An unexpected error has occurred, please refresh the page' });
      }
      if (data) {
        const { createRepresentation } = data;
        if (createRepresentation.representation) {
          const { id } = createRepresentation.representation;
          setSelection({ entries: [{ id }] });
        }
        if (isErrorPayload(createRepresentation)) {
          setState({ message: createRepresentation.message });
        }
      }
    }
  }, [loading, error, data]);

  const onCreateRepresentation = (representationDescriptionId) => {
    const selected = representationDescriptions.find((candidate) => candidate.id === representationDescriptionId);
    const objectId = selectedItem.id;
    const input: GQLCreateRepresentationInput = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId,
      representationDescriptionId,
      representationName: selected.defaultName,
    };
    createRepresentation({ variables: { input } });
  };

  const subtitle =
    selectedItem && representationDescriptions.length > 0
      ? 'Select the representation to create'
      : 'There are no representations available for the current selection';

  return (
    <>
      <Card>
        <CardContent className={classes.cardContent}>
          <Typography variant="h6">{'Create a new Representation'}</Typography>
          <Typography className={classes.subtitles} color="textSecondary">
            {readOnly ? 'You need edit access to create representations' : subtitle}
          </Typography>
          <List dense={true}>
            {readOnly
              ? null
              : representationDescriptions
                  .sort((a, b) => a.defaultName.localeCompare(b.defaultName))
                  .map((representationDescription) => {
                    return (
                      <ListItemButton
                        className={classes.item}
                        dense
                        disableGutters
                        key={representationDescription.id}
                        data-testid={representationDescription.id}
                        onClick={() => {
                          onCreateRepresentation(representationDescription.id);
                        }}>
                        <ListItemIcon>
                          <Collections fontSize="small" />
                        </ListItemIcon>
                        <ListItemText primary={representationDescription.defaultName} />
                      </ListItemButton>
                    );
                  })}
          </List>
        </CardContent>
      </Card>
      <Toast message={state.message} open={!!state.message} onClose={() => setState({ message: null })} />
    </>
  );
};

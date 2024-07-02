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
import { Toast, useSelection } from '@eclipse-sirius/sirius-components-core';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { Collections } from '@material-ui/icons';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NewDocumentAreaState } from './NewDocumentArea.types';
import {
  GQLCreateRepresentationData,
  GQLCreateRepresentationInput,
  GQLCreateRepresentationVariables,
  GQLErrorPayload,
  NewRepresentationAreaProps,
} from './NewRepresentationArea.types';

const useNewRepresentationAreaStyles = makeStyles((theme) => ({
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
  const classes = useNewRepresentationAreaStyles();
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'project.edit' });
  const { t: coreT } = useTranslation('siriusComponentsCore');
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
        setState({ message: coreT('errors.unexpected') });
      }
      if (data) {
        const { createRepresentation } = data;
        if (createRepresentation.representation) {
          const { id, label, kind } = createRepresentation.representation;
          setSelection({ entries: [{ id, label, kind }] });
        }
        if (isErrorPayload(createRepresentation)) {
          setState({ message: createRepresentation.message });
        }
      }
    }
  }, [loading, error, data, coreT]);

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
      ? t('selectRepresentationToCreate', { name: selectedItem.label })
      : t('noRepresentations');

  return (
    <>
      <Card>
        <CardContent className={classes.cardContent}>
          <Typography variant="h6">{t('createRepresentation')}</Typography>
          <Typography className={classes.subtitles} color="textSecondary">
            {readOnly ? t('noAccessToCreateRepresentation') : subtitle}
          </Typography>
          <List dense={true}>
            {readOnly
              ? null
              : representationDescriptions
                  .sort((a, b) => a.defaultName.localeCompare(b.defaultName))
                  .map((representationDescription) => {
                    return (
                      <ListItem
                        className={classes.item}
                        dense
                        disableGutters
                        button
                        key={representationDescription.id}
                        data-testid={representationDescription.id}
                        onClick={() => {
                          onCreateRepresentation(representationDescription.id);
                        }}>
                        <ListItemIcon>
                          <Collections htmlColor="primary" fontSize="small" />
                        </ListItemIcon>
                        <ListItemText primary={representationDescription.defaultName} />
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

/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { LinkButton } from 'core/linkbutton/LinkButton';
import { Select } from 'core/select/Select';
import gql from 'graphql-tag';
import { NewDocument } from 'icons';
import { NewDocumentAreaProps } from 'onboarding/NewDocumentArea.types';
import React, { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { AreaContainer } from './AreaContainer';
import styles from './NewDocumentArea.module.css';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const NewDocumentArea = ({
  editingContextId,
  stereotypeDescriptions,
  setSelection,
  readOnly,
}: NewDocumentAreaProps) => {
  const initialState = {
    message: undefined,
  };
  const [state, setState] = useState(initialState);
  const { message } = state;

  // Document creation
  const [createDocument, { loading, data, error }] = useMutation(createDocumentMutation);
  const onCreateDocument = (stereotypeDescriptionId) => {
    const selected = stereotypeDescriptions.find((candidate) => candidate.id === stereotypeDescriptionId);
    const variables = {
      input: {
        id: uuid(),
        editingContextId,
        name: selected.label,
        stereotypeDescriptionId: stereotypeDescriptionId,
      },
    };
    createDocument({ variables });
  };
  useEffect(() => {
    if (!loading && !error && data?.createDocument) {
      const { createDocument } = data;
      if (createDocument.__typename === 'ErrorPayload') {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = createDocument.message;
          return newState;
        });
      }
    }
  }, [loading, data, error, setSelection]);

  // Document stereotypes list
  let newDocumentButtons =
    stereotypeDescriptions.length > 0
      ? stereotypeDescriptions.slice(0, 5).map((stereotypeDescription) => {
          return (
            <LinkButton
              key={stereotypeDescription.id}
              label={stereotypeDescription.label}
              data-testid={stereotypeDescription.id}
              onClick={() => {
                onCreateDocument(stereotypeDescription.id);
              }}>
              <NewDocument title="" className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // More select
  const moreName = 'moreStereotypes';
  const moreLabel = 'More model types...';
  let moreSelect =
    stereotypeDescriptions.length > 5 ? (
      <Select
        onChange={(event) => {
          onCreateDocument(event.target.value);
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, stereotypeDescriptions.slice(5)].flat()}
        data-testid={moreName}
      />
    ) : null;
  let title = 'Create a new Model';
  if (readOnly) {
    return <AreaContainer title={title} subtitle="You need edit access to create models" />;
  } else {
    return (
      <AreaContainer title={title} subtitle="Select the model to create" banner={message}>
        {newDocumentButtons}
        {moreSelect}
      </AreaContainer>
    );
  }
};

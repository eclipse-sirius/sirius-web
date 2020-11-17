/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { useMutation } from 'common/GraphQLHooks';
import { AreaContainer } from './AreaContainer';
import { LinkButton } from 'core/linkbutton/LinkButton';
import { Select } from 'core/select/Select';
import { NewDocument } from 'icons';
import gql from 'graphql-tag';
import styles from './NewDocumentArea.module.css';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on CreateDocumentSuccessPayload {
        document {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const propTypes = {
  maxDisplay: PropTypes.number.isRequired,
  stereotypeDescriptions: PropTypes.array.isRequired,
  projectId: PropTypes.string.isRequired,
  setSelections: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
};

export const NewDocumentArea = ({ stereotypeDescriptions, projectId, maxDisplay, setSelections, disabled }) => {
  const initialState = {
    message: undefined,
  };
  const [state, setState] = useState(initialState);
  const { message } = state;

  // Document creation
  const [createDocument, createDocumentResult] = useMutation(createDocumentMutation, {}, 'createDocument');
  const onCreateDocument = (stereotypeDescriptionId) => {
    const selected = stereotypeDescriptions.find((candidate) => candidate.id === stereotypeDescriptionId);
    const variables = {
      input: {
        projectId,
        name: 'Untitled ' + selected.label,
        stereotypeDescriptionId: stereotypeDescriptionId,
      },
    };
    createDocument(variables);
  };
  useEffect(() => {
    if (!createDocumentResult.loading) {
      const { createDocument } = createDocumentResult.data.data;
      if (createDocument.__typename === 'ErrorPayload') {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = createDocument.message;
          return newState;
        });
      }
    }
  }, [createDocumentResult, setSelections]);

  // Document stereotypes list
  let newDocumentButtons =
    stereotypeDescriptions.length > 0
      ? stereotypeDescriptions.slice(0, maxDisplay).map((stereotypeDescription) => {
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
    stereotypeDescriptions.length > maxDisplay ? (
      <Select
        onChange={(event) => {
          onCreateDocument(event.target.value);
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, stereotypeDescriptions.slice(maxDisplay)].flat()}
        data-testid={moreName}
      />
    ) : null;
  let title = 'Create a new Model';
  if (disabled) {
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
NewDocumentArea.propTypes = propTypes;

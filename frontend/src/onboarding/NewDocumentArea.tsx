/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import {
  GQLCreateDocumentMutationInput,
  GQLCreateDocumentMutationVariables,
  GQLInvokeEditingContextActionInput,
  GQLInvokeEditingContextActionVariables,
  NewDocumentAreaProps,
} from 'onboarding/NewDocumentArea.types';
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

const NB_STEREOTYPES_SHOWN = 10;
const NB_EDITING_CONTEXT_ACTIONS_SHOWN = 10;

export const NewDocumentArea = ({
  editingContextId,
  stereotypeDescriptions,
  editingContextActions,
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
  const onCreateDocument = (stereotypeDescriptionId: string) => {
    const selected = stereotypeDescriptions.find((candidate) => candidate.id === stereotypeDescriptionId);
    const input: GQLCreateDocumentMutationInput = {
      id: uuid(),
      editingContextId,
      name: selected.documentName,
      stereotypeDescriptionId,
    };
    const variables: GQLCreateDocumentMutationVariables = {
      input,
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

  // EditingContext Action invocation
  const [
    invokeEditingContextAction,
    { loading: loadingEditingContextAction, data: dataEditingContextAction, error: errorEditingContextAction },
  ] = useMutation(invokeEditingContextActionMutation);
  const onInvokeEditingContextAction = (actionId: string) => {
    const input: GQLInvokeEditingContextActionInput = {
      id: uuid(),
      editingContextId,
      actionId,
    };
    const variables: GQLInvokeEditingContextActionVariables = {
      input,
    };
    invokeEditingContextAction({ variables });
  };
  useEffect(() => {
    if (
      !loadingEditingContextAction &&
      !errorEditingContextAction &&
      dataEditingContextAction?.invokeEditingContextAction
    ) {
      const { invokeEditingContextAction } = dataEditingContextAction;
      if (invokeEditingContextAction.__typename === 'ErrorPayload') {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = invokeEditingContextAction.message;
          return newState;
        });
      }
    }
  }, [loadingEditingContextAction, dataEditingContextAction, errorEditingContextAction, setSelection]);

  // Document stereotypes list
  let newDocumentButtons =
    stereotypeDescriptions.length > 0
      ? stereotypeDescriptions.slice(0, NB_STEREOTYPES_SHOWN).map((stereotypeDescription) => {
          return (
            <LinkButton
              key={stereotypeDescription.id}
              label={stereotypeDescription.label}
              data-testid={stereotypeDescription.id}
              onClick={() => {
                onCreateDocument(stereotypeDescription.id);
              }}
            >
              <NewDocument title="" className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // EditingContext Actions list
  let editingContextActionButtons =
    editingContextActions.length > 0
      ? editingContextActions.slice(0, NB_EDITING_CONTEXT_ACTIONS_SHOWN).map((editingContextAction) => {
          return (
            <LinkButton
              key={editingContextAction.id}
              label={editingContextAction.label}
              data-testid={editingContextAction.id}
              onClick={() => {
                onInvokeEditingContextAction(editingContextAction.id);
              }}
            >
              <NewDocument title="" className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // More select
  const moreName = 'moreStereotypes';
  const moreLabel = 'More model types...';
  let moreSelect =
    stereotypeDescriptions.length > NB_STEREOTYPES_SHOWN ? (
      <Select
        onChange={(event) => {
          onCreateDocument(event.target.value);
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, stereotypeDescriptions.slice(NB_STEREOTYPES_SHOWN)].flat()}
        data-testid={moreName}
      />
    ) : null;

  // More EditingContext actions select
  const moreEditingContextActionsName = 'moreEditingContextActions';
  const moreEditingContextActionsLabel = 'More EditingContext Actions types...';
  let moreEditingContextActionsSelect =
    editingContextActions.length > NB_EDITING_CONTEXT_ACTIONS_SHOWN ? (
      <Select
        onChange={(event) => {
          onInvokeEditingContextAction(event.target.value);
        }}
        name={moreEditingContextActionsName}
        options={[
          { id: moreEditingContextActionsLabel, label: moreEditingContextActionsLabel },
          editingContextActions.slice(NB_EDITING_CONTEXT_ACTIONS_SHOWN),
        ].flat()}
        data-testid={moreEditingContextActionsName}
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
        {editingContextActionButtons}
        {moreEditingContextActionsSelect}
      </AreaContainer>
    );
  }
};

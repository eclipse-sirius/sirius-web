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
import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect } from 'react';

import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLAction,
  GQLDiagramDescription,
  GQLErrorPayload,
  GQLGetActionsData,
  GQLGetActionsVariables,
  GQLInvokeActionData,
  GQLInvokeActionInput,
  GQLInvokeActionPayload,
  GQLInvokeActionSuccessPayload,
  GQLInvokeActionVariables,
  GQLRepresentationDescription,
  UseActionsProps,
} from './useActions.types';

export const getActionsQuery = gql`
  query getActions($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              nodeDescriptions {
                id
                actions(diagramElementId: $diagramElementId) {
                  id
                  label
                  iconURL
                }
              }
            }
          }
        }
      }
    }
  }
`;

const invokeActionMutation = gql`
  mutation invokeAction($input: InvokeActionInput!) {
    invokeAction(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

const isErrorPayload = (payload: GQLInvokeActionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isInvokeActionSuccessPayload = (payload: GQLInvokeActionPayload): payload is GQLInvokeActionSuccessPayload =>
  payload.__typename === 'InvokeActionSuccessPayload';

export const useActions = ({ diagramElementId, targetElementId, nodeDescriptionId }: UseActionsProps) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { data: actionsData, error: actionsError } = useQuery<GQLGetActionsData, GQLGetActionsVariables>(
    getActionsQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        diagramElementId,
      },
    }
  );

  useEffect(() => {
    if (actionsError) {
      addErrorMessage('An unexpected error has occurred while retrieving actions, please refresh the page');
    }
  }, [actionsError]);

  const description: GQLRepresentationDescription | undefined =
    actionsData?.viewer.editingContext.representation.description;

  const actions: GQLAction[] | null =
    description && isDiagramDescription(description)
      ? description.nodeDescriptions.filter((nd) => nd.id == nodeDescriptionId).flatMap((nd) => nd.actions)
      : null;

  const [invokeActionFrom] = useMutation<GQLInvokeActionData, GQLInvokeActionVariables>(invokeActionMutation);

  const invokeAction = useCallback(
    async (action: GQLAction) => {
      const { id: actionId } = action;
      const input: GQLInvokeActionInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        diagramElementId,
        targetElementId,
        actionId,
      };
      const { data } = await invokeActionFrom({
        variables: { input },
      });
      if (data) {
        const { invokeAction } = data;
        if (isInvokeActionSuccessPayload(invokeAction)) {
          addMessages(invokeAction.messages);
        }
        if (isErrorPayload(invokeAction)) {
          addMessages(invokeAction.messages);
        }
      }
    },
    [editingContextId, diagramId, diagramElementId, invokeActionMutation]
  );

  return { invokeAction, actions };
};

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
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLEvaluateExpressionMutationData,
  GQLEvaluateExpressionMutationVariables,
  GQLEvaluateExpressionPayload,
  GQLEvaluateExpressionSuccessPayload,
  UseEvaluateExpressionResult,
} from './useEvaluateExpression.types';

const evaluateExpressionMutation = gql`
  mutation evaluateExpression($input: EvaluateExpressionInput!) {
    evaluateExpression(input: $input) {
      __typename
      ... on EvaluateExpressionSuccessPayload {
        result {
          __typename
          ... on ObjectExpressionResult {
            objectValue: value {
              id
              kind
              label
              iconURLs
            }
          }
          ... on ObjectsExpressionResult {
            objectsValue: value {
              id
              kind
              label
              iconURLs
            }
          }
          ... on BooleanExpressionResult {
            booleanValue: value
          }
          ... on IntExpressionResult {
            intValue: value
          }
          ... on StringExpressionResult {
            stringValue: value
          }
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLEvaluateExpressionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isEvaluateExpressionSuccessPayload = (
  payload: GQLEvaluateExpressionPayload
): payload is GQLEvaluateExpressionSuccessPayload => payload.__typename === 'EvaluateExpressionSuccessPayload';

export const useEvaluateExpression = (): UseEvaluateExpressionResult => {
  const [performExpressionEvaluation, { loading, data, error }] = useMutation<
    GQLEvaluateExpressionMutationData,
    GQLEvaluateExpressionMutationVariables
  >(evaluateExpressionMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { evaluateExpression } = data;
      if (isErrorPayload(evaluateExpression)) {
        addMessages(evaluateExpression.messages);
      }
    }
  }, [data, error]);

  const evaluateExpression = (editingContextId: string, expression: string) => {
    const variables: GQLEvaluateExpressionMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        expression,
      },
    };

    performExpressionEvaluation({ variables });
  };

  let result: GQLEvaluateExpressionSuccessPayload | null = null;
  if (data && isEvaluateExpressionSuccessPayload(data.evaluateExpression)) {
    result = data.evaluateExpression;
  }

  return {
    evaluateExpression,
    loading,
    result,
  };
};

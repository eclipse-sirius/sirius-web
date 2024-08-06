/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { gql, useLazyQuery } from '@apollo/client';
import { GQLErrorPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetContainmentFeatureNamesPayload,
  GQLGetContainmentFeatureNamesQueryData,
  GQLGetContainmentFeatureNamesQueryVariables,
  GQLGetContainmentFeatureNamesSuccessPayload,
  UseContainmentFeatureNamesValue,
} from './useContainmentFeatureNames.types';

const getContainmentFeatureNamesQuery = gql`
  query getContainmentFeatureNames($editingContextId: ID!, $containerId: ID!, $containedObjectId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        containmentFeatureNames(containerId: $containerId, containedObjectId: $containedObjectId) {
          __typename
          ... on ErrorPayload {
            messages {
              body
              level
            }
          }
          ... on EditingContextContainmentFeatureNamesSuccessPayload {
            id
            containmentFeatureNames
          }
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLGetContainmentFeatureNamesPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLGetContainmentFeatureNamesPayload
): payload is GQLGetContainmentFeatureNamesSuccessPayload =>
  payload.__typename === 'EditingContextContainmentFeatureNamesSuccessPayload';

export const useContainmentFeatureNames = (): UseContainmentFeatureNamesValue => {
  const [getContainmentFeatureNames, { data, loading, error }] = useLazyQuery<
    GQLGetContainmentFeatureNamesQueryData,
    GQLGetContainmentFeatureNamesQueryVariables
  >(getContainmentFeatureNamesQuery);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
    const payload = data?.viewer?.editingContext?.containmentFeatureNames;
    if (payload) {
      if (isErrorPayload(payload)) {
        const { messages } = payload;
        addMessages(messages);
      }
    }
  }, [error, data]);

  let containmentFeatureNames = null;
  const payload = data?.viewer?.editingContext?.containmentFeatureNames;
  if (payload && isSuccessPayload(payload)) {
    containmentFeatureNames = payload.containmentFeatureNames;
  }

  return { getContainmentFeatureNames, containmentFeatureNames, loading };
};

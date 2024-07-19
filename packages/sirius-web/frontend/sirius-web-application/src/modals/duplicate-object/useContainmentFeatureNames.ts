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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetContainmentFeatureNamesQueryData,
  GQLGetContainmentFeatureNamesQueryVariables,
  UseContainmentFeatureNamesValue,
} from './useContainmentFeatureNames.types';

const getContainmentFeatureNamesQuery = gql`
  query getContainmentFeatureNames($editingContextId: ID!, $containerId: ID!, $containedObjectId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        containmentFeatureNames(containerId: $containerId, containedObjectId: $containedObjectId)
      }
    }
  }
`;

export const useContainmentFeatureNames = (
  editingContextId: string,
  containerId: string | null,
  containedObjectId: string
): UseContainmentFeatureNamesValue => {
  const variables: GQLGetContainmentFeatureNamesQueryVariables = {
    editingContextId,
    containerId,
    containedObjectId,
  };
  const { data, error } = useQuery<GQLGetContainmentFeatureNamesQueryData, GQLGetContainmentFeatureNamesQueryVariables>(
    getContainmentFeatureNamesQuery,
    { variables, skip: containerId === null }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const containmentFeatureNames = data?.viewer?.editingContext?.containmentFeatureNames ?? [];

  return { containmentFeatureNames };
};

/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetWorkbenchOmniboxCommandsQueryData,
  GQLGetWorkbenchOmniboxCommandsQueryVariables,
  UseWorkbenchOmniboxCommandsValue,
} from './useWorkbenchOmniboxCommands.types';

const getWorkbenchOmniboxCommandsQuery = gql`
  query getWorkbenchOmniboxCommands($editingContextId: ID!, $selectedObjectIds: [ID!]!, $query: String!) {
    viewer {
      workbenchOmniboxCommands(
        editingContextId: $editingContextId
        selectedObjectIds: $selectedObjectIds
        query: $query
      ) {
        edges {
          node {
            id
            label
            iconURLs
            description
          }
        }
        pageInfo {
          count
        }
      }
    }
  }
`;

export const useWorkbenchOmniboxCommands = (): UseWorkbenchOmniboxCommandsValue => {
  const [getWorkbenchOmniboxCommands, { loading, data, error }] = useLazyQuery<
    GQLGetWorkbenchOmniboxCommandsQueryData,
    GQLGetWorkbenchOmniboxCommandsQueryVariables
  >(getWorkbenchOmniboxCommandsQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    getWorkbenchOmniboxCommands,
    loading,
    data: data ?? null,
  };
};

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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetOmniboxCommandsQueryData,
  GQLGetOmniboxCommandsQueryVariables,
  UseOmniboxCommandsValue,
} from './useOmniboxCommands.types';

const getOmniboxCommandsQuery = gql`
  query getOmniboxCommands($contextEntries: [OmniboxContextEntry!]!, $query: String!) {
    viewer {
      omniboxCommands(contextEntries: $contextEntries, query: $query) {
        edges {
          node {
            id
            label
            kind
            iconURLs
            description
            __typename
          }
        }
        pageInfo {
          count
        }
      }
    }
  }
`;

export const useOmniboxCommands = (): UseOmniboxCommandsValue => {
  const [getOmniboxCommands, { loading, data, error }] = useLazyQuery<
    GQLGetOmniboxCommandsQueryData,
    GQLGetOmniboxCommandsQueryVariables
  >(getOmniboxCommandsQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    getOmniboxCommands,
    loading,
    data: data ?? null,
  };
};

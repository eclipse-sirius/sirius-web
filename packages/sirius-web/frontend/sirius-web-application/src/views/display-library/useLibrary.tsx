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
import { GQLGetLibraryQueryData, GQLGetLibraryQueryVariables, UseLibraryValue } from './useLibrary.types';

const getLibraryQuery = gql`
  query getLibraries($namespace: String!, $name: String!, $version: String!) {
    viewer {
      library(namespace: $namespace, name: $name, version: $version) {
        namespace
        name
        version
        description
        createdOn
        currentEditingContext {
          id
        }
      }
    }
  }
`;

export const useLibrary = (namespace: string, name: string, version: string): UseLibraryValue => {
  const variables: GQLGetLibraryQueryVariables = {
    namespace,
    name,
    version,
  };

  const { data, error, loading } = useQuery<GQLGetLibraryQueryData, GQLGetLibraryQueryVariables>(getLibraryQuery, {
    variables,
  });

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null, loading };
};

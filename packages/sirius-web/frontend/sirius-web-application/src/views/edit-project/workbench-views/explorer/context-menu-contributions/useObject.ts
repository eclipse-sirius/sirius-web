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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { GQLGetObjectQueryData, GQLGetObjectQueryVariables, UseObjectValue } from './useObject.types';

const getObjectLibrary = gql`
  query getObjectLibrary($editingContextId: ID!, $objectId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        object(objectId: $objectId) {
          library {
            namespace
            name
            version
          }
        }
      }
    }
  }
`;

export const useObject = (editingContextId: string, objectId: string): UseObjectValue => {
  const { loading, data, error } = useQuery<GQLGetObjectQueryData, GQLGetObjectQueryVariables>(getObjectLibrary, {
    variables: {
      editingContextId,
      objectId,
    },
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    data: data ?? null,
    loading,
  };
};

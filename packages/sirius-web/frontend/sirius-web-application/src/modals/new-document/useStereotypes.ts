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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetStereotypesQueryData,
  GQLGetStereotypesQueryVariables,
  UseStereotypesValue,
} from './useStereotypes.types';

const getStereotypesQuery = gql`
  query getStereotypes($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        ...EditingContextStereotypes
      }
    }
  }
`;

export const useStereotypes = (editingContextId: string): UseStereotypesValue => {
  const variables: GQLGetStereotypesQueryVariables = {
    editingContextId,
  };
  const { data, loading, error } = useQuery<GQLGetStereotypesQueryData, GQLGetStereotypesQueryVariables>(
    getStereotypesQuery,
    { variables }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null, loading };
};

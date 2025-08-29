/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { useEffect } from 'react';
import { useMultiToast } from '../toast/MultiToast';
import {
  GQLGetObjectLabelsQueryData,
  GQLGetObjectLabelsQueryVariables,
  UseObjectsLabelsValue,
} from './useObjectsLabels.types';

const getObjectLabelsQuery = gql`
  query getObjectsLabels($editingContextId: ID!, $objectIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        objects(objectIds: $objectIds) {
          id
          label
        }
      }
    }
  }
`;

export const useObjectsLabels = (): UseObjectsLabelsValue => {
  const [loadLabels, { loading, data, error }] = useLazyQuery<
    GQLGetObjectLabelsQueryData,
    GQLGetObjectLabelsQueryVariables
  >(getObjectLabelsQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const fetchObjectLabels = (editingContextId: string, objectIds: string[]) => {
    loadLabels({ variables: { editingContextId, objectIds } });
  };

  const labels: string[] | null = data?.viewer.editingContext?.objects.map((obj) => obj.label) || null;

  return {
    fetchObjectLabels,
    loading,
    labels,
  };
};

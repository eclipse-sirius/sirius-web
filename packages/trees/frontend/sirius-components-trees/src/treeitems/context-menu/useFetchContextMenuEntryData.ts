/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
  GQLFetchTreeItemContextEntryDataData,
  GQLFetchTreeItemContextMenuEntryData,
  GQLGetFetchTreeItemContextMenuEntryDataQueryVariables,
  UseFetchContextMenuEntryDataValue,
} from './useFetchContextMenuEntryData.types';

const getFetchTreeItemContextMenuEntryDataQuery = gql`
  query getFetchTreeItemContextMenuEntryDataQuery(
    $editingContextId: ID!
    $representationId: ID!
    $treeItemId: ID!
    $menuEntryId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              fetchTreeItemContextMenuEntryData(treeItemId: $treeItemId, menuEntryId: $menuEntryId) {
                urlToFetch
                fetchKind
              }
            }
          }
        }
      }
    }
  }
`;

export const useFetchContextMenuEntryData = (): UseFetchContextMenuEntryDataValue => {
  const { addErrorMessage } = useMultiToast();

  const [getFetchData, { loading, data, error }] = useLazyQuery<
    GQLFetchTreeItemContextEntryDataData,
    GQLGetFetchTreeItemContextMenuEntryDataQueryVariables
  >(getFetchTreeItemContextMenuEntryDataQuery);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [data, error]);

  const getFetchContextMenuEntryData = (
    editingContextId: string,
    treeId: string,
    treeItemId: string,
    menuEntryId: string
  ) => {
    const variables: GQLGetFetchTreeItemContextMenuEntryDataQueryVariables = {
      editingContextId,
      representationId: treeId,
      treeItemId,
      menuEntryId: menuEntryId,
    };
    getFetchData({ variables });
  };

  const fetchData: GQLFetchTreeItemContextMenuEntryData | null =
    data?.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData || null;

  return { getFetchContextMenuEntryData, loading, fetchData };
};

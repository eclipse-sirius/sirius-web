/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
  GQLGetAllContextMenuEntriesData,
  GQLGetAllContextMenuEntriesVariables,
  GQLTreeItemContextMenuEntry,
  UseContextMenuEntriesValue,
} from './useContextMenuEntries.types';

const getAllContextMenuEntriesQuery = gql`
  query getAllContextMenuEntries($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              contextMenu(treeItemId: $treeItemId) {
                keyBindings {
                  isCtrl
                  isMeta
                  isAlt
                  key
                }
                ... on SingleClickTreeItemContextMenuEntry {
                  __typename
                  id
                  label
                  iconURL
                  withImpactAnalysis
                }
                ... on FetchTreeItemContextMenuEntry {
                  __typename
                  id
                  label
                  iconURL
                }
              }
            }
          }
        }
      }
    }
  }
`;

export const useContextMenuEntries = (): UseContextMenuEntriesValue => {
  const [getContextMenuEntries, { loading, data, error }] = useLazyQuery<
    GQLGetAllContextMenuEntriesData,
    GQLGetAllContextMenuEntriesVariables
  >(getAllContextMenuEntriesQuery);

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const contextMenuEntries: GQLTreeItemContextMenuEntry[] =
    data?.viewer.editingContext.representation?.description.contextMenu || [];

  return {
    getContextMenuEntries,
    contextMenuEntries,
    loading,
  };
};

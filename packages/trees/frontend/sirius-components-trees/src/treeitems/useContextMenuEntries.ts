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
import { useEffect, useState } from 'react';
import { TreeItemContextMenuEntry } from './TreeItemContextMenu.types';
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
`;

export const useContextMenuEntries = (
  editingContextId: string,
  treeId: string,
  treeItemId: string
): UseContextMenuEntriesValue => {
  const [contextMenuEntries, setContextMenuEntries] = useState<TreeItemContextMenuEntry[]>([]);

  const { loading, data, error } = useQuery<GQLGetAllContextMenuEntriesData, GQLGetAllContextMenuEntriesVariables>(
    getAllContextMenuEntriesQuery,
    {
      variables: {
        editingContextId,
        representationId: treeId,
        treeItemId,
      },
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const contextEntries: GQLTreeItemContextMenuEntry[] =
    data?.viewer.editingContext.representation?.description.contextMenu || [];

  const convertMetadata = (menuEntry: GQLTreeItemContextMenuEntry): TreeItemContextMenuEntry => {
    return {
      __typename: menuEntry.__typename,
      id: menuEntry.id,
      label: menuEntry.label,
      iconURL: menuEntry.iconURL,
    };
  };

  useEffect(() => {
    if (!loading) {
      const allContextEntries: TreeItemContextMenuEntry[] = contextEntries.map(convertMetadata);
      setContextMenuEntries(allContextEntries);
    }
  }, [loading, contextEntries]);

  return {
    contextMenuEntries,
    loading,
  };
};

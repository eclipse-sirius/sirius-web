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
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';
import {
  GQLExplorerDescription,
  GQLGetAllExplorerDescriptionsData,
  GQLGetAllExplorerDescriptionsVariables,
  UseExplorerDescriptionValue,
} from './useExplorerDescriptions.types';

const getAllExplorerDescriptionsQuery = gql`
  query getAllExplorerDescriptions($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        explorerDescriptions {
          id
          label
        }
      }
    }
  }
`;

export const useExplorerDescriptions = (editingContextId: string): UseExplorerDescriptionValue => {
  const [treeDescriptions, setTreeDescriptions] = useState<TreeDescriptionMetadata[]>([]);

  const { loading, data, error } = useQuery<GQLGetAllExplorerDescriptionsData, GQLGetAllExplorerDescriptionsVariables>(
    getAllExplorerDescriptionsQuery,
    {
      variables: {
        editingContextId,
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

  const explorerDescriptions: GQLExplorerDescription[] = data?.viewer.editingContext.explorerDescriptions || [];

  useEffect(() => {
    if (!loading) {
      const allExplorerDescriptions: TreeDescriptionMetadata[] = explorerDescriptions.map((gqlExplorerDescription) => ({
        id: gqlExplorerDescription.id,
        label: gqlExplorerDescription.label,
      }));
      setTreeDescriptions(allExplorerDescriptions);
    }
  }, [loading, explorerDescriptions]);

  return {
    explorerDescriptions: treeDescriptions,
    loading,
  };
};

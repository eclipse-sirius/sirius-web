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
import { useMultiToast, WorkbenchConfiguration } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetWorkbenchConfigurationData,
  GQLGetWorkbenchConfigurationVariables,
  UseWorkbenchConfigurationValue,
} from './useWorkbenchConfiguration.types';

const getWorkbenchConfigurationQuery = gql`
  query getWorkbenchConfiguration($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        workbenchConfiguration {
          mainPanel {
            id
            representationEditors {
              representationId
              isActive
            }
          }
          workbenchPanels {
            id
            isOpen
            views {
              ... on DefaultViewConfiguration {
                id
                isActive
              }
            }
          }
        }
      }
    }
  }
`;

export const useWorkbenchConfiguration = (editingContextId: string, skip: boolean): UseWorkbenchConfigurationValue => {
  const { addErrorMessage } = useMultiToast();
  const { data, loading, error } = useQuery<GQLGetWorkbenchConfigurationData, GQLGetWorkbenchConfigurationVariables>(
    getWorkbenchConfigurationQuery,
    {
      variables: {
        editingContextId,
      },
      skip,
    }
  );

  const workbenchConfiguration: WorkbenchConfiguration | null = data?.viewer.editingContext.workbenchConfiguration;

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred while retrieving the workbench configuration');
    }
  }, [error]);

  return { workbenchConfiguration, loading };
};

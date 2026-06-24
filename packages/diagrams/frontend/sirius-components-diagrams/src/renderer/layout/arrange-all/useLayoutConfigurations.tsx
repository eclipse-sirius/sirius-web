/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import {
  GQLDiagramDescription,
  GQLGetLayoutConfigurationsData,
  GQLGetLayoutConfigurationsVariables,
  GQLLayoutConfiguration,
  GQLRepresentationDescription,
  UseLayoutConfigurationsValue,
} from './useLayoutConfigurations.types';

const getLayoutConfigurationsQuery = gql`
  query getLayoutConfigurations($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              layoutConfigurations {
                id
                label
                iconURL
                layoutOptions {
                  key
                  value
                }
              }
            }
          }
        }
      }
    }
  }
`;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const useLayoutConfigurations = (): UseLayoutConfigurationsValue => {
  const { addErrorMessage } = useMultiToast();
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { data, error, loading } = useQuery<GQLGetLayoutConfigurationsData, GQLGetLayoutConfigurationsVariables>(
    getLayoutConfigurationsQuery,
    {
      variables: {
        editingContextId,
        representationId: diagramId,
      },
    }
  );

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred while retrieving this configuration');
    }
  }, [error]);

  const description: GQLRepresentationDescription | undefined =
    data?.viewer.editingContext?.representation?.description;
  const layoutConfigurations: GQLLayoutConfiguration[] | null =
    description && isDiagramDescription(description) ? description.layoutConfigurations : null;

  return {
    layoutConfigurations,
    loading,
  };
};

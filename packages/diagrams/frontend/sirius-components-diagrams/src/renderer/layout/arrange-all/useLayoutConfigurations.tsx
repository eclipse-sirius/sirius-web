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
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { useContext, useMemo } from 'react';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import {
  GQLDiagramDescription,
  GQLGetLayoutConfigurationsData,
  GQLGetLayoutConfigurationsVariables,
  GQLLayoutConfigurations,
  GQLRepresentationDescription,
  UseLayoutConfigurationsValue,
} from './useLayoutConfigurations.types';

const getLayoutOptionsQuery = gql`
  query getLayoutOptions($editingContextId: ID!, $representationId: ID!) {
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

const EMPTY_ARRAY: GQLLayoutConfigurations[] = [];

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const useLayoutConfigurations = (): UseLayoutConfigurationsValue => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { data, error, loading } = useQuery<GQLGetLayoutConfigurationsData, GQLGetLayoutConfigurationsVariables>(
    getLayoutOptionsQuery,
    {
      variables: {
        editingContextId,
        representationId: diagramId,
      },
    }
  );

  const layoutConfigurations = useMemo(() => {
    if (!data || error || loading) {
      return EMPTY_ARRAY;
    }

    const description: GQLRepresentationDescription | undefined =
      data?.viewer.editingContext.representation.description;
    const layoutData: GQLLayoutConfigurations[] =
      description && isDiagramDescription(description) ? description.layoutConfigurations : [];

    return layoutData.map((layout): GQLLayoutConfigurations => {
      const optionsObject: LayoutOptions = {};

      if (layout.layoutOptions && Array.isArray(layout.layoutOptions)) {
        layout.layoutOptions.forEach((opt: { key: string; value: string }) => {
          optionsObject[opt.key] = opt.value;
        });
      }
      return {
        id: layout.id,
        label: layout.label,
        iconURL: layout.iconURL,
        layoutOptions: optionsObject,
      };
    });
  }, [data, error]);

  return {
    layoutConfigurations,
    loading,
  };
};

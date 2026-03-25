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

import { gql, useQuery } from '@apollo/client';
import { useContext, useEffect } from 'react';

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GetConnectorToolsData,
  GetConnectorToolsVariables,
  GQLDiagramDescription,
  GQLRepresentationDescription,
  GQLTool,
  UseConnectorPaletteContentValue,
} from './useConnectorPaletteContents.types';

export const getConnectorToolsQuery = gql`
  query getConnectorTools(
    $editingContextId: ID!
    $representationId: ID!
    $sourceDiagramElementId: ID!
    $targetDiagramElementId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              connectorTools(
                sourceDiagramElementId: $sourceDiagramElementId
                targetDiagramElementId: $targetDiagramElementId
              ) {
                id
                label
                iconURL
                ... on SingleClickOnTwoDiagramElementsTool {
                  dialogDescriptionId
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

export const useConnectorPaletteContents = (
  sourceDiagramElementId: string,
  targetDiagramElementId: string
): UseConnectorPaletteContentValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage } = useMultiToast();

  const {
    data,
    loading,
    error: paletteError,
  } = useQuery<GetConnectorToolsData, GetConnectorToolsVariables>(getConnectorToolsQuery, {
    variables: {
      editingContextId,
      representationId: diagramId,
      sourceDiagramElementId,
      targetDiagramElementId,
    },

    skip: !sourceDiagramElementId && !targetDiagramElementId,
  });

  const description: GQLRepresentationDescription | undefined =
    data?.viewer.editingContext?.representation?.description;

  const connectorTools: GQLTool[] | null =
    description && isDiagramDescription(description) ? description.connectorTools : [];

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  return { connectorTools, loading };
};

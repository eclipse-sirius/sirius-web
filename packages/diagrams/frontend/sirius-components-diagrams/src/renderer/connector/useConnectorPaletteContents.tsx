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
import { GQLPalette } from '@eclipse-sirius/sirius-components-palette';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLDiagramDescription,
  GQLGetConnectorPaletteData,
  GQLGetConnectorPaletteVariables,
  GQLRepresentationDescription,
  UseConnectorPaletteContentValue,
} from './useConnectorPaletteContents.types';

export const getPaletteQuery = gql`
  fragment ToolFields on Tool {
    __typename
    id
    label
    iconURL
    ... on SingleClickOnTwoDiagramElementsTool {
      dialogDescriptionId
    }
  }

  query getConnectorPalette(
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
              connectorPalette(
                sourceDiagramElementId: $sourceDiagramElementId
                targetDiagramElementId: $targetDiagramElementId
              ) {
                id
                quickAccessTools {
                  ...ToolFields
                }
                paletteEntries {
                  ...ToolFields
                  ... on ToolSection {
                    id
                    label
                    iconURL
                    tools {
                      ...ToolFields
                    }
                  }
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

  const { data, loading, error } = useQuery<GQLGetConnectorPaletteData, GQLGetConnectorPaletteVariables>(
    getPaletteQuery,
    {
      variables: {
        editingContextId,
        representationId: diagramId,
        sourceDiagramElementId,
        targetDiagramElementId,
      },
    }
  );

  const description: GQLRepresentationDescription | undefined =
    data?.viewer.editingContext?.representation?.description;
  const connectorPalette: GQLPalette | null =
    description && isDiagramDescription(description) ? description.connectorPalette : null;

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { connectorPalette, loading };
};

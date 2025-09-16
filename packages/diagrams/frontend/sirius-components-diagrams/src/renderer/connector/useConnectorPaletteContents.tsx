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
import { GQLPalette } from './../palette/Palette.types';
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
    $diagramId: ID!
    $sourceDiagramElementId: ID!
    $targetDiagramElementId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
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
  sourceDiagramElementId: string | null,
  targetDiagramElementId: string | null
): UseConnectorPaletteContentValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage } = useMultiToast();

  const {
    data: paletteData,
    loading,
    error: paletteError,
  } = useQuery<GQLGetConnectorPaletteData, GQLGetConnectorPaletteVariables>(getPaletteQuery, {
    variables: {
      editingContextId,
      diagramId,
      sourceDiagramElementId,
      targetDiagramElementId,
    },
  });

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const connectorPalette: GQLPalette | null =
    description && isDiagramDescription(description) ? description.connectorPalette : null;

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  return { connectorPalette, loading };
};

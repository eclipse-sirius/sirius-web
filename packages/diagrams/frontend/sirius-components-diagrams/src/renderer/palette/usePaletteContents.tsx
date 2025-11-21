/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLPalette } from './Palette.types';
import {
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLRepresentationDescription,
  UsePaletteContentValue,
} from './usePaletteContents.types';

export const getPaletteQuery = gql`
  fragment ToolFields on Tool {
    __typename
    id
    label
    iconURL
    ... on SingleClickOnDiagramElementTool {
      targetDescriptions {
        id
      }
      appliesToDiagramRoot
      dialogDescriptionId
      withImpactAnalysis
    }
  }
  query getPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementId: $diagramElementId) {
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

export const usePaletteContents = (diagramElementId: string, skip: boolean): UsePaletteContentValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage } = useMultiToast();

  const {
    data: paletteData,
    loading,
    error: paletteError,
  } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getPaletteQuery, {
    variables: {
      editingContextId,
      diagramId,
      diagramElementId,
    },
    skip,
  });

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const palette: GQLPalette | null = description && isDiagramDescription(description) ? description.palette : null;

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  return { palette, loading };
};

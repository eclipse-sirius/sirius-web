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
import { useContext, useEffect } from 'react';

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLPalette, GQLPaletteEntry } from '@eclipse-sirius/sirius-components-palette';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { GQLSingleClickOnTwoDiagramElementsTool } from '../connector/useConnector.types';
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
    dialogDescriptionId
    withImpactAnalysis
    keyBindings {
      isCtrl
      isMeta
      isAlt
      key
    }
    ... on SingleClickOnDiagramElementTool {
      targetDescriptions {
        id
      }
    }
  }
  query getPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementIds: [ID!]) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementIds: $diagramElementIds) {
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

const isSingleClickOnTwoDiagramElementsTool = (
  entry: GQLPaletteEntry
): entry is GQLSingleClickOnTwoDiagramElementsTool => entry.__typename === 'SingleClickOnTwoDiagramElementsTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const usePaletteContents = (diagramElementIds: string[], skip: boolean): UsePaletteContentValue => {
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
      diagramElementIds,
    },
    skip,
  });

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  let palette: GQLPalette | null = description && isDiagramDescription(description) ? description.palette : null;

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  //Filter isSingleClickOnTwoDiagramElementsTool until the backend return one palette for SingleClickOnDiagramElementTool and another for SingleClickOnTwoDiagramElementTool
  if (palette) {
    palette = {
      ...palette,
      paletteEntries: palette.paletteEntries.filter((entry) => !isSingleClickOnTwoDiagramElementsTool(entry)),
    };
  }

  return { palette, loading };
};

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

import { gql, useLazyQuery } from '@apollo/client';
import { useEffect } from 'react';

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
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
      appliesToDiagramRoot
      dialogDescriptionId
      withImpactAnalysis
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

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const usePaletteContents = (): UsePaletteContentValue => {
  const { addErrorMessage } = useMultiToast();

  const [getPaletteContents, { data: paletteData, loading, error: paletteError }] = useLazyQuery<
    GQLGetToolSectionsData,
    GQLGetToolSectionsVariables
  >(getPaletteQuery);

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const palette: GQLPalette | null = description && isDiagramDescription(description) ? description.palette : null;

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  return { getPaletteContents, palette, loading };
};

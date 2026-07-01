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
import {
  GQLPalette,
  isPaletteDivider,
  isSingleClickOnDiagramElementTool,
  isToolSection,
} from '@eclipse-sirius/sirius-components-palette';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
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
      dialogDescriptionId
      withImpactAnalysis
      withDeletionConfirmationDialog
      keyBindings {
        isCtrl
        isMeta
        isAlt
        key
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

  // Workaround since the same getPalette query is used for connector and node tools
  // See https://github.com/eclipse-sirius/sirius-web/pull/5511 that will remove this filter by using a dedicated useConnectorPalette query.
  if (palette) {
    palette = {
      id: palette.id,
      paletteEntries: palette.paletteEntries.filter(
        (entry) => isToolSection(entry) || isSingleClickOnDiagramElementTool(entry) || isPaletteDivider(entry)
      ),
      quickAccessTools: palette.quickAccessTools,
    };
  }

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  return { palette, loading };
};

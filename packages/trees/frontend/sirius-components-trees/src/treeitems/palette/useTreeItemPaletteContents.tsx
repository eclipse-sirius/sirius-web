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
import { useEffect } from 'react';

import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  GQLGetPaletteData,
  GQLGetPaletteVariables,
  GQLPalette,
  GQLRepresentationDescription,
  GQLTreeDescription,
  UsePaletteContentValue,
} from './useTreeItemPaletteContents.types';

export const getPaletteQuery = gql`
  query getPalette($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              palette(treeItemId: $treeItemId) {
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

  fragment ToolFields on Tool {
    __typename
    id
    label
    iconURL
    ... on SingleClickTreeItemTool {
      withImpactAnalysis
      keyBindings {
        isCtrl
        isMeta
        isAlt
        key
      }
    }
    ... on FetchTreeItemTool {
      keyBindings {
        isCtrl
        isMeta
        isAlt
        key
      }
    }
  }
`;

const isTreeDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLTreeDescription => representationDescription.__typename === 'TreeDescription';

export const useTreeItemPaletteContents = (
  editingContextId: string,
  treeId: string,
  treeItemId: string
): UsePaletteContentValue => {
  const { addErrorMessage } = useMultiToast();

  const { loading, data, error } = useQuery<GQLGetPaletteData, GQLGetPaletteVariables>(getPaletteQuery, {
    variables: {
      editingContextId,
      representationId: treeId,
      treeItemId,
    },
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const description: GQLRepresentationDescription | undefined = data?.viewer.editingContext.representation.description;
  let palette: GQLPalette | null = description && isTreeDescription(description) ? description.palette : null;

  return { palette, loading };
};

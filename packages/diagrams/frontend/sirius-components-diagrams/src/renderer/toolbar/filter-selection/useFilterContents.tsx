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

import { gql, useLazyQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from './../../../contexts/DiagramContext';
import { DiagramContextValue } from './../../../contexts/DiagramContext.types';
import {
  GQLDiagramDescription,
  GQLFilterContentsData,
  GQLFilterContentsVariables,
  GQLFilterSelectionMenuItem,
  GQLRepresentationDescription,
  UseFilterContentValue,
} from './useFilterContents.types';

export const getFilterSelectionMenuItems = gql`
  query getDiagramDescription($editingContextId: ID!, $representationId: ID!, $diagramElementIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              id
              toolbar {
                filterSelectionMenuItems(diagramElementIds: $diagramElementIds) {
                  id
                  label
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

export const useFilterContents = (): UseFilterContentValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage } = useMultiToast();

  const [loadFilterMenuItems, { data: filterData, error: filterError, loading }] = useLazyQuery<
    GQLFilterContentsData,
    GQLFilterContentsVariables
  >(getFilterSelectionMenuItems);

  const description: GQLRepresentationDescription | undefined =
    filterData?.viewer.editingContext.representation.description;

  const filterSelectionMenuItems: GQLFilterSelectionMenuItem[] =
    description && isDiagramDescription(description) && description.toolbar?.filterSelectionMenuItems
      ? description.toolbar?.filterSelectionMenuItems
      : [];

  useEffect(() => {
    if (filterError) {
      addErrorMessage(filterError.message);
    }
  }, [filterError]);

  const fetchFilterMenuItems = (diagramElementIds: string[]) => {
    loadFilterMenuItems({ variables: { editingContextId, representationId: diagramId, diagramElementIds } });
  };

  return { fetchFilterMenuItems, filterSelectionMenuItems, loading };
};

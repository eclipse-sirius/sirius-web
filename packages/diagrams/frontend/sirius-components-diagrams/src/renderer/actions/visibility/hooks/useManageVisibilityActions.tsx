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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';

import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import {
  GQLDiagramDescription,
  GQLGetManageVisibilityActionsData,
  GQLGetManageVisibilityActionsVariables,
  GQLManageVisibilityAction,
  GQLRepresentationDescription,
  UseManageVisibilityActionsValue,
} from './useManageVisibilityActions.types';

export const getActionsQuery = gql`
  query getActions($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              manageVisibilityActions(diagramElementId: $diagramElementId) {
                id
                label
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

export const useManageVisibilityActions = (diagramElementId: string): UseManageVisibilityActionsValue => {
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { data, error } = useQuery<GQLGetManageVisibilityActionsData, GQLGetManageVisibilityActionsVariables>(
    getActionsQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        diagramElementId,
      },
    }
  );

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred while retrieving actions, please refresh the page');
    }
  }, [error]);

  const description: GQLRepresentationDescription | undefined = data?.viewer.editingContext.representation.description;
  const actions: GQLManageVisibilityAction[] =
    description && isDiagramDescription(description) ? description.manageVisibilityActions : [];

  return { actions };
};

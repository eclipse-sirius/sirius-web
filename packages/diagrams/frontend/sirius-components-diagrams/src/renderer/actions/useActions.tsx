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
import { useData, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect, useMemo } from 'react';

import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ActionContributionProps } from './ActionsContribution.types';
import { actionsExtensionPoint } from './ActionsExtensionPoints';
import {
  GQLAction,
  GQLDiagramDescription,
  GQLGetActionsData,
  GQLGetActionsVariables,
  GQLRepresentationDescription,
  UseActionsValue,
} from './useActions.types';

export const getActionsQuery = gql`
  query getActions($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              actions(diagramElementId: $diagramElementId) {
                id
                name
                tooltip
                iconURLs
                readOnlyVisible
                remoteExecution
                localExecution
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

export const useActions = (diagramElementId: string): UseActionsValue => {
  const { addErrorMessage } = useMultiToast();
  const { readOnly, diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { data: actionsContribution } = useData(actionsExtensionPoint);
  const actionsContributionMap = useMemo(() => {
    const map: Map<string, ActionContributionProps> = new Map();
    actionsContribution.forEach((contribution) => map.set(contribution.name, contribution));
    return map;
  }, []);

  const { data, error } = useQuery<GQLGetActionsData, GQLGetActionsVariables>(getActionsQuery, {
    variables: {
      editingContextId,
      diagramId,
      diagramElementId,
    },
  });

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred while retrieving actions, please refresh the page');
    }
  }, [error]);

  const description: GQLRepresentationDescription | undefined = data?.viewer.editingContext.representation.description;
  const actions: GQLAction[] = description && isDiagramDescription(description) ? description.actions : [];
  const filteredActions: GQLAction[] = readOnly ? actions.filter((action) => action.readOnlyVisible) : actions;

  return { actions: filteredActions, contributions: actionsContributionMap };
};

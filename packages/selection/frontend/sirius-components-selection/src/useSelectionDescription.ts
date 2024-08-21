/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useEffect } from 'react';
import {
  GetSelectionDescriptionData,
  GetSelectionDescriptionVariables,
  GQLSelectionDescription,
  UseSelectionDescriptionProps,
  UseSelectionDescriptionValue,
} from './useSelectionDescription.types';
const getSelectionDescription = gql`
  query getSelectionDescription(
    $editingContextId: ID!
    $representationId: ID!
    $variables: [SelectionDialogVariable!]!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on SelectionDescription {
              message(variables: $variables)
              treeDescription {
                id
              }
            }
          }
        }
      }
    }
  }
`;

export const useSelectionDescription = ({
  editingContextId,
  selectionDescriptionId,
  variables,
}: UseSelectionDescriptionProps): UseSelectionDescriptionValue => {
  //Since the SelectionDialogRepresentation does not really exist, the representationId just contains the description id
  const representationId = `selectionDialog://?representationDescription=${encodeURIComponent(selectionDescriptionId)}`;
  const { loading, data, error } = useQuery<GetSelectionDescriptionData, GetSelectionDescriptionVariables>(
    getSelectionDescription,
    {
      variables: {
        editingContextId,
        representationId,
        variables,
      },
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const selectionDescription: GQLSelectionDescription | null =
    data?.viewer.editingContext.representation.description || null;

  return {
    selectionDescription,
    loading,
  };
};

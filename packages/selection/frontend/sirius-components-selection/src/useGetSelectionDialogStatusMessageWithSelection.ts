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
import { useEffect } from 'react';
import {
  GetDialogStatusMessageWithSelectionData,
  GetDialogStatusMessageWithSelectionVariables,
  UseGetSelectionDialogStatusMessageWithSelectionProps,
  UseGetSelectionDialogStatusMessageWithSelectionValue,
} from './useGetSelectionDialogStatusMessageWithSelection.types';

const getDialogStatusMessageWithSelection = gql`
  query getDialogStatusMessageWithSelection(
    $editingContextId: ID!
    $representationId: ID!
    $treeSelection: [String!]!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on SelectionDescription {
              dialogSelectionStatusMessageWithSelection(treeSelection: $treeSelection)
            }
          }
        }
      }
    }
  }
`;

export const useGetSelectionDialogStatusMessageWithSelection = ({
  editingContextId,
  selectionDescriptionId,
}: UseGetSelectionDialogStatusMessageWithSelectionProps): UseGetSelectionDialogStatusMessageWithSelectionValue => {
  const representationId = `selectionDialog://?representationDescription=${encodeURIComponent(selectionDescriptionId)}`;

  const [getStatusMessage, { data, loading, error }] = useLazyQuery<
    GetDialogStatusMessageWithSelectionData,
    GetDialogStatusMessageWithSelectionVariables
  >(getDialogStatusMessageWithSelection);

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const updateStatusMessage = (treeSelection: string[]) => {
    const variables: GetDialogStatusMessageWithSelectionVariables = {
      editingContextId,
      representationId,
      treeSelection,
    };
    getStatusMessage({ variables });
  };

  return {
    loading,
    dialogSelectionStatusMessageWithSelection:
      data?.viewer.editingContext?.representation?.description?.dialogSelectionStatusMessageWithSelection ?? null,
    updateStatusMessage,
  };
};

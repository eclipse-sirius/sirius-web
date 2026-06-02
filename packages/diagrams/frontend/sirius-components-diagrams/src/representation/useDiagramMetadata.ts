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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import {
  GQLGetRepresentationDiagramMetadataQueryData,
  GQLGetRepresentationDiagramMetadataQueryVariables,
  UseDiagramMetadataValue,
} from './useDiagramMetadata.types';

const getRepresentationDiagramMetadataQuery = gql`
  query getRepresentationDiagramMetadata($editingContextId: ID!, $diagramId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          label
        }
      }
    }
  }
`;

export const useDiagramMetadata = (skip: boolean): UseDiagramMetadataValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { loading, data, error } = useQuery<
    GQLGetRepresentationDiagramMetadataQueryData,
    GQLGetRepresentationDiagramMetadataQueryVariables
  >(getRepresentationDiagramMetadataQuery, {
    variables: { editingContextId, diagramId },
    skip,
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    loading,
    data: data ?? null,
  };
};

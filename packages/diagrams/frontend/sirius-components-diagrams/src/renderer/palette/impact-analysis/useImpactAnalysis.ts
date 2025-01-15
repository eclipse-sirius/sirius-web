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
import { useMultiToast, GQLImpactAnalysisReport } from '@eclipse-sirius/sirius-components-core';
import { gql, useQuery } from '@apollo/client';
import { useEffect } from 'react';
import {
  GQLInvokeImpactAnalysisToolVariables,
  GQLGetImpactAnalysisReportData,
  UseInvokeImpactAnalysisValue,
} from './useImpactAnalysis.types';
import { GQLToolVariable } from '../Palette.types';

const getImpactAnalysisReportQuery = gql`
  query getImpactAnalysisReport(
    $editingContextId: ID!
    $representationId: ID!
    $toolId: ID!
    $diagramElementId: ID!
    $variables: [ToolVariable!]!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              diagramImpactAnalysisReport(toolId: $toolId, diagramElementId: $diagramElementId, variables: $variables) {
                nbElementDeleted
                nbElementModified
                nbElementCreated
                additionalReports
              }
            }
          }
        }
      }
    }
  }
`;

export const useInvokeImpactAnalysis = (
  editingContextId: string | null,
  representationId: string | null,
  toolId: string | null,
  diagramElementId: string | null,
  variables: GQLToolVariable[] | null
): UseInvokeImpactAnalysisValue => {
  const { loading, data, error } = useQuery<GQLGetImpactAnalysisReportData, GQLInvokeImpactAnalysisToolVariables>(
    getImpactAnalysisReportQuery,
    {
      skip:
        editingContextId === null ||
        representationId === null ||
        toolId === null ||
        diagramElementId === null ||
        variables === null,
      variables: {
        editingContextId,
        representationId,
        toolId,
        diagramElementId,
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

  const impactAnalysisReport: GQLImpactAnalysisReport | null =
    data?.viewer.editingContext.representation.description.diagramImpactAnalysisReport ?? null;

  return { impactAnalysisReport, loading };
};

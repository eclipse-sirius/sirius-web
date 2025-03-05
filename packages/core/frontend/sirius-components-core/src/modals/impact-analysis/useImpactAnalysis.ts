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
import { useEffect } from 'react';
import {
  GQLInvokeImpactAnalysisToolVariables,
  GQLGetImpactAnalysisReportData,
  UseInvokeImpactAnalysisValue,
  GQLImpactAnalysisReport,
} from './useImpactAnalysis.types';
import { useMultiToast } from '../../toast/MultiToast';
import { GQLToolVariable } from '../../graphql/GQLTypes.types';

const getImpactAnalysisReportQuery = gql`
  query getImpactAnalysisReport(
    $editingContextId: ID!
    $representationId: ID!
    $toolId: ID!
    $targetObjectId: ID!
    $variables: [ToolVariable!]!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          impactAnalysisReport(toolId: $toolId, targetObjectId: $targetObjectId, variables: $variables) {
            nbElementDeleted
            nbElementModified
            nbElementCreated
            additionalReports
          }
        }
      }
    }
  }
`;

export const useInvokeImpactAnalysis = (
  editingContextId: string,
  representationId: string,
  toolId: string,
  targetObjectId: string,
  variables: GQLToolVariable[]
): UseInvokeImpactAnalysisValue => {
  const { loading, data, error } = useQuery<GQLGetImpactAnalysisReportData, GQLInvokeImpactAnalysisToolVariables>(
    getImpactAnalysisReportQuery,
    {
      variables: {
        editingContextId,
        representationId,
        toolId,
        targetObjectId,
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
    data?.viewer.editingContext.representation.impactAnalysisReport ?? null;

  return { impactAnalysisReport, loading };
};

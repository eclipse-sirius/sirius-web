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
import { gql, useLazyQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLImpactAnalysisReport } from '@eclipse-sirius/sirius-components-impactanalysis';
import { useEffect } from 'react';
import {
  GQLGetImpactAnalysisReportData,
  GQLInvokeImpactAnalysisToolVariables,
  UseInvokeImpactAnalysisValue,
} from './useDiagramImpactAnalysis.types';

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
                impactTree {
                  id
                  nodes {
                    id
                    parentId
                    label {
                      styledStringFragments {
                        text
                        styledStringFragmentStyle {
                          isStruckOut
                          underlineStyle
                          borderStyle
                          font
                          backgroundColor
                          foregroundColor
                          strikeoutColor
                          underlineColor
                          borderColor
                          isBold
                          isItalic
                        }
                      }
                    }
                    iconURLs
                    endIconsURLs
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

export const useInvokeImpactAnalysis = (): UseInvokeImpactAnalysisValue => {
  const [getImpactAnalysisReport, { loading, data, error }] = useLazyQuery<
    GQLGetImpactAnalysisReportData,
    GQLInvokeImpactAnalysisToolVariables
  >(getImpactAnalysisReportQuery);

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const impactAnalysisReport: GQLImpactAnalysisReport | null =
    data?.viewer.editingContext.representation.description.diagramImpactAnalysisReport ?? null;

  return { getImpactAnalysisReport, impactAnalysisReport, loading };
};

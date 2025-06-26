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
import { GQLImpactAnalysisReport, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetImpactAnalysisReportData,
  GQLInvokeImpactAnalysisVariables,
  UseInvokeImpactAnalysisValue,
} from './useUpdateLibraryImpactAnalysis.types';

const getImpactAnalysisReportQuery = gql`
  query getImpactAnalysisReport($editingContextId: ID!, $libraryId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        updateLibraryImpactAnalysisReport(libraryId: $libraryId) {
          nbElementDeleted
          nbElementModified
          nbElementCreated
          additionalReports
        }
      }
    }
  }
`;

export const useInvokeImpactAnalysis = (): UseInvokeImpactAnalysisValue => {
  const [getImpactAnalysisReport, { loading, data, error }] = useLazyQuery<
    GQLGetImpactAnalysisReportData,
    GQLInvokeImpactAnalysisVariables
  >(getImpactAnalysisReportQuery);

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const impactAnalysisReport: GQLImpactAnalysisReport | null =
    data?.viewer.editingContext.updateLibraryImpactAnalysisReport ?? null;

  return { getImpactAnalysisReport, impactAnalysisReport, loading };
};

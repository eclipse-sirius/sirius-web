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
import { LazyQueryExecFunction } from '@apollo/client';
import { GQLImpactAnalysisReport } from '@eclipse-sirius/sirius-components-core';

export interface UseInvokeImpactAnalysisValue {
  getImpactAnalysisReport: LazyQueryExecFunction<GQLGetImpactAnalysisReportData, GQLInvokeImpactAnalysisVariables>;
  impactAnalysisReport: GQLImpactAnalysisReport | null;
  loading: boolean;
}

export interface GQLInvokeImpactAnalysisVariables {
  editingContextId: string;
  libraryId: string;
}

export interface GQLGetImpactAnalysisReportData {
  viewer: GQLGetImpactAnalysisReportViewer;
}

export interface GQLGetImpactAnalysisReportViewer {
  editingContext: GQLGetImpactAnalysisReportEditingContext;
}

export interface GQLGetImpactAnalysisReportEditingContext {
  updateLibraryImpactAnalysisReport: GQLImpactAnalysisReport;
}

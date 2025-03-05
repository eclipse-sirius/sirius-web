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
import { GQLToolVariable } from '../../graphql/GQLTypes.types';

export interface UseInvokeImpactAnalysisValue {
  impactAnalysisReport: GQLImpactAnalysisReport | null;
  loading: boolean;
}

export interface GQLInvokeImpactAnalysisToolVariables {
  editingContextId: string;
  representationId: string;
  toolId: string;
  targetObjectId: string;
  variables: GQLToolVariable[];
}

export interface GQLGetImpactAnalysisReportData {
  viewer: GQLGetImpactAnalysisReportViewer;
}

export interface GQLGetImpactAnalysisReportViewer {
  editingContext: GQLGetImpactAnalysisReportEditingContext;
}

export interface GQLGetImpactAnalysisReportEditingContext {
  representation: GQLGetImpactAnalysisReportRepresentation;
}

export interface GQLGetImpactAnalysisReportRepresentation {
  impactAnalysisReport: GQLImpactAnalysisReport;
}

export interface GQLImpactAnalysisReport {
  nbElementDeleted: number;
  nbElementModified: number;
  nbElementCreated: number;
  additionalReports: string[];
}

export interface ReportViewerProps extends GQLImpactAnalysisReport {
  toolLabel: string;
}

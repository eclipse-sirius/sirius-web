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
import { GQLDataTree } from '@eclipse-sirius/sirius-components-datatree';

export interface ImpactAnalysisDialogProps {
  open: boolean;
  label: string;
  impactAnalysisReport: GQLImpactAnalysisReport | null;
  loading: boolean;
  onConfirm: () => void;
  onCancel: () => void;
}

export interface GQLImpactAnalysisReport {
  nbElementDeleted: number;
  nbElementModified: number;
  nbElementCreated: number;
  additionalReports: string[];
  impactTree: GQLDataTree;
}

export interface ReportViewerProps extends GQLImpactAnalysisReport {
  label: string;
}

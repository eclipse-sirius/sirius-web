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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseDeleteValue {
  deleteDiagramElements: (editingContextId: string, diagramId: string, nodeIds: string[], edgeIds: string[]) => void;
  loading: boolean;
  data: GQLDeleteFromDiagramData | null;
}

export interface GQLDeleteFromDiagramVariables {
  input: GQLDeleteFromDiagramInput;
}

export interface GQLDeleteFromDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  edgeIds: string[];
}

export interface GQLDeleteFromDiagramData {
  deleteFromDiagram: GQLDeleteFromDiagramPayload;
}

export type GQLDeleteFromDiagramPayload = GQLErrorPayload | GQLSuccessPayload;

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
import { GQLToolVariable } from '../Palette.types';

export interface ImpactAnalysisDialogContextValue {
  showImpactAnalysisDialog: (
    editingContextId: string,
    representationId: string,
    toolId: string,
    toolLabel: string,
    diagramElementId: string,
    variables: GQLToolVariable[],
    onConfirm: () => void
  ) => void;
}

export interface ImpactAnalysisDialogContextProviderState {
  open: boolean;
  onConfirm: () => void;
  editingContextId: string | null;
  representationId: string | null;
  toolId: string | null;
  toolLabel: string | null;
  diagramElementId: string | null;
  variables: GQLToolVariable[];
}

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

export interface UseGetSelectionDialogSelectionRequiredWithSelectionStatusMessageProps {
  editingContextId: string;
  selectionDescriptionId: string;
}

export interface UseGetSelectionDialogSelectionRequiredWithSelectionStatusMessageValue {
  loading: boolean;
  dialogSelectionRequiredWithSelectionStatusMessage: string | null;
  updateStatusMessage: (treeSelection: string[]) => void;
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageVariables {
  editingContextId: string;
  representationId: string;
  selectedObjectIds: string[];
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageData {
  viewer: GetSelectionDialogSelectionRequiredWithSelectionStatusMessageViewer;
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageViewer {
  editingContext: GetSelectionDialogSelectionRequiredWithSelectionStatusMessageEditingContext | null;
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageEditingContext {
  representation: GetSelectionDialogSelectionRequiredWithSelectionStatusMessageRepresentation | null;
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageRepresentation {
  description: GetSelectionDialogSelectionRequiredWithSelectionStatusMessageDescription | null;
}

export interface GetSelectionDialogSelectionRequiredWithSelectionStatusMessageDescription {
  dialogSelectionRequiredWithSelectionStatusMessage: string;
}

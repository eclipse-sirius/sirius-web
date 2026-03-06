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

export interface UseGetSelectionDialogStatusMessageWithSelectionProps {
  editingContextId: string;
  selectionDescriptionId: string;
}

export interface UseGetSelectionDialogStatusMessageWithSelectionValue {
  loading: boolean;
  dialogSelectionStatusMessageWithSelection: string | null;
  updateStatusMessage: (treeSelection: string[]) => void;
}

export interface GetDialogStatusMessageWithSelectionVariables {
  editingContextId: string;
  representationId: string;
  treeSelection: string[];
}

export interface GetDialogStatusMessageWithSelectionData {
  viewer: GetDialogStatusMessageWithSelectionViewer;
}

export interface GetDialogStatusMessageWithSelectionViewer {
  editingContext: GetDialogStatusMessageWithSelectionEditingContext | null;
}

export interface GetDialogStatusMessageWithSelectionEditingContext {
  representation: GetDialogStatusMessageWithSelectionRepresentation | null;
}

export interface GetDialogStatusMessageWithSelectionRepresentation {
  description: GetDialogStatusMessageWithSelectionDescription | null;
}

export interface GetDialogStatusMessageWithSelectionDescription {
  dialogSelectionStatusMessageWithSelection: string;
}

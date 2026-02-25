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

import { GQLSelectionDescription } from './useSelectionDescription.types';

export interface SelectionDialogContextProviderProps {
  children: React.ReactNode;
  selectionDescription: GQLSelectionDescription;
}
export interface SelectionDialogContextValue {
  selectionDialogDescription: GQLSelectionDialogDescription;
  selectionOptionSelected: boolean;
  noSelectionOptionSelected: boolean;
  treeDescriptionId: string | null;
  multiple: boolean;
  optional: boolean;
  updateSelectionOptions: (noSelectionOptionSelected: boolean, selectionOptionSelected: boolean) => void;
}

export interface SelectionDialogContextProviderState {
  noSelectionOptionSelected: boolean;
  selectionOptionSelected: boolean;
}

export interface GQLSelectionDialogDescription {
  titles: GQLSelectionDialogTitlesDescription;
  description: string;
  noSelectionAction: GQLSelectionDialogNoSelectionActionDescription;
  selectionAction: GQLSelectionDialogSelectionActionDescription;
  statusMessages: GQLSelectionDialogStatusMessages;
  confirmButtonLabels: GQLSelectionDialogConfirmButtonLabels;
}

export interface GQLSelectionDialogTitlesDescription {
  defaultTitle: string;
  noSelectionTitle: string;
  selectionTitle: string;
}

export interface GQLSelectionDialogStatusMessages {
  noSelectionStatusMessage: string;
  selectionStatusMessageWithoutSelection: string;
}

export interface GQLSelectionDialogConfirmButtonLabels {
  noSelectionConfirmButtonLabel: string;
  selectionConfirmButtonLabelWithoutSelection: string;
}

export interface GQLSelectionDialogNoSelectionActionDescription {
  label: string;
  description: string;
}

export interface GQLSelectionDialogSelectionActionDescription {
  label: string;
  description: string;
}

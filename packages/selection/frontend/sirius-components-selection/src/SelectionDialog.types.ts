/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import { DiagramDialogComponentProps } from '@eclipse-sirius/sirius-components-diagrams';
import { GQLTreeDescription } from './useSelectionDescription.types';

export interface SelectionDialogProps extends DiagramDialogComponentProps {}

export interface SelectionDialogState {
  selectedTreeItemIds: string[];
  selectionDialogOption: SelectionDialogOptions;
}

export type SelectionDialogOptions = 'INITIAL' | 'NO_SELECTION' | 'WITH_SELECTION';

export interface GQLSelectionDescription {
  dialog: GQLSelectionDialogDescription;
  treeDescription: GQLTreeDescription;
  multiple: boolean;
  optional: boolean;
}

export interface GQLSelectionDialogDescription {
  titles: GQLSelectionDialogTitlesDescription;
  description: string;
  noSelectionAction: GQLSelectionDialogNoSelectionActionDescription;
  withSelectionAction: GQLSelectionDialogSelectionActionDescription;
  statusMessages: GQLSelectionDialogStatusMessages;
  confirmButtonLabels: GQLSelectionDialogConfirmButtonLabels;
}

export interface GQLSelectionDialogTitlesDescription {
  defaultTitle: string;
  noSelectionTitle: string;
  selectionTitle: string;
}

export interface GQLSelectionDialogStatusMessages {
  noSelectionActionStatusMessage: string;
  selectionRequiredWithoutSelectionStatusMessage: string;
}

export interface GQLSelectionDialogConfirmButtonLabels {
  noSelectionConfirmButtonLabel: string;
  selectionRequiredWithoutSelectionConfirmButtonLabel: string;
  selectionRequiredWithSelectionConfirmButtonLabel: string;
}

export interface GQLSelectionDialogNoSelectionActionDescription {
  label: string;
  description: string;
}

export interface GQLSelectionDialogSelectionActionDescription {
  label: string;
  description: string;
}

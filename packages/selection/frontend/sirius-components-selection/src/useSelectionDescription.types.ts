/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import { DiagramDialogVariable } from '@eclipse-sirius/sirius-components-diagrams';

export interface UseSelectionDescriptionProps {
  editingContextId: string;
  selectionDescriptionId: string;
  variables: DiagramDialogVariable[];
}

export interface UseSelectionDescriptionValue {
  loading: boolean;
  selectionDescription: GQLSelectionDescription | null;
}

export interface GetSelectionDescriptionVariables {
  editingContextId: string;
  representationId: string;
  variables: DiagramDialogVariable[];
}

export interface GQLSelectionDescription {
  dialog: GQLSelectionDialog;
  treeDescription: GQLTreeDescription;
  multiple: boolean;
  optional: boolean;
}

export interface GQLSelectionDialog {
  titles: GQLSelectionDialogTitles;
  description: string;
  noSelectionAction: GQLSelectionDialogAction;
  withSelectionAction: GQLSelectionDialogAction;
  statusMessages: GQLSelectionDialogStatusMessages;
  confirmButtonLabels: GQLSelectionDialogConfirmButtonLabels;
}

export interface GQLSelectionDialogTitles {
  defaultTitle: string;
  noSelectionTitle: string;
  withSelectionTitle: string;
}

export interface GQLSelectionDialogAction {
  label: string;
  description: string;
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

export interface GQLTreeDescription {
  id: string;
}

export interface GetSelectionDescriptionData {
  viewer: GetSelectionDescriptionViewer;
}

export interface GetSelectionDescriptionViewer {
  editingContext: GetSelectionDescriptionEditingContext;
}

export interface GetSelectionDescriptionEditingContext {
  representation: GetSelectionDescriptionRepresentation;
}

export interface GetSelectionDescriptionRepresentation {
  description: GQLSelectionDescription;
}

/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export interface DynamicDialogModalProps {
  editingContextId: string;
  dialogDescriptionId: string;
  objectId: any;
  onMutationDone: any;
  onClose: any;
}

export interface DialogDescription {
  id: string;
  label: string;
}

export interface GQLGetDynamicDialogQueryVariables {
  editingContextId: string;
  objectId: string;
  dialogDescriptionId: string;
}

export interface GQLGetDynamicDialogQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  dynamicDialog: GQLDynamicDialog;
}

export interface GQLDynamicDialog {
  id: string;
  label: string;
  widgets: GQLDWidget[];
}

export interface GQLDWidget {
  __typename: string;
  id: string;
  parentId: string;
  descriptionId: string;
  label: string;
  outputVariableName: string;
  inputVariableNames: string[];
  required: boolean;
}
export interface GQLDSelectWidget extends GQLDWidget {
  initialValue: string;
}

export interface VariableRuntime {
  value: string;
  setValue: any;
}

export interface GQLGetDynamicDialogValidationMessagesQueryVariables {
  editingContextId: string;
  objectId: string;
  dialogDescriptionId: string;
  variables: any[];
}

export interface GQLVariable {
  name: string;
  value: string;
}

export interface GQLGetDynamicDialogValidationMessagesQueryData {
  viewer: GQLViewerValidationMessages;
}

export interface GQLViewerValidationMessages {
  editingContext: GQLEditingContextValidationMessages;
}

export interface GQLEditingContextValidationMessages {
  dynamicDialogValidationMessages: GQLValidationMessages[];
}
export interface GQLValidationMessages {
  severity: string;
  message: string;
  blocksApplyDialog: string;
}

export interface GQLApplyDynamicDialogVariables {
  input: GQLApplyDynamicDialogInput;
}
export interface GQLApplyDynamicDialogInput {
  id: string;
  dialogDescriptionId: string;
  editingContextId: string;
  objectId: string;
  widgetVariables: any[];
}

export interface GQLApplyDynamicDialogMutationData {
  applyDynamicDialog: GQLApplyDynamicDialogPayload;
}

export interface GQLApplyDynamicDialogPayload {
  __typename: string;
  id: string;
}

export interface GQLApplyDynamicDialogSuccessPayload extends GQLApplyDynamicDialogPayload {}

// export interface GQLObject {
//   id: string;
//   label: string;
//   kind: string;
// }

export interface GQLErrorPayload extends GQLApplyDynamicDialogPayload {
  message: string;
}

/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import { GQLGroup } from '@eclipse-sirius/sirius-components-forms';

export interface GQLWidgetOperationPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLWidgetOperationPayload {
  id: string;
}

export interface GQLErrorPayload extends GQLWidgetOperationPayload {
  message: string;
}
export interface GQLFormDescriptionEditorEventSubscription {
  formDescriptionEditorEvent: GQLFormDescriptionEditorEventPayload;
}

export interface GQLFormDescriptionEditorEventVariables {
  input: GQLFormDescriptionEditorEventInput;
}

export interface GQLFormDescriptionEditorEventInput {
  id: string;
  editingContextId: string;
  formDescriptionEditorId: string;
}

export interface GQLFormDescriptionEditorEventPayload {
  __typename: string;
}

export interface GQLSubscribersUpdatedEventPayload extends GQLFormDescriptionEditorEventPayload {
  id: string;
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
}

export interface GQLFormDescriptionEditorRefreshedEventPayload extends GQLFormDescriptionEditorEventPayload {
  id: string;
  formDescriptionEditor: GQLFormDescriptionEditor;
}

export interface Subscriber {
  username: string;
}

export interface GQLErrorPayload extends GQLFormDescriptionEditorEventPayload {
  message: string;
}

export interface GQLRepresentation {
  id: string;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
}

export interface GQLFormDescriptionEditor extends GQLRepresentation {
  id: string;
  metadata: GQLRepresentationMetadata;
  groups: GQLGroup[];
}

export interface GQLAddWidgetInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string;
  kind: string;
  index: number;
}

export interface GQLAddWidgetMutationVariables {
  input: GQLAddWidgetInput;
}

export interface GQLAddWidgetMutationData {
  addWidget: GQLWidgetOperationPayload;
}

export interface GQLDeleteWidgetInput {
  id: string;
  editingContextId: string;
  representationId: string;
  widgetId: string;
}

export interface GQLDeleteWidgetMutationVariables {
  input: GQLDeleteWidgetInput;
}

export interface GQLDeleteWidgetMutationData {
  deleteWidget: GQLWidgetOperationPayload;
}

export interface GQLMoveWidgetInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string;
  widgetId: string;
  index: number;
}

export interface GQLMoveWidgetMutationVariables {
  input: GQLMoveWidgetInput;
}

export interface GQLMoveWidgetMutationData {
  moveWidget: GQLWidgetOperationPayload;
}

export interface GQLAddToolbarActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string;
}

export interface GQLAddToolbarActionMutationVariables {
  input: GQLAddToolbarActionInput;
}

export interface GQLAddToolbarActionMutationData {
  addToolbarAction: GQLWidgetOperationPayload;
}

export interface GQLDeleteToolbarActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  toolbarActionId: string;
}

export interface GQLDeleteToolbarActionMutationVariables {
  input: GQLDeleteToolbarActionInput;
}

export interface GQLDeleteToolbarActionMutationData {
  deleteToolbarAction: GQLWidgetOperationPayload;
}

export interface GQLMoveToolbarActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string;
  toolbarActionId: string;
  index: number;
}

export interface GQLMoveToolbarActionMutationVariables {
  input: GQLMoveToolbarActionInput;
}

export interface GQLMoveToolbarActionMutationData {
  moveToolbarAction: GQLWidgetOperationPayload;
}

export interface GQLAddGroupInput {
  id: string;
  editingContextId: string;
  representationId: string;
  index: number;
}

export interface GQLAddGroupMutationVariables {
  input: GQLAddGroupInput;
}

export interface GQLAddGroupMutationData {
  addGroup: GQLWidgetOperationPayload;
}
export interface GQLDeleteGroupInput {
  id: string;
  editingContextId: string;
  representationId: string;
  groupId: string;
}

export interface GQLDeleteGroupMutationVariables {
  input: GQLDeleteGroupInput;
}

export interface GQLDeleteGroupMutationData {
  deleteGroup: GQLWidgetOperationPayload;
}

export interface GQLMoveGroupInput {
  id: string;
  editingContextId: string;
  representationId: string;
  groupId: string;
  index: number;
}

export interface GQLMoveGroupMutationVariables {
  input: GQLMoveGroupInput;
}

export interface GQLMoveGroupMutationData {
  moveGroup: GQLWidgetOperationPayload;
}

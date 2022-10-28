/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { GQLFlexDirection, GQLFlexWrap } from '@eclipse-sirius/sirius-components-forms';

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
  toolbarActions: GQLFormDescriptionEditorToolbarAction[];
  widgets: GQLAbstractFormDescriptionEditorWidget[];
}

export interface GQLAbstractFormDescriptionEditorWidget {
  id: string;
  label: string;
  kind: string;
}

export interface GQLFormDescriptionEditorWidget extends GQLAbstractFormDescriptionEditorWidget {}

export interface GQLFormDescriptionEditorFlexboxContainer extends GQLAbstractFormDescriptionEditorWidget {
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  children: GQLAbstractFormDescriptionEditorWidget[];
}

export interface GQLFormDescriptionEditorToolbarAction extends GQLAbstractFormDescriptionEditorWidget {}

export interface GQLAddWidgetInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string | null;
  kind: string;
  index: number;
}

export interface GQLAddWidgetMutationVariables {
  input: GQLAddWidgetInput;
}

export interface GQLAddWidgetMutationData {
  addWidget: GQLAddWidgetPayload;
}

export interface GQLAddWidgetPayload {
  __typename: string;
}

export interface GQLAddWidgetSuccessPayload extends GQLAddWidgetPayload {
  id: string;
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
  deleteWidget: GQLDeleteWidgetPayload;
}

export interface GQLDeleteWidgetPayload {
  __typename: string;
}

export interface GQLDeleteWidgetSuccessPayload extends GQLDeleteWidgetPayload {
  id: string;
}

export interface GQLMoveWidgetInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string | null;
  widgetId: string;
  index: number;
}

export interface GQLMoveWidgetMutationVariables {
  input: GQLMoveWidgetInput;
}

export interface GQLMoveWidgetMutationData {
  moveWidget: GQLMoveWidgetPayload;
}

export interface GQLMoveWidgetPayload {
  __typename: string;
}

export interface GQLMoveWidgetSuccessPayload extends GQLMoveWidgetPayload {
  id: string;
}

export interface GQLAddToolbarActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string | null;
}

export interface GQLAddToolbarActionMutationVariables {
  input: GQLAddToolbarActionInput;
}

export interface GQLAddToolbarActionMutationData {
  addToolbarAction: GQLAddToolbarActionPayload;
}

export interface GQLAddToolbarActionPayload {
  __typename: string;
}

export interface GQLAddToolbarActionSuccessPayload extends GQLAddToolbarActionPayload {
  id: string;
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
  deleteToolbarAction: GQLDeleteToolbarActionPayload;
}

export interface GQLDeleteToolbarActionPayload {
  __typename: string;
}

export interface GQLDeleteToolbarActionSuccessPayload extends GQLDeleteToolbarActionPayload {
  id: string;
}

export interface GQLMoveToolbarActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  containerId: string | null;
  toolbarActionId: string;
  index: number;
}

export interface GQLMoveToolbarActionMutationVariables {
  input: GQLMoveToolbarActionInput;
}

export interface GQLMoveToolbarActionMutationData {
  moveToolbarAction: GQLMoveToolbarActionPayload;
}

export interface GQLMoveToolbarActionPayload {
  __typename: string;
}

export interface GQLMoveToolbarActionSuccessPayload extends GQLMoveToolbarActionPayload {
  id: string;
}

export interface GQLErrorPayload
  extends GQLAddWidgetPayload,
    GQLDeleteWidgetPayload,
    GQLMoveWidgetPayload,
    GQLAddToolbarActionPayload,
    GQLDeleteToolbarActionPayload,
    GQLMoveToolbarActionPayload {
  message: string;
}

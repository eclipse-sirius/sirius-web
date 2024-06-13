/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import { GQLPage } from '@eclipse-sirius/sirius-components-forms';

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

export interface GQLFormDescriptionEditor extends GQLRepresentation {
  id: string;
  pages: GQLPage[];
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
  addWidget: GQLAddWidgetPayload;
}

export interface GQLAddWidgetPayload {
  __typename: string;
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
  moveWidget: GQLMoveWidgetPayload;
}

export interface GQLMoveWidgetPayload {
  __typename: string;
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
  addToolbarAction: GQLAddToolbarActionPayload;
}

export interface GQLAddToolbarActionPayload {
  __typename: string;
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
  moveToolbarAction: GQLMoveToolbarActionPayload;
}

export interface GQLMoveToolbarActionPayload {
  __typename: string;
}
export interface GQLAddGroupInput {
  id: string;
  editingContextId: string;
  representationId: string;
  pageId: string;
  index: number;
}

export interface GQLAddGroupMutationVariables {
  input: GQLAddGroupInput;
}

export interface GQLAddGroupMutationData {
  addGroup: GQLAddGroupPayload;
}

export interface GQLAddGroupPayload {
  __typename: string;
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
  deleteGroup: GQLDeleteGroupPayload;
}

export interface GQLDeleteGroupPayload {
  __typename: string;
}

export interface GQLMoveGroupInput {
  id: string;
  editingContextId: string;
  representationId: string;
  pageId: string;
  groupId: string;
  index: number;
}

export interface GQLMoveGroupMutationVariables {
  input: GQLMoveGroupInput;
}

export interface GQLMoveGroupMutationData {
  moveGroup: GQLMoveGroupPayload;
}

export interface GQLMoveGroupPayload {
  __typename: string;
}

export interface GQLAddPageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  index: number;
}

export interface GQLAddPageMutationVariables {
  input: GQLAddPageInput;
}

export interface GQLAddPageMutationData {
  addPage: GQLAddPagePayload;
}

export interface GQLAddPagePayload {
  __typename: string;
}

export interface GQLMovePageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  pageId: string;
  index: number;
}

export interface GQLMovePageMutationVariables {
  input: GQLMovePageInput;
}

export interface GQLMovePageMutationData {
  movePage: GQLMovePagePayload;
}

export interface GQLMovePagePayload {
  __typename: string;
}

export interface GQLDeletePageInput {
  id: string;
  editingContextId: string;
  representationId: string;
  pageId: string;
}

export interface GQLDeletePageMutationVariables {
  input: GQLDeletePageInput;
}

export interface GQLDeletePageMutationData {
  deletePage: GQLMovePagePayload;
}

export interface GQLDeletePagePayload {
  __typename: string;
}

export interface GQLErrorPayload
  extends GQLAddWidgetPayload,
    GQLDeleteWidgetPayload,
    GQLMoveWidgetPayload,
    GQLAddToolbarActionPayload,
    GQLDeleteToolbarActionPayload,
    GQLMoveToolbarActionPayload,
    GQLAddGroupPayload,
    GQLDeleteGroupPayload,
    GQLMoveGroupPayload,
    GQLAddPagePayload,
    GQLMovePagePayload,
    GQLDeletePagePayload {
  message: string;
}

export interface GQLSuccessPayload
  extends GQLAddWidgetPayload,
    GQLDeleteWidgetPayload,
    GQLMoveWidgetPayload,
    GQLAddToolbarActionPayload,
    GQLDeleteToolbarActionPayload,
    GQLMoveToolbarActionPayload,
    GQLAddGroupPayload,
    GQLDeleteGroupPayload,
    GQLMoveGroupPayload,
    GQLAddPagePayload,
    GQLMovePagePayload,
    GQLDeletePagePayload {
  id: string;
}

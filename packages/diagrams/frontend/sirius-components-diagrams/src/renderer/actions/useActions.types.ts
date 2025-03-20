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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseActionsProps {
  diagramElementId: string;
  targetElementId: string;
  nodeDescriptionId: string;
}

export interface GQLGetActionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}

export interface GQLGetActionsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  nodeDescriptions: GQLNodeDescription[];
}

export interface GQLNodeDescription {
  id: string;
  actions: GQLAction[];
}

export interface GQLAction {
  id: string;
  label: string;
  iconURL: string[];
}

export interface GQLInvokeActionPayload {
  __typename: string;
}

export interface GQLInvokeActionData {
  invokeAction: GQLInvokeActionPayload;
}

export interface GQLInvokeActionSuccessPayload extends GQLInvokeActionPayload {
  id: string;
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLInvokeActionPayload {
  message: string;
  messages: GQLMessage[];
}

export interface GQLInvokeActionVariables {
  input: GQLInvokeActionInput;
}

export interface GQLInvokeActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  targetElementId: string;
  actionId: string;
}

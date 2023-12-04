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

export interface GQLPortalEventSubscription {
  portalEvent: GQLPortalEventPayload;
}

export interface GQLPortalEventPayload {
  id: string;
  __typename: string;
}

export interface GQLErrorPayload extends GQLPortalEventPayload {
  message: string;
}

export interface GQLPortalEventVariables {
  input: GQLPortalEventInput;
}

export interface GQLPortalEventInput {
  id: string;
  editingContextId: string;
  portalId: string;
}

export interface GQLPortalRefreshedEventPayload extends GQLPortalEventPayload {
  id: string;
  portal: GQLPortal;
}

export interface GQLPortal {
  id: string;
  label: string;
  views: GQLPortalView[];
}

export interface GQLPortalView {
  id: string;
  representationMetadata: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLAddPortalViewMutationVariables {
  input: GQLAddPortalViewInput;
}

export interface GQLAddPortalViewInput {
  id: string;
  editingContextId: string;
  representationId: string;
  viewRepresentationId: string;
}

export interface GQLAddPortalViewMutationData {
  removePortalView: GQLPayload;
}

export interface GQLRemovePortalViewMutationVariables {
  input: GQLRemovePortalViewInput;
}

export interface GQLRemovePortalViewInput {
  id: string;
  editingContextId: string;
  representationId: string;
  portalViewId: string;
}

export interface GQLRemovePortalViewMutationData {
  removePortalView: GQLPayload;
}

export interface GQLPayload {
  __typename: string;
}

export interface PortalRepresentationState {
  id: string;
  portalRefreshedEventPayload: GQLPortalRefreshedEventPayload | null;
  complete: boolean;
  message: string | null;
}

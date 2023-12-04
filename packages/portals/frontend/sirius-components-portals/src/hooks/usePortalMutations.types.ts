/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export interface UsePortalMutationsValue {
  addPortalView: (viewRepresentationId: string) => void;
  removePortalView: (portalViewId: string) => void;
  layoutPortal: (layoutData: GQLLayoutPortalLayoutData[]) => void;
}

// mutation addPortalView
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
  addPortalView: GQLPayload;
}

// mutation removePortalView
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

// mutation layoutPortal
export interface GQLLayoutPortalViewMutationVariables {
  input: GQLLayoutPortalMutationVariables;
}

export interface GQLLayoutPortalMutationVariables {
  id: string;
  editingContextId: string;
  representationId: string;
  layoutData: GQLLayoutPortalLayoutData[];
}

export interface GQLLayoutPortalLayoutData {
  portalViewId: string;
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface GQLLayoutPortalViewMutationData {
  layoutPortalView: GQLPayload;
}

export interface GQLPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLPayload {
  message: string;
}

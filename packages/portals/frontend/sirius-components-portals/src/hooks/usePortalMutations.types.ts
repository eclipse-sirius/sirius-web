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

import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UsePortalMutationsValue {
  addPortalView: (viewRepresentationId: string, x: number, y: number, width: number, height: number) => void;
  removePortalView: (portalViewId: string) => void;
  layoutPortal: (layoutData: GQLLayoutPortalLayoutData[]) => void;
  layoutInProgress: boolean;
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
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface GQLAddPortalViewMutationData {
  addPortalView: GQLAddPortalViewPayload;
}

export type GQLAddPortalViewPayload = GQLErrorPayload | GQLSuccessPayload;

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
  removePortalView: GQLRemovePortalViewPayload;
}

export type GQLRemovePortalViewPayload = GQLErrorPayload | GQLSuccessPayload;

// mutation layoutPortal
export interface GQLLayoutPortalMutationVariables {
  input: GQLLayoutPortalInput;
}

export interface GQLLayoutPortalInput {
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

export interface GQLLayoutPortalMutationData {
  layoutPortal: GQLLayoutPortalPayload;
}

export type GQLLayoutPortalPayload = GQLErrorPayload | GQLSuccessPayload;

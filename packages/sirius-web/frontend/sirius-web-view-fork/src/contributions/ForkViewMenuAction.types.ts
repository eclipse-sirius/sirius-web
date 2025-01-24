/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

export interface GQLCreateForkedStudioMutationData {
  createForkedStudio: CreateForkedStudioPayload;
}

export interface CreateForkedStudioPayload {
  __typename: string;
}

export interface CreateProjectSuccessPayload extends CreateForkedStudioPayload {
  project: GQLProject;
}

export interface GQLProject {
  id: string;
}

export interface GQLErrorPayload extends CreateForkedStudioPayload {
  message: string;
}

export interface GQLCreateForkedStudioInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
}

export interface ForkViewMenuActionStates {
  isOpen: boolean;
}

export type ForkViewMenuActionProps = {
  editingContextId: string;
  representationId: string;
  tableId: string;
};

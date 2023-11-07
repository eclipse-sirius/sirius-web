/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
export interface DeleteProjectModalProps {
  projectId: string;
  onDelete: () => void;
  onClose: () => void;
}

export interface GQLDeleteProjectMutationData {
  deleteProject: GQLDeleteProjectPayload;
}

export interface GQLDeleteProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDeleteProjectPayload {
  message: string;
}

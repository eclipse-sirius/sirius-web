/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { RepresentationMetadata } from '../workbench/Workbench.types';

export interface UseRepresentationMetadataValue {
  representationMetadata: (
    editingContextId: string,
    representationIds: string[],
    onRetrieveRepresentationMetadataSuccess: (representationMetadata: RepresentationMetadata[]) => void
  ) => void;
}

export interface GQLRepresentationMetadataQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}
export interface GQLEditingContext {
  representation: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLRepresentationMetadataQueryVariables {
  editingContextId: string;
  representationId: string;
}

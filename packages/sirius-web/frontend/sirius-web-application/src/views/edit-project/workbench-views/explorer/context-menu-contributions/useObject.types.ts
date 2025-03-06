/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

export interface UseObjectValue {
  data: GQLGetObjectQueryData | null;
  loading: boolean;
}

export interface GQLGetObjectQueryVariables {
  editingContextId: string;
  objectId: string;
}

export interface GQLGetObjectQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  id: string;
  object: GQLObject | null;
}

export type GQLObject = {
  library: GQLLibrary | null;
};

export type GQLLibrary = {
  namespace: string;
  name: string;
  version: string;
};

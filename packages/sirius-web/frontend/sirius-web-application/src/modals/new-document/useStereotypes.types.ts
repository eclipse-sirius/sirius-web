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

export interface UseStereotypesValue {
  data: GQLGetStereotypesQueryData | null;
  loading: boolean;
}

export interface GQLGetStereotypesQueryVariables {
  editingContextId: string;
}

export interface GQLGetStereotypesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  stereotypes: GQLEditingContextStereotypesConnection;
}

export interface GQLEditingContextStereotypesConnection {
  edges: GQLEditingContextStereotypesEdge[];
}

export interface GQLEditingContextStereotypesEdge {
  node: GQLStereotype;
}

export interface GQLStereotype {
  id: string;
  label: string;
}

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

export interface UseContainmentFeatureNamesValue {
  containmentFeatureNames: ContainmentFeature[];
}

export interface ContainmentFeature {
  id: string;
  label: string;
}

export interface GQLGetContainmentFeatureNamesQueryVariables {
  editingContextId: string;
  containerId: string | null;
  containedObjectId: string;
}

export interface GQLGetContainmentFeatureNamesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  containmentFeatureNames: ContainmentFeature[];
}

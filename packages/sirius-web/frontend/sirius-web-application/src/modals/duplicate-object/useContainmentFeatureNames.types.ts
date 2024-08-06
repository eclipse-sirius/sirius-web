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

import { LazyQueryExecFunction } from '@apollo/client';
import { GQLErrorPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseContainmentFeatureNamesValue {
  getContainmentFeatureNames: LazyQueryExecFunction<
    GQLGetContainmentFeatureNamesQueryData,
    GQLGetContainmentFeatureNamesQueryVariables
  >;
  containmentFeatureNames: string[];
  loading: boolean;
}

export interface GQLGetContainmentFeatureNamesQueryVariables {
  editingContextId: string;
  containerId: string;
  containedObjectId: string;
}

export interface GQLGetContainmentFeatureNamesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  containmentFeatureNames: GQLGetContainmentFeatureNamesPayload;
}

export interface GQLGetContainmentFeatureNamesSuccessPayload {
  __typename: 'EditingContextContainmentFeatureNamesSuccessPayload';
  id: string | null;
  containmentFeatureNames: string[];
}

export type GQLGetContainmentFeatureNamesPayload = GQLErrorPayload | GQLGetContainmentFeatureNamesSuccessPayload;

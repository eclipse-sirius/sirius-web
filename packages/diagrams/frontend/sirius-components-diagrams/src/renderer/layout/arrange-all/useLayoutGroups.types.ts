/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { GQLLayoutConfiguration } from './useLayoutConfigurations.types';

export interface UseLayoutGroupsValue {
  loadLayoutGroups: () => Promise<GQLLayoutGroup[]>;
}

export interface GQLGetLayoutGroupsVariables {
  editingContextId: string;
  representationId: string;
}

export interface GQLGetLayoutGroupsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  layoutGroups: GQLLayoutGroup[];
}

export interface GQLLayoutGroup {
  id: string;
  nodeIds: string[];
  layoutConfiguration?: GQLLayoutConfiguration;
}

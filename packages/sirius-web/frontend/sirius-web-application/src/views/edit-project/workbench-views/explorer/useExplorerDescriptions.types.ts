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
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';

export interface UseExplorerDescriptionValue {
  explorerDescriptions: TreeDescriptionMetadata[];
  loading: boolean;
}

export interface GQLGetAllExplorerDescriptionsVariables {
  editingContextId: string;
}

export interface GQLGetAllExplorerDescriptionsData {
  viewer: GQLGetAllExplorerDescriptionsViewer;
}

export interface GQLGetAllExplorerDescriptionsViewer {
  editingContext: GQLGetAllExplorerDescriptionsEditingContext;
}

export interface GQLGetAllExplorerDescriptionsEditingContext {
  explorerDescriptions: GQLExplorerDescription[];
}

export interface GQLExplorerDescription {
  id: string;
  label: string;
}

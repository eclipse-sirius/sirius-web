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

export interface UseProjectAndRepresentationMetadataValue {
  data: GQLGetProjectAndRepresentationMetadataQueryData | null;
  loading: boolean;
}

export interface GQLGetProjectAndRepresentationMetadataQueryVariables {
  projectId: string;
  name: string | null;
  representationId: string;
  includeRepresentation: boolean;
}

export interface GQLGetProjectAndRepresentationMetadataQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject | null;
}

export interface GQLProject {
  id: string;
  name: string;
  natures: GQLNature[];
  capabilities: GQLProjectCapabilities;
  currentEditingContext: GQLEditingContext | null;
}

export interface GQLNature {
  name: string;
}

export interface GQLProjectCapabilities {
  canDownload: boolean;
  canRename: boolean;
  canDelete: boolean;
  canEdit: boolean;
  canDuplicate: boolean;
  settings: GQLProjectSettingsCapabilities;
}

export interface GQLProjectSettingsCapabilities {
  canView: boolean;
}

export interface GQLEditingContext {
  id: string;
  representation?: GQLRepresentationMetadata;
}

export type GQLRepresentationMetadata = {
  id: string;
  label: string;
  kind: string;
  iconURLs: string[];
  description: GQLRepresentationDescription;
};

export interface GQLRepresentationDescription {
  id: string;
}

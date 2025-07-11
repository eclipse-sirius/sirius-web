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

export interface UseViewerValue {
  data: GQLGetViewerQueryData | null;
  loading: boolean;
}
export interface GQLGetViewerQueryVariables {}

export interface GQLGetViewerQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  capabilities: GQLViewerCapabilities;
}

export interface GQLViewerCapabilities {
  projects: GQLProjectsCapabilities;
  libraries: GQLLibrariesCapabilities;
}

export interface GQLProjectsCapabilities {
  canCreate: boolean;
  canUpload: boolean;
}

export interface GQLLibrariesCapabilities {
  canView: boolean;
}

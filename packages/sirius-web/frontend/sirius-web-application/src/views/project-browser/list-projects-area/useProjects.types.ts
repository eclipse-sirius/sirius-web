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

export interface UseProjectsValue {
  data: GQLGetProjectsQueryData | null;
  loading: boolean;
  refreshProjects: () => void;
}

export interface GQLGetProjectsQueryVariables {
  after: string | null;
  before: string | null;
  first: number | null;
  last: number | null;
  filter: GQLProjectFilter | null;
}

export interface GQLProjectFilter {
  name?: GQLStringFilterOperation;

  [key: string]: GQLStringFilterOperation;
}

export interface GQLStringFilterOperation {
  contains?: string;
}

export interface GQLGetProjectsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  projects: GQLViewerProjectConnection;
}

export interface GQLViewerProjectConnection {
  edges: GQLViewerProjectEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerProjectEdge {
  node: GQLProject;
  cursor: string;
}

export interface GQLProject {
  id: string;
  name: string;
  capabilities: GQLProjectCapabilities;
}

export interface GQLProjectCapabilities {
  canDownload: boolean;
  canRename: boolean;
  canDelete: boolean;
  canDuplicate: boolean;
}

export interface GQLPageInfo {
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  startCursor: string | null;
  endCursor: string | null;
  count: number;
}

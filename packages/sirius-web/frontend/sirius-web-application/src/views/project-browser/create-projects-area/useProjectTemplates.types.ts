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

export interface UseProjectTemplatesValue {
  data: GQLgetProjectTemplatesQueryData | null;
  loading: boolean;
}

export interface GQLgetProjectTemplatesQueryVariables {
  page: number;
  limit: number;
}

export interface GQLgetProjectTemplatesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  projectTemplates: GQLViewerProjectTemplateConnection;
}

export interface GQLViewerProjectTemplateConnection {
  edges: GQLViewerProjectTemplateEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerProjectTemplateEdge {
  node: GQLProjectTemplate;
}

export interface GQLProjectTemplate {
  id: string;
  label: string;
  imageURL: string;
}

export interface GQLPageInfo {
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  count: number;
}

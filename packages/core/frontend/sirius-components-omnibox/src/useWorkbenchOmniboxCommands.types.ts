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

import { LazyQueryExecFunction } from '@apollo/client';

export interface UseWorkbenchOmniboxCommandsValue {
  getWorkbenchOmniboxCommands: LazyQueryExecFunction<
    GQLGetWorkbenchOmniboxCommandsQueryData,
    GQLGetWorkbenchOmniboxCommandsQueryVariables
  >;
  loading: boolean;
  data: GQLGetWorkbenchOmniboxCommandsQueryData | null;
}

export interface GQLGetWorkbenchOmniboxCommandsQueryVariables {
  editingContextId: string;
  selectedObjectIds: string[];
  query: string;
}

export interface GQLGetWorkbenchOmniboxCommandsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  workbenchOmniboxCommands: GQLViewerOmniboxCommandsConnection;
}

export interface GQLViewerOmniboxCommandsConnection {
  edges: GQLViewerOmniboxCommandsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerOmniboxCommandsEdge {
  node: GQLOmniboxCommand;
}

export interface GQLOmniboxCommand {
  id: string;
  label: string;
  iconURLs: string[];
  description: string;
}

export interface GQLPageInfo {
  count: number;
}

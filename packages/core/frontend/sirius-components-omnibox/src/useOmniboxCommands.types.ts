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

export interface UseOmniboxCommandsValue {
  getOmniboxCommands: LazyQueryExecFunction<GQLGetOmniboxCommandsQueryData, GQLGetOmniboxCommandsQueryVariables>;
  loading: boolean;
  data: GQLGetOmniboxCommandsQueryData | null;
}

export interface GQLGetOmniboxCommandsQueryVariables {
  contextEntries: GQLOmniboxContextEntry[];
  query: string;
}

export interface GQLOmniboxContextEntry {
  id: string;
  kind: string;
}

export interface GQLGetOmniboxCommandsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  omniboxCommands: GQLViewerOmniboxCommandsConnection;
}

export interface GQLViewerOmniboxCommandsConnection {
  edges: GQLViewerOmniboxCommandsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerOmniboxCommandsEdge {
  node: GQLViewerOmniboxCommand;
}

export interface GQLViewerOmniboxCommand {
  id: string;
  label: string;
  kind: string;
  iconURLs: string[];
  description: string;
  __typename: string;
}

export interface GQLPageInfo {
  count: number;
}

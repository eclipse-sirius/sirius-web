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
  getOmniboxCommands: LazyQueryExecFunction<GQLGetOmniboxCommandsQueryVariables, GQLGetOmniboxCommandsQueryData>;
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

export interface GQLGetOmniboxCommandsQueryData {}

export interface GQLViewer {}

export interface GQLViewerOmniboxCommandsConnection {
  edges: GQLViewerOmniboxCommandsEdge;
  pageInfo: GQLPageInfo;
}

export interface GQLViewerOmniboxCommandsEdge {
  node: GQLViewerOmniboxCommand;
}

export interface GQLViewerOmniboxCommand {
  id: string;
  label: string;
  iconURLs: string[];
  description: string;
  __typename: OmniboxCommandTypeName;
}

export type OmniboxCommandTypeName = 'OmniboxExternalNavigationCommand' | 'OmniboxSelectionCommand';

export interface GQLPageInfo {
  count: number;
}

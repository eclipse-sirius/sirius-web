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

export interface UseProjectStyleCustomizationsValue {
  data: GQLGetProjectStyleCustomizationsQueryData | null;
  loading: boolean;
}

export interface GQLGetProjectStyleCustomizationsQueryVariables {
  projectId: string;
  after: string | null;
  before: string | null;
  first: number | null;
  last: number | null;
}

export interface GQLGetProjectStyleCustomizationsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject | null;
}

export interface GQLProject {
  styleCustomizations: GQLStyleCustomizationsConnection;
}

export interface GQLStyleCustomizationsConnection {
  edges: GQLStyleCustomizationsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLStyleCustomizationsEdge {
  node: GQLStyleCustomization;
}

export interface GQLStyleCustomization {
  id: string;
  name: string;
  description: string;
  enabled: boolean;
}

export interface GQLPageInfo {
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  startCursor: string | null;
  endCursor: string | null;
  count: number;
}

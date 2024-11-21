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

import { GQLProject } from './useProjects.types';

export interface ListProjectsAreaProps {}

export interface ListProjectsAreaState {
  pageSize: number;
  projects: GQLProject[];
  hasPrev: boolean;
  hasNext: boolean;
  startCursor: string | null;
  endCursor: string | null;
  onRefreshProjects: () => void;
  beforeRefreshProjects: boolean;
  afterRefreshProjects: boolean;
  beforeRefreshNeeded: boolean;
  afterRefreshNeeded: boolean;
  previousPageRequired: boolean;
  nextPageRequired: boolean;
}

export interface NoProjectsFoundProps {}

export interface FetchingProjectsProps {}

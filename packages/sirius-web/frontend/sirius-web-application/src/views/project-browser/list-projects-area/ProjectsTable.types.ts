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

export interface ProjectsTableProps {
  projects: GQLProject[];
  page: number;
  limit: number;
  count: number;
  onChange: () => void;
  onPageChange: (page: number) => void;
}

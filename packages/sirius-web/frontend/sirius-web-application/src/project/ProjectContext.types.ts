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

import { GQLProject } from './useProject.types';

export type ProjectContextProviderParams = 'projectId';

export interface ProjectContextValue {
  project: GQLProject;
  name: string | null;
}

export interface ProjectContextProviderProps {
  children: React.ReactNode;
}

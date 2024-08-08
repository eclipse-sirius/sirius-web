/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo and others.
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

import { GQLProject } from './useProjectAndRepresentationMetadata.types';

export type EditProjectViewParams = 'projectId' | 'representationId';
export interface TreeItemContextMenuProviderProps {
  children: React.ReactNode;
}

export interface TreeToolBarProviderProps {
  children: React.ReactNode;
}

export interface DiagramPaletteToolProviderProps {
  children: React.ReactNode;
}

export type ProjectReadOnlyPredicate = (project: GQLProject) => boolean;

/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { WorkbenchViewConfiguration } from '@eclipse-sirius/sirius-components-core';
import { GQLTree, TreeFilter } from '@eclipse-sirius/sirius-components-trees';

export interface ExplorerViewState {
  filterBar: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
  tree: GQLTree | null;
}

export interface ExplorerViewConfiguration extends WorkbenchViewConfiguration {
  activeTreeDescriptionId: string | null;
  activeTreeFilters: TreeFilter[];
}

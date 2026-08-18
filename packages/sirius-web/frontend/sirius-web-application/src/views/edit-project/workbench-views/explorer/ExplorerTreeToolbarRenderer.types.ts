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
import { TreeFilter } from '@eclipse-sirius/sirius-components-trees';
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';

export interface ExplorerTreeToolbarRendererProps {
  editingContextId: string;
  activeTreeDescriptionId: string;
  readOnly: boolean;
  explorerDescriptions: TreeDescriptionMetadata[];
  treeFilters: TreeFilter[];
  resetTree: () => void;
  onFilter: () => void;
  setTreeFilters: (treeFilters: TreeFilter[]) => void;
  setActiveDescriptionId: (activeDescriptionId: string) => void;
  onRevealSelection: () => void;
}

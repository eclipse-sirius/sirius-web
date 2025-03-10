/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';

export interface ModelBrowserTreeViewProps {
  editingContextId: string;
  referenceKind: string;
  ownerId: string;
  descriptionId: string;
  isContainment: boolean;
  markedItemIds: string[];
  title: string;
  leafType: 'reference' | 'container';
  ownerKind: string;
  onTreeItemClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem) => void;
  selectedTreeItemIds: string[];
}

export interface ModelBrowserTreeViewState {
  filterBarText: string;
  expanded: string[];
  maxDepth: number;
}

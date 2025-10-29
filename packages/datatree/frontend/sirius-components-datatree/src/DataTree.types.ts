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
import { GQLStyledString } from '@eclipse-sirius/sirius-components-core';

export interface DataTreeProps {
  dataTree: GQLDataTree;
}

export interface GQLDataTree {
  id: string;
  label: string;
  iconURLs: string[];
  nodes: GQLDataTreeNode[];
}

export interface GQLDataTreeNode {
  id: string;
  parentId: string;
  label: GQLStyledString;
  iconURLs: string[];
  endIconsURLs: string[][];
}

/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLSubscriber, GQLTree, GQLTreeNode } from '../form/FormEventFragments.types';

export interface TreePropertySectionProps {
  widget: GQLTree;
  subscribers: GQLSubscriber[];
  setSelection: (selection: Selection) => void;
}

export interface TreeItemProps {
  node: GQLTreeNode;
  nodes: GQLTreeNode[];
  setSelection: (selection: Selection) => void;
}

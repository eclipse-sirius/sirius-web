/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { GQLTree } from '../views/ExplorerView.types';

export interface TreeProps {
  editingContextId: string;
  tree: GQLTree;
  onExpand: (id: string, depth: number) => void;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

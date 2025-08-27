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

import React from 'react';
import { TreePaletteContextValue } from './TreePaletteContext.types';

const defaultValue: TreePaletteContextValue = {
  editingContextId: '',
  treeId: '',
  item: null,
  readOnly: false,
  expanded: [],
  selectedTreeItems: [],
  onDirectEditClick: () => {},
  selectTreeItems: () => {},
  expandItem: () => {},
  onClose: () => {},
  onExpandedElementChange: () => {},
};

export const TreePaletteContext = React.createContext<TreePaletteContextValue>(defaultValue);

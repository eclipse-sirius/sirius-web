/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { TreeItemType } from './TreeItem.types';
import { TreeItemHandler } from './TreeItemContextMenu.types';

// Catch-all, used when no custom handler is found for an item.
const defaultItemHandler: TreeItemHandler = {
  handles: (item) => true,
  getModal: (name) => null,
  getMenuEntries: (item) => {
    return [];
  },
};

const handlers = [];

const value = {
  registerTreeItemHandler(handler: TreeItemHandler): void {
    handlers.unshift(handler);
  },
  getTreeItemHandler(item: TreeItemType): TreeItemHandler {
    return handlers.find((entry) => entry.handles(item)) ?? defaultItemHandler;
  },
};

export const TreeItemHandlersContext = React.createContext(value);

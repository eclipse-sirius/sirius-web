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

import { Palette } from '@eclipse-sirius/sirius-components-palette';
import React from 'react';
import { DEFAULT_TOOL_LIST_ITEMS } from './toolListItems/DefaultToolListItems';
import { GQLPalette, GQLTool, PaletteProps } from './TreeItemPalette.types';
import { useInvokePaletteTool } from './useInvokePaletteTool';

const palette: GQLPalette = {
  id: '',
  quickAccessTools: [],
  paletteEntries: DEFAULT_TOOL_LIST_ITEMS,
};

export const TreeItemPalette = ({ editingContextId, treeId, treeItem, onDirectEditClick }: PaletteProps) => {
  const nodeRef = React.useRef<HTMLDivElement>(null);

  const { invokeTool } = useInvokePaletteTool();

  const handleToolClick = (tool: GQLTool) => {
    invokeTool(editingContextId, treeId, treeItem, onDirectEditClick, tool);
  };

  return (
    <Palette
      ref={nodeRef}
      x={0}
      y={0}
      representationDescriptionId=""
      representationElementIds={[treeItem.id]}
      palette={palette}
      onToolClick={handleToolClick}
      onClose={() => {}}
      paletteToolListExtensions={[]}
    />
  );
};

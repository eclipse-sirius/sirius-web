/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { isSingleClickOnDiagramElementTool } from '../Palette';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { PaletteToolSectionListProps } from './PaletteToolSectionList.types';

export const PaletteToolSectionList = ({ toolSection, onToolClick }: PaletteToolSectionListProps) => {
  return toolSection.tools
    .filter(isSingleClickOnDiagramElementTool)
    .map((tool) => <ToolListItem onToolClick={onToolClick} tool={tool} disabled={false} key={tool.id} />);
};

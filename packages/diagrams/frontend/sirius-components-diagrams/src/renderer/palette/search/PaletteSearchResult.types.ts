/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { PaletteEntry, Tool } from './../draggable-palette/DraggablePalette.types';
export interface PaletteSearchResultProps {
  onToolClick: (tool: Tool) => void;
  searchToolValue: string;
  paletteEntries: PaletteEntry[];
}

export interface HighlightedLabelProps {
  label: string;
  textToHighlight: string;
}

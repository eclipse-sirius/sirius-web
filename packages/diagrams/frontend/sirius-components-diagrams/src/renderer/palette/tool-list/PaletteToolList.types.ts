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

import { PaletteEntry, Tool, ToolSection } from './../draggable-palette/DraggablePalette.types';

export interface PaletteToolListProps {
  onToolClick: (tool: Tool) => void;
  paletteEntries: PaletteEntry[];
  lastToolInvoked: Tool | null;
}

export interface PaletteToolListStateValue {
  toolSection: ToolSection | null;
}

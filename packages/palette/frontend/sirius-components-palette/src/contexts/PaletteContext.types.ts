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

import { GQLTool } from '../Palette.types';

export interface PaletteContextValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  representationElementIds: string[];
  showPalette: (x: number, y: number, representationElementIds: string[]) => void;
  hidePalette: () => void;
  getLastToolInvoked: (paletteId: string) => GQLTool | null;
  setLastToolInvoked: (paletteId: string, tool: GQLTool) => void;
}

export interface PaletteContextProviderProps {
  children: React.ReactNode;
}

export interface PaletteContextProviderState {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  representationElementIds: string[];
  lastToolsInvoked: PaletteWithLastTool[];
}

export interface PaletteWithLastTool {
  paletteId: string;
  lastTool: GQLTool;
}

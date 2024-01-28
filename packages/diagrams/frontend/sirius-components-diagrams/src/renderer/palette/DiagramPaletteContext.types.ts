/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { GQLTool } from './Palette.types';

export interface DiagramPaletteContextValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  showDiagramPalette: (x: number, y: number) => void;
  hideDiagramPalette: () => void;
  getLastToolInvoked: (toolSectionId: string) => GQLTool | null;
  setLastToolInvoked: (toolSectionId: string, tool: GQLTool) => void;
}

export interface DiagramPaletteContextProviderProps {
  children: React.ReactNode;
}

export interface DiagramPaletteContextProviderState {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  lastToolsInvoked: ToolSectionWithLastTool[];
}

export interface ToolSectionWithLastTool {
  toolSectionId: string;
  lastTool: GQLTool;
}

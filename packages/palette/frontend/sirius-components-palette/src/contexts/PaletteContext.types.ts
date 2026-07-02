/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

export interface PaletteContextValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  representationElementIds: string[];
  showPalette: (x: number, y: number, representationElementIds: string[]) => void;
  hidePalette: () => void;
  getLastToolInvokedId: (paletteId: string) => string | null;
  setLastToolInvokedId: (paletteId: string, toolId: string) => void;
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
  lastToolId: string;
}

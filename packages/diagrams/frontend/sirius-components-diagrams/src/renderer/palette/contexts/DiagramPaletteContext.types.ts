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

export interface DiagramPaletteContextValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  diagramElementIds: string[];
  showDiagramPalette: (x: number, y: number, diagramElementIds: string[]) => void;
  hideDiagramPalette: () => void;
  getLastToolInvokedId: (paletteId: string) => string | null;
  setLastToolInvokedId: (paletteId: string, toolId: string) => void;
}

export interface DiagramPaletteContextProviderProps {
  children: React.ReactNode;
}

export interface DiagramPaletteContextProviderState {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  diagramElementIds: string[];
  lastToolsInvoked: PaletteWithLastTool[];
}

export interface PaletteWithLastTool {
  paletteId: string;
  lastToolId: string | null;
}

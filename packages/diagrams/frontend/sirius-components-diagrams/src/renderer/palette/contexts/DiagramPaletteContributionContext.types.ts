/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export interface DiagramPaletteContributionContextValue {
  paletteId: string;
  x: number | null;
  y: number | null;
  diagramElementIds: string[];
  hideDiagramPalette: () => void;
  setLastToolInvokedId: (paletteId: string, toolId: string) => void;
}

export interface DiagramPaletteContributionContextProviderProps {
  paletteId: string;
  x: number | null;
  y: number | null;
  diagramElementIds: string[];
  hideDiagramPalette: () => void;
  setLastToolInvokedId: (paletteId: string, toolId: string) => void;
  children: React.ReactNode;
}

export interface PaletteWithLastTool {
  paletteId: string;
  lastToolId: string | null;
}

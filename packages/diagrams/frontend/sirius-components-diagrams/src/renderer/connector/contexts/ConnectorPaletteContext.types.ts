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

import { GQLTool } from '../../palette/Palette.types';

export interface ConnectorPaletteContextValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  sourceDiagramElementId: string | null;
  targetDiagramElementId: string | null;
  candidateDescriptionIds: string[];
  isConnectionInProgress: boolean;
  showConnectorPalette: (x: number, y: number, sourceDiagramElementId: string, targetDiagramElementId: string) => void;
  hideConnectorPalette: () => void;
  setCandidateDescriptionIds: (candidateDescriptionIds: string[]) => void;
  getLastToolInvoked: (paletteId: string) => GQLTool | null;
  setLastToolInvoked: (paletteId: string, tool: GQLTool) => void;
}

export interface ConnectorPaletteContextProviderProps {
  children: React.ReactNode;
}

export interface ConnectorPaletteContextProviderState {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  sourceDiagramElementId: string | null;
  targetDiagramElementId: string | null;
  candidateDescriptionIds: string[];
  lastToolsInvoked: PaletteWithLastTool[];
}

export interface PaletteWithLastTool {
  paletteId: string;
  lastTool: GQLTool;
}

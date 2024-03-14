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

export interface UseDiagramPaletteValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  hideDiagramPalette: () => void;
  showDiagramPalette: (x: number, y: number) => void;
  onDiagramBackgroundClick: (event: React.MouseEvent<Element, MouseEvent>) => void;
  onDiagramElementClick: () => void;
  getLastToolInvoked: (toolSectionId: string) => GQLTool | null;
  setLastToolInvoked: (toolSectionId: string, tool: GQLTool) => void;
}

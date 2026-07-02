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

export interface usePaletteValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  representationElementIds: string[];
  hidePalette: () => void;
  showPalette: (x: number, y: number, selectedElementsIds: string[]) => void;
  getLastToolInvokedId: (paletteId: string) => string | null;
  setLastToolInvokedId: (paletteId: string, toolId: string) => void;
}

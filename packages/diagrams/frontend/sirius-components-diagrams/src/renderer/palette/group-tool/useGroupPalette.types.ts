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

import { XYPosition } from 'reactflow';

export interface UseGroupPaletteValue {
  position: XYPosition | null;
  isOpened: boolean;
  hideGroupPalette: () => void;
  onDiagramGroupElementClick: (event: React.MouseEvent<Element, MouseEvent>) => void;
}

export interface UseGroupPaletteState {
  position: XYPosition | null;
  isOpened: boolean;
}

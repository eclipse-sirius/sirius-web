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
import { GQLPalette, GQLTool, PaletteExtensionSectionProps } from '@eclipse-sirius/sirius-components-palette';
import { XYPosition } from '@xyflow/react';

export interface DraggablePaletteState {
  controlledPosition: XYPosition;
}

export interface DraggablePaletteProps {
  representationKind: string;
  representationElementIds: string[];
  palette: GQLPalette;
  paletteToolListExtensions: React.ReactElement<PaletteExtensionSectionProps>[];
  onToolClick: (tool: GQLTool) => void;
  onClose: () => void;
  x: number;
  y: number;
}

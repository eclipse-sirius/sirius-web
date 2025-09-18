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

import { GQLTool, PaletteExtensionSectionProps } from '@eclipse-sirius/sirius-components-palette';
import { XYPosition } from '@xyflow/react';

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

export interface PaletteProps {
  x: number;
  y: number;
  diagramElementId: string;
  targetObjectId: string;
  onDirectEditClick: () => void;
  onClose: () => void;
  children: React.ReactElement<PaletteExtensionSectionProps>[];
}

export interface PaletteState {
  searchToolValue: string;
  controlledPosition: XYPosition;
}

export interface PaletteStyleProps {
  paletteWidth: string;
  paletteHeight: string;
}

export interface GQLSingleClickOnDiagramElementTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  dialogDescriptionId: string;
  withImpactAnalysis: boolean;
}

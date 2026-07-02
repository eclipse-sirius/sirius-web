/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import { GQLPalette, GQLTool } from '../Palette.types';
import { PaletteExtensionSectionProps } from '../PaletteExtensionSection.types';

export interface PaletteToolSectionProps {
  representationKind: string;
  representationElementIds: string[];
  palette: GQLPalette;
  onToolClick: (tool: GQLTool) => void;
  onBackToMainList: () => void;
  onClose: () => void;
  lastToolInvokedId: string | null;
  extensionSections: React.ReactElement<PaletteExtensionSectionProps>[];
}

export interface PaletteToolSectionStateValue {
  currentSectionId: string | undefined;
  history: (string | undefined)[];
  direction: 'left' | 'right';
}

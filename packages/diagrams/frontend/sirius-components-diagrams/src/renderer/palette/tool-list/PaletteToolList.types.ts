/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { GQLPalette, GQLTool, GQLToolSection } from '../Palette.types';
import { PaletteExtensionSectionProps } from '../PaletteExtensionSection.types';

export interface PaletteToolListProps {
  onToolClick: (tool: GQLTool) => void;
  palette: GQLPalette;
  onBackToMainList: () => void;
  diagramElementId: string;
  children: React.ReactElement<PaletteExtensionSectionProps>[];
}

export interface PaletteToolListStateValue {
  toolSection: GQLToolSection | null;
  extensionSection: string | null;
}

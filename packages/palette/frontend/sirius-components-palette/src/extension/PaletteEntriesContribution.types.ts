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
import { GQLTool } from '../Palette.types';

export interface PaletteEntriesContributionProps {
  id: string;
  canHandle: (representationElementIds: string[]) => boolean;
  component: React.ComponentType<PaletteExtensionEntryComponentProps>;
  sectionComponent: React.ComponentType<PaletteExtensionSectionComponentProps> | null;
}

export interface PaletteExtensionEntryComponentProps {
  onToolClick: (tool: GQLTool) => void;
  onToolSectionClick: (event: React.MouseEvent<HTMLDivElement>, toolSectionId: string) => void;
}

export interface PaletteExtensionSectionComponentProps {
  onBackToMainList: () => void;
  onClose: () => void;
  representationElementIds: string[];
}

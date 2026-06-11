/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

export { PaletteContext, PaletteContextProvider } from './contexts/PaletteContext';
export type { PaletteQuickToolComponentProps } from './extensions/PaletteQuickToolContribution.types';
export { paletteQuickToolExtensionPoint } from './extensions/PaletteQuickToolExtensionPoints';
export type {
  PaletteToolContributionComponentProps,
  PaletteToolContributionProps,
} from './extensions/PaletteToolContribution.types';
export { paletteToolExtensionPoint } from './extensions/PaletteToolExtensionPoints';
export type {
  PaletteToolOverriddenContributionComponentProps,
  PaletteToolOverriddenContributionProps,
} from './extensions/PaletteToolOverrideContribution.types';
export { paletteToolOverrideExtensionPoint } from './extensions/PaletteToolOverrideExtensionPoints';
export { isPaletteDivider, isSingleClickOnDiagramElementTool, isTool, isToolSection, Palette } from './Palette';
export { PaletteExtensionSection } from './PaletteExtensionSection';
export type {
  PaletteExtensionSectionComponentProps,
  PaletteExtensionSectionProps,
} from './PaletteExtensionSection.types';
export { PaletteQuickAccessToolBar } from './quick-access-tool/PaletteQuickAccessToolBar';
export { Tool } from './quick-access-tool/Tool';
export { PaletteSearchField } from './search/PaletteSearchField';
export { PaletteSearchResult } from './search/PaletteSearchResult';
export { ToolListItem } from './tool-list-item/ToolListItem';
export { PaletteToolSection } from './tool-section/PaletteToolSection';
export { ToolSectionEntry } from './tool-section/ToolSectionEntry';
export { ToolSectionHeader } from './tool-section/ToolSectionHeader';
export { usePalette } from './usePalette';

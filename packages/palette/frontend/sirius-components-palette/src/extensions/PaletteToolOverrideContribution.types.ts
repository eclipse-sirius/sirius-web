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
export interface PaletteToolOverriddenContributionProps {
  canHandle: (representationKind: string, tool: GQLTool) => boolean;
  component: React.ComponentType<PaletteToolOverriddenContributionComponentProps>;
}

export interface PaletteToolOverriddenContributionComponentProps {
  representationElementIds: string[];
  tool: GQLTool;
  /**
   * Callback to call once when the overridden tool is actually considered to have been invoked by the user.
   */
  onInvoked: () => void;
  /**
   * True if the tool is rendered as the last tool used, false otherwise.
   */
  asLastToolUsed: boolean;
  /**
   * True if the tool is in the palette retrieved from the backend
   * Useful if the tool is rendered as the last tool used but is not in the palette because of a precondition and need to be disabled
   */
  isToolInPalette: boolean;
  /**
   * Current text in the search field of the toolbar or null if nothing is being searched.
   */
  searchedValue: string | null;
}

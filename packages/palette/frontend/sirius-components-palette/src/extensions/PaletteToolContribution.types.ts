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

export interface PaletteToolContributionProps {
  id: string;
  sectionId?: string;
  canHandle: (representationKind: string) => boolean;
  component: React.ComponentType<PaletteToolContributionComponentProps>;
}

export interface PaletteToolContributionComponentProps {
  representationElementIds: string[];
  /**
   * Callback to call once when the contributed tool is actually considered to have been invoked by the user.
   */
  onInvoked: () => void;
  /**
   * True if the tool is rendered as the last tool used, false otherwise.
   */
  asLastToolUsed: boolean;
  /**
   * Current text in the search field of the toolbar or null if nothing is being searched.
   */
  searchedValue: string | null;
}

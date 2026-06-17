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

export interface UseTreeStateLocalStorageValue {
  activeTreeDescriptionId: string;
  expanded: string[];
  maxDepth: number;
  setActiveDescriptionId: (activeDescriptionId: string) => void;
  onExpandedElementChange: (newExpandedIds: string[], newMaxDepth: number) => void;
}

export interface UseTreeStateLocalStorageState {
  activeTreeDescriptionId: string;
  expanded: { [key: string]: string[] };
  maxDepth: { [key: string]: number };
}

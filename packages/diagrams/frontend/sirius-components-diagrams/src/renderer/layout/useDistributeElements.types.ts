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

export interface UseDistributeElementsValue {
  distributeGapVertically: (selectedNodeIds: string[]) => void;
  distributeGapHorizontally: (selectedNodeIds: string[]) => void;
  distributeAlignLeft: (selectedNodeIds: string[], refElementId: string | null) => void;
  distributeAlignRight: (selectedNodeIds: string[], refElementId: string | null) => void;
  distributeAlignCenter: (selectedNodeIds: string[], refElementId: string | null) => void;
  distributeAlignTop: (selectedNodeIds: string[], refElementId: string | null) => void;
  distributeAlignBottom: (selectedNodeIds: string[], refElementId: string | null) => void;
  distributeAlignMiddle: (selectedNodeIds: string[], refElementId: string | null) => void;
  justifyHorizontally: (selectedNodeIds: string[], refElementId: string | null) => void;
  justifyVertically: (selectedNodeIds: string[], refElementId: string | null) => void;
  arrangeInRow: (selectedNodeIds: string[]) => void;
  arrangeInColumn: (selectedNodeIds: string[]) => void;
  arrangeInGrid: (selectedNodeIds: string[]) => void;
  makeNodesSameSize: (selectedNodeIds: string[], refElementId: string | null) => void;
}

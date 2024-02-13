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
import { NodeChange } from 'reactflow';

export interface UseHelperLinesValue {
  helperLinesEnabled: boolean;
  setHelperLinesEnabled: (enable: boolean) => void;
  verticalHelperLine: number | null;
  horizontalHelperLine: number | null;
  applyHelperLines: (changes: NodeChange[]) => NodeChange[];
  resetHelperLines: (changes: NodeChange[]) => void;
}

export interface UseHelperLinesState {
  vertical: number | null;
  horizontal: number | null;
}

export type HelperLines = {
  horizontal: number | null;
  vertical: number | null;
  snapX: number | null;
  snapY: number | null;
};

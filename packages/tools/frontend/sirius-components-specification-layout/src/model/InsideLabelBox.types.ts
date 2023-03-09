/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export type InsideLabelPosition =
  | 'TOP_LEFT'
  | 'TOP_CENTER'
  | 'TOP_RIGHT'
  | 'LEFT_TOP'
  | 'LEFT_CENTER'
  | 'LEFT_BOTTOM'
  | 'BOTTOM_LEFT'
  | 'BOTTOM_CENTER'
  | 'BOTTOM_RIGHT'
  | 'RIGHT_BOTTOM'
  | 'RIGHT_CENTER'
  | 'RIGHT_TOP'
  | 'CENTER';

export interface InsideLabelBoxProps {
  label?: string;
  position?: InsideLabelPosition;
  children?: React.ReactNode;
}

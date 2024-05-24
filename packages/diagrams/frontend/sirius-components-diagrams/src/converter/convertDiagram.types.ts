/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export interface AlignmentMapType {
  TOP_CENTER: Alignment;
  TOP_LEFT: Alignment;
  TOP_RIGHT: Alignment;
  BOTTOM_CENTER: Alignment;
  BOTTOM_LEFT: Alignment;
  BOTTOM_RIGHT: Alignment;
  MIDDLE_CENTER: Alignment;
  MIDDLE_LEFT: Alignment;
  MIDDLE_RIGHT: Alignment;
}

export const AlignmentMap: AlignmentMapType = {
  TOP_CENTER: { primaryAlignment: 'TOP', secondaryAlignment: 'CENTER', isPrimaryVerticalAlignment: true },
  TOP_LEFT: { primaryAlignment: 'TOP', secondaryAlignment: 'LEFT', isPrimaryVerticalAlignment: true },
  TOP_RIGHT: { primaryAlignment: 'TOP', secondaryAlignment: 'RIGHT', isPrimaryVerticalAlignment: true },
  BOTTOM_CENTER: { primaryAlignment: 'BOTTOM', secondaryAlignment: 'CENTER', isPrimaryVerticalAlignment: true },
  BOTTOM_LEFT: { primaryAlignment: 'BOTTOM', secondaryAlignment: 'LEFT', isPrimaryVerticalAlignment: true },
  BOTTOM_RIGHT: { primaryAlignment: 'BOTTOM', secondaryAlignment: 'RIGHT', isPrimaryVerticalAlignment: true },
  MIDDLE_CENTER: { primaryAlignment: 'MIDDLE', secondaryAlignment: 'CENTER', isPrimaryVerticalAlignment: true },
  MIDDLE_LEFT: { primaryAlignment: 'MIDDLE', secondaryAlignment: 'LEFT', isPrimaryVerticalAlignment: true },
  MIDDLE_RIGHT: { primaryAlignment: 'MIDDLE', secondaryAlignment: 'RIGHT', isPrimaryVerticalAlignment: true },
};

export type AlignmentValue = 'TOP' | 'BOTTOM' | 'LEFT' | 'RIGHT' | 'CENTER' | 'MIDDLE';

export interface Alignment {
  primaryAlignment: AlignmentValue;
  secondaryAlignment: AlignmentValue;
  isPrimaryVerticalAlignment: boolean;
}

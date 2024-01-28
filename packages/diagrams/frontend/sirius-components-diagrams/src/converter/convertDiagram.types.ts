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
}

export const AlignmentMap: AlignmentMapType = {
  TOP_CENTER: { primaryAlignment: 'TOP', secondaryAlignment: 'CENTER', isPrimaryVerticalAlignment: true },
};

export type AlignmentValue = 'TOP' | 'BOTTOM' | 'LEFT' | 'RIGHT' | 'CENTER';

export interface Alignment {
  primaryAlignment: AlignmentValue;
  secondaryAlignment: AlignmentValue;
  isPrimaryVerticalAlignment: boolean;
}

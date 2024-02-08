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

export interface GQLLabel {
  id: string;
  text: string;
  style: GQLLabelStyle;
}

export interface GQLInsideLabel {
  id: string;
  text: string;
  insideLabelLocation: GQLInsideLabelLocation;
  style: GQLLabelStyle;
  isHeader: boolean;
  displayHeaderSeparator: boolean;
}

export type GQLInsideLabelLocation = 'TOP_CENTER';

export interface GQLOutsideLabel {
  id: string;
  text: string;
  outsideLabelLocation: GQLOutsideLabelLocation;
  style: GQLLabelStyle;
}

export type GQLOutsideLabelLocation = 'BOTTOM_BEGIN' | 'BOTTOM_MIDDLE' | 'BOTTOM_END';

export interface GQLLabelStyle {
  color: string;
  fontSize: number;
  bold: boolean;
  italic: boolean;
  underline: boolean;
  strikeThrough: boolean;
  iconURL: string[];
}

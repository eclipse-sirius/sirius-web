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
  overflowStrategy: GQLLabelOverflowStrategy;
  textAlign: GQLLabelTextAlign;
}

export type GQLInsideLabelLocation =
  | 'TOP_CENTER'
  | 'TOP_LEFT'
  | 'TOP_RIGHT'
  | 'BOTTOM_CENTER'
  | 'BOTTOM_LEFT'
  | 'BOTTOM_RIGHT'
  | 'MIDDLE_CENTER'
  | 'MIDDLE_LEFT'
  | 'MIDDLE_RIGHT';

export type GQLLabelOverflowStrategy = 'NONE' | 'WRAP' | 'ELLIPSIS';

export type GQLLabelTextAlign = 'LEFT' | 'RIGHT' | 'CENTER' | 'JUSTIFY';

export interface GQLOutsideLabel {
  id: string;
  text: string;
  outsideLabelLocation: GQLOutsideLabelLocation;
  style: GQLLabelStyle;
  overflowStrategy: GQLLabelOverflowStrategy;
  textAlign: GQLLabelTextAlign;
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

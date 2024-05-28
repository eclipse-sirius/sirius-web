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
export interface StyledLabelInputProps {
  styledString: GQLStyledString;
  selected: boolean;
  textToHighlight: string;
  marked: boolean;
}

export interface StyledLabelInputState {
  selected: boolean;
}

export interface GQLStyledString {
  styledStringFragments: GQLStyledStringFragment[];
}

export interface GQLStyledStringFragment {
  text: string;
  styledStringFragmentStyle: GQLStyledStringFragmentStyle;
}

export interface GQLStyledStringFragmentStyle {
  font: string;
  backgroundColor: string;
  foregroundColor: string;
  isStrikedout: boolean;
  strikeoutColor: string;
  underlineColor: string;
  borderColor: string;
  borderStyle: GQLBorderStyle;
  underlineStyle: GQLUnderLineStyle;
}

enum GQLUnderLineStyle {
  NONE,
  SOLID,
  DOUBLE,
  DOTTED,
  DASHED,
  WAVY,
}

enum GQLBorderStyle {
  NONE,
  HIDDEN,
  DOTTED,
  DASHED,
  SOLID,
  DOUBLE,
  GROOVE,
  RIDGE,
  INSET,
  OUTSET,
}

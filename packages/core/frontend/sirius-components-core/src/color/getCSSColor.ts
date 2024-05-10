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
import {
  amber,
  blue,
  blueGrey,
  brown,
  common,
  cyan,
  deepOrange,
  deepPurple,
  green,
  grey,
  indigo,
  lightBlue,
  lightGreen,
  lime,
  orange,
  pink,
  purple,
  red,
  teal,
  yellow,
} from '@material-ui/core/colors';
import { Theme } from '@material-ui/core/styles';

const muiColors = {
  amber: amber,
  blue: blue,
  blueGrey: blueGrey,
  brown: brown,
  common: common,
  cyan: cyan,
  deepOrange: deepOrange,
  deepPurple: deepPurple,
  green: green,
  grey: grey,
  indigo: indigo,
  lightBlue: lightBlue,
  lightGreen: lightGreen,
  lime: lime,
  orange: orange,
  pink: pink,
  purple: purple,
  red: red,
  teal: teal,
  yellow: yellow,
};

export const getCSSColor = (value: string | undefined, theme: Theme): string | undefined => {
  let cssColor: string | undefined = value;
  if (value) {
    if (value === 'theme.palette.primary.main') {
      cssColor = theme.palette.primary.main;
    } else if (value === 'theme.palette.primary.light') {
      cssColor = theme.palette.primary.light;
    } else if (value === 'theme.palette.primary.dark') {
      cssColor = theme.palette.primary.dark;
    } else if (value === 'theme.palette.secondary.main') {
      cssColor = theme.palette.secondary.main;
    } else if (value === 'theme.palette.secondary.light') {
      cssColor = theme.palette.secondary.light;
    } else if (value === 'theme.palette.secondary.dark') {
      cssColor = theme.palette.secondary.dark;
    } else if (value === 'theme.palette.text.primary') {
      cssColor = theme.palette.text.primary;
    } else if (value === 'theme.palette.text.disabled') {
      cssColor = theme.palette.text.disabled;
    } else if (value === 'theme.palette.text.hint') {
      cssColor = theme.palette.text.hint;
    } else if (value === 'theme.palette.error.main') {
      cssColor = theme.palette.error.main;
    } else if (value === 'theme.palette.error.light') {
      cssColor = theme.palette.error.light;
    } else if (value === 'theme.palette.error.dark') {
      cssColor = theme.palette.error.dark;
    } else {
      const colorName: string = value.substring(0, value.indexOf('['));
      const colorValue: string = value.substring(value.indexOf('[') + 1, value.length - 1);
      if (colorName && colorValue) {
        cssColor = muiColors[colorName][colorValue];
      }
    }
  }
  return cssColor;
};

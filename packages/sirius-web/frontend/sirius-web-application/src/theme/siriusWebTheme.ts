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

import { theme } from '@eclipse-sirius/sirius-components-core';
import { Theme, createTheme } from '@material-ui/core/styles';

export const baseTheme: Theme = createTheme({
  ...theme,
  palette: {
    type: 'light',
    primary: {
      main: '#BE1A78',
      dark: '#851254',
      light: '#CB4793',
    },
    secondary: {
      main: '#261E58',
      dark: '#1A153D',
      light: '#514B79',
    },
    text: {
      primary: '#261E58',
      disabled: '#B3BFC5',
      hint: '#B3BFC5',
    },
    error: {
      main: '#DE1000',
      dark: '#9B0B00',
      light: '#E43F33',
    },
    success: {
      main: '#43A047',
      dark: '#327836',
      light: '#4EBA54',
    },
    warning: {
      main: '#FF9800',
      dark: '#D98200',
      light: '#FFB800',
    },
    info: {
      main: '#2196F3',
      dark: '#1D7DCC',
      light: '#24A7FF',
    },
    divider: '#B3BFC5',
    navigation: {
      leftBackground: '#BE1A7880',
      rightBackground: '#261E5880',
    },
    navigationBar: {
      border: '#BE1A78',
      background: '#261E58',
    },
    selected: '#BE1A78',
    action: {
      hover: '#BE1A7826',
      selected: '#BE1A7842',
    },
  },
});

export const siriusWebTheme = createTheme(
  {
    overrides: {
      MuiAvatar: {
        colorDefault: {
          backgroundColor: baseTheme.palette.primary.main,
        },
      },
      MuiTooltip: {
        tooltip: {
          backgroundColor: baseTheme.palette.common.black,
        },
      },
    },
  },
  baseTheme
);

/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { createTheme, responsiveFontSizes } from '@material-ui/core/styles';

const fallbackTheme = createTheme({
  palette: {
    navigation: {
      leftBackground: 'white',
      rightBackground: 'white',
    },
    navigationBar: {
      border: 'black',
      background: 'black',
    },
    selected: 'gray',
  },
  typography: {
    fontSize: 12,
    h1: {
      fontSize: '3.42rem',
      fontWeight: 400,
      lineHeight: 1.167,
      letterSpacing: '0em',
    },
    h2: {
      fontSize: '2.5rem',
      fontWeight: 400,
      lineHeight: 1.2,
      letterSpacing: '0em',
    },
    h3: {
      fontSize: '2.142rem',
      fontWeight: 400,
      lineHeight: 1.167,
      letterSpacing: '0.3px',
    },
    h4: {
      fontSize: '1.7rem',
      fontWeight: 400,
      lineHeight: 1.235,
      letterSpacing: '0.00735em',
    },
    h5: {
      fontWeight: 700,
      lineHeight: 1.334,
      letterSpacing: '0em',
    },
    h6: {
      fontWeight: 700,
      lineHeight: 1.5,
      letterSpacing: '0.0075em',
    },
    subtitle1: {
      fontWeight: 700,
      lineHeight: 1.7,
      letterSpacing: '0.03em',
    },
    subtitle2: {
      fontWeight: 700,
      lineHeight: 2,
      letterSpacing: '0.06em',
    },
    body1: {
      fontWeight: 400,
      lineHeight: 1.5,
      letterSpacing: '0.00938em',
    },
    body2: {
      fontWeight: 400,
      lineHeight: 1.43,
      letterSpacing: '0.01071em',
    },
  },
});
export const theme = responsiveFontSizes(fallbackTheme);

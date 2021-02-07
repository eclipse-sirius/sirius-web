/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { createMuiTheme, responsiveFontSizes } from '@material-ui/core/styles';

const fallbackTheme = createMuiTheme({
  typography: {
    h1: {
      fontSize: '4rem',
      fontWeight: 400,
      lineHeight: 1.167,
      letterSpacing: '0em',
    },
    h2: {
      fontSize: '3rem',
      fontWeight: 400,
      lineHeight: 1.2,
      letterSpacing: '0em',
    },
    h3: {
      fontSize: '2.5rem',
      fontWeight: 400,
      lineHeight: 1.167,
      letterSpacing: '0.3px',
    },
    h4: {
      fontSize: '2rem',
      fontWeight: 400,
      lineHeight: 1.235,
      letterSpacing: '0.00735em',
    },
    h5: {
      fontSize: '1.5rem',
      fontWeight: 500,
      lineHeight: 1.334,
      letterSpacing: '0em',
    },
    h6: {
      fontSize: '1.25rem',
      fontWeight: 500,
      lineHeight: 1.5,
      letterSpacing: '0.0075em',
    },
    subtitle1: {
      fontSize: '1rem',
      fontWeight: 500,
      lineHeight: 1.7,
      letterSpacing: '0.03em',
    },
    subtitle2: {
      fontSize: '0.875rem',
      fontWeight: 500,
      lineHeight: 2,
      letterSpacing: '0.06em',
    },
    body1: {
      fontSize: '1rem',
      fontWeight: 400,
      lineHeight: 1.5,
      letterSpacing: '0.00938em',
    },
    body2: {
      fontSize: '0.875rem',
      fontWeight: 400,
      lineHeight: 1.43,
      letterSpacing: '0.01071em',
    },
  },
});
export const theme = responsiveFontSizes(fallbackTheme);

/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import React from 'react';

declare module '@mui/material/styles' {
  export interface Palette {
    formdescriptioneditor: {
      widget: {
        defaultBorderColor: React.CSSProperties['color'];
      };
    };
  }
}

// TODO: there is somthing that can produce an error.
// The "fallback" theme does not have default border color for widget in the form description editor.
// Think about a solution to contribute to the theme package by package with something in the "fallback" theme

// NOTE: The fallback theme is used as a reference by other theme that depends on it.

declare module '@mui/material/styles/createPalette' {
  export interface Palette {
    formdescriptioneditor: {
      widget: {
        defaultBorderColor: React.CSSProperties['color'];
      };
    };
  }
  export interface PaletteOptions {
    formdescriptioneditor: {
      widget: {
        defaultBorderColor: React.CSSProperties['color'];
      };
    };
  }
}

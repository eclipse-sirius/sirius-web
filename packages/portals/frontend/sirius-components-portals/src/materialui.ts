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
    portal: {
      representationFrame: {
        borderColor: React.CSSProperties['color'];
      };
      frameHeader: {
        backgroundColor: React.CSSProperties['color'];
      };
    };
  }
}

declare module '@mui/material/styles/createPalette' {
  export interface Palette {
    portal: {
      representationFrame: {
        borderColor: React.CSSProperties['color'];
      };
      frameHeader: {
        backgroundColor: React.CSSProperties['color'];
      };
    };
  }
  export interface PaletteOptions {
    portal: {
      representationFrame: {
        borderColor: React.CSSProperties['color'];
      };
      frameHeader: {
        backgroundColor: React.CSSProperties['color'];
      };
    };
  }
}

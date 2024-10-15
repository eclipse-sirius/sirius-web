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
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
    navigationBar: {
      border: React.CSSProperties['color'];
      background: React.CSSProperties['color'];
    };
    selected: React.CSSProperties['color'];
    dropArea: {
      backgroundColor: React.CSSProperties['color'];
      color: React.CSSProperties['color'];
      borderColor: React.CSSProperties['color'];
    };
  }
}

declare module '@mui/material/styles/createPalette' {
  export interface Palette {
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
    navigationBar: {
      border: React.CSSProperties['color'];
      background: React.CSSProperties['color'];
    };
    selected: React.CSSProperties['color'];
    dropArea: {
      backgroundColor: React.CSSProperties['color'];
      color: React.CSSProperties['color'];
      borderColor: React.CSSProperties['color'];
    };
  }
  export interface PaletteOptions {
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
    navigationBar: {
      border: React.CSSProperties['color'];
      background: React.CSSProperties['color'];
    };
    selected: React.CSSProperties['color'];
    dropArea: {
      backgroundColor: React.CSSProperties['color'];
      color: React.CSSProperties['color'];
      borderColor: React.CSSProperties['color'];
    };
  }
}

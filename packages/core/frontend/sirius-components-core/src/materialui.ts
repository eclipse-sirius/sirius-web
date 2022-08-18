/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

declare module '@material-ui/core/styles' {
  export interface Palette {
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
  }
}

declare module '@material-ui/core/styles/createPalette' {
  export interface Palette {
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
  }
  export interface PaletteOptions {
    navigation: {
      leftBackground: React.CSSProperties['color'];
      rightBackground: React.CSSProperties['color'];
    };
  }
}

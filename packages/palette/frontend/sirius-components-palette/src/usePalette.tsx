/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { useContext } from 'react';
import { PaletteContext } from './contexts/PaletteContext';
import { PaletteContextValue } from './contexts/PaletteContext.types';
import { usePaletteValue } from './usePalette.types';

export const usePalette = (): usePaletteValue => {
  const {
    x,
    y,
    isOpened,
    representationElementIds,
    hidePalette,
    showPalette,
    getLastToolInvokedId,
    setLastToolInvokedId,
  } = useContext<PaletteContextValue>(PaletteContext);

  return {
    x,
    y,
    isOpened,
    representationElementIds,
    hidePalette,
    showPalette,
    getLastToolInvokedId,
    setLastToolInvokedId,
  };
};

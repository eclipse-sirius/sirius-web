/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { useEffect, useState } from 'react';
import { UseSnapToGridValue } from './useSnapToGrid.types';

export const useSnapToGrid = (defaultState: boolean, canSnapToGrid: boolean): UseSnapToGridValue => {
  const [state, setState] = useState<boolean>(defaultState);

  useEffect(() => {
    if (!canSnapToGrid) {
      setState(false);
    }
  }, [state, canSnapToGrid]);

  return {
    snapToGrid: state,
    onSnapToGrid: setState,
  };
};

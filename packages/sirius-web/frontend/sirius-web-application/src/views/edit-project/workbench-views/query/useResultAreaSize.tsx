/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { useEffect, useRef, useState } from 'react';
import { UseResultAreaSizeState, UseResultAreaSizeValue } from './useResultAreaSize.types';

export const useResultAreaSize = (): UseResultAreaSizeValue => {
  const [state, setState] = useState<UseResultAreaSizeState>({
    height: null,
    width: null,
  });

  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (!ref.current) {
      return () => {};
    }
    const parentDiv = ref.current;
    const expressionDiv: HTMLDivElement | null = parentDiv.querySelector('[data-role="expression"]');
    const resultDiv: HTMLDivElement | null = parentDiv.querySelector('[data-role="result"]');

    const resizeObserver = new ResizeObserver(() => {
      if (expressionDiv && resultDiv) {
        const height = (parentDiv.offsetHeight - expressionDiv.offsetHeight) * 0.85;
        const width = expressionDiv.offsetWidth;

        setState((prevState) => ({
          ...prevState,
          height,
          width,
        }));
      }
    });

    resizeObserver.observe(ref.current);

    return () => resizeObserver.disconnect();
  }, [ref.current]);

  return {
    ref,
    height: state.height,
    width: state.width,
  };
};

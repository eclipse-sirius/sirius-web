/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { useEffect, useRef } from 'react';
import { MousePosition, UseMousePositionValue } from './useMousePosition.types';

export const useMousePosition = (): UseMousePositionValue => {
  const mousePositionRef = useRef<MousePosition>({ x: 0, y: 0 });

  useEffect(() => {
    const updateMousePosition = (event: MouseEvent) => {
      mousePositionRef.current.x = event.clientX;
      mousePositionRef.current.y = event.clientY;
    };
    window.addEventListener('mousemove', updateMousePosition);

    return () => {
      window.removeEventListener('mousemove', updateMousePosition);
    };
  }, []);

  const getMousePosition = () => ({ x: mousePositionRef.current.x, y: mousePositionRef.current.y });

  return {
    getMousePosition,
  };
};

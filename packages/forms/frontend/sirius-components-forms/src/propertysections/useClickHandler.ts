/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export function useClickHandler<T>(
  onSimpleClick: (element: T) => void,
  onDoubleClick: (element: T) => void,
  delay: number = 250
): (element: T) => void {
  const eventRef = useRef<T | null>(null);
  const [clicks, setClicks] = useState(0);
  useEffect(() => {
    let singleClickTimer;
    if (clicks === 1) {
      singleClickTimer = setTimeout(function () {
        onSimpleClick?.(eventRef.current);
        setClicks(0);
        eventRef.current = null;
      }, delay);
    } else if (clicks === 2) {
      onDoubleClick?.(eventRef.current);
      eventRef.current = null;
      setClicks(0);
    }
    return () => clearTimeout(singleClickTimer);
  }, [clicks, onSimpleClick, onDoubleClick, delay]);
  return (element) => {
    eventRef.current = element;
    setClicks(clicks + 1);
  };
}

/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { useCallback, useEffect, useState } from 'react';
import { UseFullscreenValue } from './useFullscreen.types';

export const useFullscreen = (domNode: React.RefObject<HTMLDivElement>): UseFullscreenValue => {
  const [fullscreen, setFullscreenState] = useState<boolean>(false);

  useEffect(() => {
    const onFullscreenChange = () => setFullscreenState(Boolean(document.fullscreenElement));
    document.addEventListener('fullscreenchange', onFullscreenChange);
    return () => document.removeEventListener('fullscreenchange', onFullscreenChange);
  }, []);

  const setFullscreen = useCallback(
    (fullscreen: boolean) => {
      if (domNode.current) {
        if (fullscreen) {
          domNode.current.requestFullscreen();
        } else {
          document.exitFullscreen();
        }
      }
    },
    [domNode.current]
  );

  return {
    fullscreen,
    setFullscreen,
  };
};

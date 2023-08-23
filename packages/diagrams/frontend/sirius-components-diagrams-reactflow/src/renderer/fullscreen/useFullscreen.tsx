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
import { useCallback, useContext, useEffect } from 'react';
import { useStoreApi } from 'reactflow';
import { FullscreenContextValue } from './Fullscreen.context.types';
import { FullscreenContext } from './FullscreenContext';
import { UseFullscreenValue } from './useFullscreen.types';

export const useFullscreen = (): UseFullscreenValue => {
  const { domNode } = useStoreApi().getState();
  const { fullscreen, setFullscreen } = useContext<FullscreenContextValue>(FullscreenContext);

  useEffect(() => {
    const onFullscreenChange = () => setFullscreen(Boolean(document.fullscreenElement));

    document.addEventListener('fullscreenchange', onFullscreenChange);

    return () => document.removeEventListener('fullscreenchange', onFullscreenChange);
  }, []);

  const onFullscreen = useCallback(
    (fullscreen: boolean) => {
      if (domNode) {
        if (fullscreen) {
          domNode.requestFullscreen();
        } else {
          document.exitFullscreen();
        }
      }
    },
    [domNode]
  );

  return {
    fullscreen,
    onFullscreen,
  };
};

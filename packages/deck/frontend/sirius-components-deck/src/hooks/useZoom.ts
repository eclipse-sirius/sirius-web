/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useCallback, useState } from 'react';
import { UseZoomValue } from './useZoom.types';

export const useZoom = (
  contentNode: React.RefObject<HTMLDivElement | null>,
  containerNode: React.RefObject<HTMLDivElement | null>
): UseZoomValue => {
  const [zoom, setZoom] = useState<number>(1);

  const zoomIn = useCallback(() => {
    setZoom((prevZoom) => prevZoom + 0.1);
  }, []);

  const zoomOut = useCallback(() => {
    setZoom((prevZoom) => prevZoom - 0.1);
  }, []);

  const fitToScreen = useCallback(() => {
    if (containerNode.current && contentNode.current) {
      //We use the ratio between the representation container size and the board size to compute the zoom factor.
      const containerWidth = containerNode.current.clientWidth;
      const containerHeight = containerNode.current.clientHeight;
      const contentWidth = contentNode.current.clientWidth;
      const contentHeight = contentNode.current.clientHeight;
      const scaleX = containerWidth / contentWidth;
      const scaleY = containerHeight / contentHeight;
      const newScale = Math.min(scaleX, scaleY);
      setZoom(() => newScale);
    }
  }, []);

  return {
    zoom,
    zoomIn,
    zoomOut,
    fitToScreen,
  };
};

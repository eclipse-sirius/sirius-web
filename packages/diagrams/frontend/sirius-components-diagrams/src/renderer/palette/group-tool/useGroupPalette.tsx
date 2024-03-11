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
import { useCallback, useEffect, useState } from 'react';
import { XYPosition, useKeyPress, useStoreApi } from 'reactflow';
import { UseGroupPaletteValue, UseGroupPaletteState } from './useGroupPalette.types';

const computePalettePosition = (event: MouseEvent | React.MouseEvent, bounds?: DOMRect): XYPosition => {
  return {
    x: event.clientX - (bounds?.left ?? 0),
    y: event.clientY - (bounds?.top ?? 0),
  };
};

export const useGroupPalette = (): UseGroupPaletteValue => {
  const [state, setState] = useState<UseGroupPaletteState>({ position: null, isOpened: false });

  const store = useStoreApi();

  const escapePressed = useKeyPress('Escape');
  useEffect(() => {
    if (escapePressed) {
      hideGroupPalette();
    }
  }, [escapePressed]);

  const onDiagramGroupElementClick = useCallback((event: React.MouseEvent<Element, MouseEvent>) => {
    const { domNode } = store.getState();
    const element = domNode?.getBoundingClientRect();
    const palettePosition = computePalettePosition(event, element);
    setState((prevState) => ({ ...prevState, position: palettePosition, isOpened: true }));
  }, []);

  const hideGroupPalette = () => {
    setState((prevState) => ({ ...prevState, position: null, isOpened: false }));
  };
  return {
    position: state.position,
    isOpened: state.isOpened,
    hideGroupPalette,
    onDiagramGroupElementClick,
  };
};

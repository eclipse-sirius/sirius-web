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

import { useEffect, useState } from 'react';
import { UseNodesDraggableState, UseNodesDraggableValue } from './useNodesDraggable.types';

export const useNodesDraggable = (): UseNodesDraggableValue => {
  const [state, setState] = useState<UseNodesDraggableState>({
    nodesDraggable: true,
  });

  useEffect(() => {
    const keydownListener = (event: KeyboardEvent) => {
      if (event.key === 'Alt') {
        setState((prevState) => ({ ...prevState, nodesDraggable: false }));
      }
    };
    document.addEventListener('keydown', keydownListener);

    const keyupListener = (event: KeyboardEvent) => {
      if (event.key === 'Alt') {
        setState((prevState) => ({ ...prevState, nodesDraggable: true }));
      }
    };
    document.addEventListener('keyup', keyupListener);

    return () => {
      document.removeEventListener('keydown', keydownListener);
      document.removeEventListener('keyup', keyupListener);
    };
  }, []);

  return { nodesDraggable: state.nodesDraggable };
};

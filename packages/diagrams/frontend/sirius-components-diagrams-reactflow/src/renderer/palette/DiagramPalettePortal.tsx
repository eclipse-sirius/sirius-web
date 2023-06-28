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

import { createPortal } from 'react-dom';
import { ReactFlowState, useStore } from 'reactflow';
import { DiagramPalettePortalProps } from './DiagramPalette.types';

const selector = (state: ReactFlowState) => state.domNode?.querySelector('.react-flow__renderer');

export const DiagramPalettePortal = ({ children }: DiagramPalettePortalProps) => {
  const wrapperRef = useStore(selector);

  if (!wrapperRef) {
    return null;
  }

  return createPortal(children, wrapperRef);
};

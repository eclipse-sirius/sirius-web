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

import { useStoreApi } from '@xyflow/react';
import { createPortal } from 'react-dom';
import { PalettePortalProps } from './PalettePortal.types';

//The sibling dom element .react-flow__renderer have a zIndex of 4, so we set it here to 5 to have the palette in front of the diagram.
const palettePortalStyle: React.CSSProperties = {
  zIndex: 5,
  position: 'absolute',
};

export const PalettePortal = ({ children }: PalettePortalProps) => {
  const wrapperRef = useStoreApi().getState().domNode;

  if (!wrapperRef) {
    return null;
  }

  return createPortal(<div style={palettePortalStyle}>{children}</div>, wrapperRef);
};

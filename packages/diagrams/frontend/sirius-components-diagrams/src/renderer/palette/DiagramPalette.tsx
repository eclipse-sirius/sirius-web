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

import { memo, useContext, useEffect } from 'react';
import { useKeyPress } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { Palette } from './Palette';
import { PalettePortal } from './PalettePortal';
import { useDiagramPalette } from './useDiagramPalette';

export const DiagramPalette = memo(({ diagramElementId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x, y, hideDiagramPalette } = useDiagramPalette();
  if (readOnly) {
    return null;
  }

  const escapePressed = useKeyPress('Escape');
  useEffect(() => {
    if (escapePressed) {
      hideDiagramPalette();
    }
  }, [escapePressed, hideDiagramPalette]);

  return isOpened && x && y ? (
    <PalettePortal>
      <Palette x={x} y={y} diagramElementId={diagramElementId} onDirectEditClick={() => {}} />
    </PalettePortal>
  ) : null;
});

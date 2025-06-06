/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { memo, useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { Palette } from './Palette';
import { PalettePortal } from './PalettePortal';
import { useDiagramPalette } from './useDiagramPalette';

export const DiagramPalette = memo(({ diagramElementId, targetObjectId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x, y, hideDiagramPalette } = useDiagramPalette();

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        hideDiagramPalette();
      }
    },
    [hideDiagramPalette, isOpened]
  );

  if (readOnly) {
    return null;
  }

  return isOpened && x && y ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={x}
          y={y}
          diagramElementId={diagramElementId}
          targetObjectId={targetObjectId}
          onDirectEditClick={() => {}}
          onClose={hideDiagramPalette}
          children={[]}
        />
      </div>
    </PalettePortal>
  ) : null;
});

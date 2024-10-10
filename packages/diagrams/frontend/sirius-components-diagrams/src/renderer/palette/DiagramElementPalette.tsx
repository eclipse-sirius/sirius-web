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

import { useKeyPress } from '@xyflow/react';
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { DiagramElementPaletteProps } from './DiagramElementPalette.types';
import { Palette } from './Palette';
import { PalettePortal } from './PalettePortal';
import { useDiagramElementPalette } from './useDiagramElementPalette';

export const DiagramElementPalette = memo(
  ({ diagramElementId, targetObjectId, labelId }: DiagramElementPaletteProps) => {
    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const { isOpened, x, y, hideDiagramElementPalette } = useDiagramElementPalette();
    const { setCurrentlyEditedLabelId, currentlyEditedLabelId } = useDiagramDirectEdit();

    const escapePressed = useKeyPress('Escape');
    useEffect(() => {
      if (escapePressed) {
        hideDiagramElementPalette();
      }
    }, [escapePressed, hideDiagramElementPalette]);

    if (readOnly) {
      return null;
    }

    const handleDirectEditClick = () => {
      if (labelId) {
        setCurrentlyEditedLabelId('palette', labelId, null);
      }
    };

    return isOpened && x && y && !currentlyEditedLabelId ? (
      <PalettePortal>
        <Palette
          x={x}
          y={y}
          diagramElementId={diagramElementId}
          targetObjectId={targetObjectId}
          onDirectEditClick={handleDirectEditClick}
          hideableDiagramElement
        />
      </PalettePortal>
    ) : null;
  }
);

/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { memo } from 'react';
import { Label } from '../Label';
import { NodeInsideLabelProps } from './NodeInsideLabel.types';

export const NodeInsideLabel = memo(({ diagramElementId, insideLabel, faded, innerBorderRadius }: NodeInsideLabelProps) => {
  const { headerPosition, height } = insideLabel;
  const { background } = insideLabel.contentStyle;
  const hasHeaderBackground = insideLabel.isHeader && headerPosition !== undefined && height > 0 && !!background;
  const label = hasHeaderBackground
    ? { ...insideLabel, contentStyle: { ...insideLabel.contentStyle, background: undefined } }
    : insideLabel;
  return (
    <Box
      sx={{
        display: 'grid',
        gridTemplateRows: '1fr',
        gridTemplateColumns: 'minmax(0, 1fr)',
      }}>
      {hasHeaderBackground ? (
        <div
          aria-hidden="true"
          style={{
            // Clip to the full node so a short header does not shrink the corner radii.
            position: 'absolute',
            inset: 0,
            overflow: 'hidden',
            ...innerBorderRadius,
            pointerEvents: 'none',
            visibility: insideLabel.style.visibility,
            opacity: faded ? 0.4 : undefined,
            zIndex: -1,
          }}>
          <Box
            sx={(theme) => ({
              position: 'absolute',
              left: 0,
              right: 0,
              top: headerPosition === 'TOP' ? 0 : undefined,
              bottom: headerPosition === 'BOTTOM' ? 0 : undefined,
              height: `${height}px`,
              background: getCSSColor(String(background), theme),
            })}
          />
        </div>
      ) : null}
      <Box
        sx={{
          gridRow: 1,
          gridColumn: 1,
        }}>
        <Label diagramElementId={diagramElementId} label={label} faded={faded} />
      </Box>
    </Box>
  );
});

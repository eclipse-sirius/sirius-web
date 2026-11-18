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
import Box from '@mui/material/Box';
import { memo } from 'react';
import { Label } from '../Label';
import { NodeInsideLabelProps } from './NodeInsideLabel.types';

export const NodeInsideLabel = memo(({ diagramElementId, insideLabel, faded }: NodeInsideLabelProps) => {
  return (
    <Box
      sx={{
        display: 'grid',
        gridTemplateRows: '1fr',
        gridTemplateColumns: 'minmax(0, 1fr)',
      }}>
      <Box
        sx={{
          gridRow: 1,
          gridColumn: 1,
        }}>
        <Label diagramElementId={diagramElementId} label={insideLabel} faded={faded} />
      </Box>
    </Box>
  );
});

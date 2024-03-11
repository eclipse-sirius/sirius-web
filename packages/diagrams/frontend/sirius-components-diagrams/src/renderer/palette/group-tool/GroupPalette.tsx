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

import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import Tooltip from '@material-ui/core/Tooltip';
import { makeStyles } from '@material-ui/core/styles';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import { memo, useCallback, useState } from 'react';
import { useOnSelectionChange } from 'reactflow';
import { PinIcon } from '../../../icons/PinIcon';
import { useFadeDiagramElements } from '../../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { usePinDiagramElements } from '../../pin/usePinDiagramElements';
import { ContextualPaletteStyleProps } from '../Palette.types';
import { PalettePortal } from '../PalettePortal';
import { useDiagramElementPalette } from '../useDiagramElementPalette';
import { GroupPaletteProps } from './GroupPalette.types';

const usePaletteStyle = makeStyles((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
  paletteContent: {
    display: 'grid',
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  toolIcon: {
    color: theme.palette.text.primary,
  },
}));

export const GroupPalette = memo(({ x, y, isOpened }: GroupPaletteProps) => {
  const { hideDiagramElements } = useHideDiagramElements();
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const [selectedElementIds, setSelectedElementIds] = useState<string[]>([]);

  const onChange = useCallback(({ nodes }) => {
    if (nodes.filter((node) => node.selected).length > 1) {
      setSelectedElementIds(nodes.filter((node) => node.selected).map((node) => node.id));
    } else {
      setSelectedElementIds([]);
    }
  }, []);
  useOnSelectionChange({
    onChange,
  });

  const toolCount = 3;
  const classes = usePaletteStyle({ toolCount });

  const shouldRender = selectedElementIds.length > 1 && isOpened && x && y;
  if (!shouldRender) {
    return null;
  }
  hideDiagramElementPalette();

  return (
    <PalettePortal>
      <Paper className={classes.palette} style={{ position: 'absolute', left: x, top: y }} data-testid="GroupPalette">
        <div className={classes.paletteContent}>
          <Tooltip title="Hide elements">
            <IconButton
              className={classes.toolIcon}
              size="small"
              aria-label="Hide elements"
              onClick={() => hideDiagramElements(selectedElementIds, true)}
              data-testid="Hide-elements">
              <VisibilityOffIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Fade elements">
            <IconButton
              className={classes.toolIcon}
              size="small"
              aria-label="Fade elements"
              onClick={() => fadeDiagramElements(selectedElementIds, true)}
              data-testid="Fade-elements">
              <TonalityIcon fontSize="small" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Pin elements">
            <IconButton
              className={classes.toolIcon}
              size="small"
              aria-label="Pin elements"
              onClick={() => pinDiagramElements(selectedElementIds, true)}
              data-testid="Pin-elements">
              <PinIcon fontSize="small" />
            </IconButton>
          </Tooltip>
        </div>
      </Paper>
    </PalettePortal>
  );
});

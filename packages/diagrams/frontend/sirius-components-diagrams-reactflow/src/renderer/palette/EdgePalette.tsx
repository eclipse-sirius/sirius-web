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

import { makeStyles } from '@material-ui/core/styles';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { EdgePaletteProps } from './EdgePalette.types';
import { EdgePalettePortal } from './EdgePalettePortal';
import { Palette } from './Palette';
import { useEdgePalette } from './useEdgePalette';

const useEdgePaletteStyle = makeStyles((theme) => ({
  toolbar: {
    background: theme.palette.background.paper,
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
}));

export const EdgePalette = ({ edgeId, labelId }: EdgePaletteProps) => {
  const { setCurrentlyEditedLabelId } = useDiagramDirectEdit();
  const { x, y, isOpened, hideEdgePalette } = useEdgePalette();
  const classes = useEdgePaletteStyle();

  const handleDirectEditClick = () => {
    if (labelId) {
      setCurrentlyEditedLabelId('palette', labelId, null);
      hideEdgePalette();
    }
  };

  return isOpened && x && y ? (
    <EdgePalettePortal>
      <div className={classes.toolbar} style={{ position: 'absolute', left: x, top: y }}>
        <Palette diagramElementId={edgeId} onDirectEditClick={handleDirectEditClick} isDiagramElementPalette={true} />
      </div>
    </EdgePalettePortal>
  ) : null;
};

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
import { DiagramPaletteProps } from './DiagramPalette.types';
import { DiagramPalettePortal } from './DiagramPalettePortal';
import { Palette } from './Palette';
import { useDiagramPalette } from './useDiagramPalette';

const useDiagramPaletteStyle = makeStyles((theme) => ({
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

export const DiagramPalette = ({ targetObjectId }: DiagramPaletteProps) => {
  const { x, y, isOpened } = useDiagramPalette();
  const classes = useDiagramPaletteStyle();

  return isOpened ? (
    <DiagramPalettePortal>
      <div className={classes.toolbar} style={{ position: 'absolute', left: x, top: y }}>
        <Palette diagramElementId={targetObjectId} onDirectEditClick={() => {}} isNodePalette={false} />
      </div>
    </DiagramPalettePortal>
  ) : null;
};

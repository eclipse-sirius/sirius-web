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
import { NodeToolbar, Position } from 'reactflow';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { NodePaletteProps } from './NodePalette.types';
import { Palette } from './Palette';

const useNodePaletteStyle = makeStyles((theme) => ({
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

export const NodePalette = ({ diagramElementId, labelId }: NodePaletteProps) => {
  const { setCurrentlyEditedLabelId } = useDiagramDirectEdit();
  const classes = useNodePaletteStyle();

  const handleDirectEditClick = () => {
    if (labelId) {
      setCurrentlyEditedLabelId('palette', labelId, null);
    }
  };

  return (
    <NodeToolbar className={classes.toolbar} position={Position.Top}>
      <Palette diagramElementId={diagramElementId} onDirectEditClick={handleDirectEditClick} isNodePalette={true} />
    </NodeToolbar>
  );
};

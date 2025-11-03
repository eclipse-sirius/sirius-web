/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { HideElementToolProps } from './HideElementTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const HideElementTool = ({ diagramElementIds }: HideElementToolProps) => {
  const { classes } = useStyle();
  const { hideDiagramElements } = useHideDiagramElements();

  return (
    <Tooltip title="Hide element" key={'tooltip_hide_element_tool'}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label="Hide element"
        onClick={() => hideDiagramElements(diagramElementIds, true)}
        data-testid="Hide-element">
        <VisibilityOffIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};

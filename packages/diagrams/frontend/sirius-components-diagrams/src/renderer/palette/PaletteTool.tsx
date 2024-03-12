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
import Tooltip from '@material-ui/core/Tooltip';
import { makeStyles } from '@material-ui/core/styles';
import { PaletteToolProps } from './PaletteTool.types';

const usePaletteToolStyle = makeStyles((theme) => ({
  toolIcon: {
    color: theme.palette.text.primary,
  },
}));

export const PaletteTool = ({ toolName, onClick, children }: PaletteToolProps) => {
  const classes = usePaletteToolStyle();

  return (
    <Tooltip title={toolName}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={toolName}
        onClick={onClick}
        data-testid={toolName}>
        {children}
      </IconButton>
    </Tooltip>
  );
};

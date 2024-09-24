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

import AdjustIcon from '@mui/icons-material/Adjust';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { useAdjustSize } from '../../adjust-size/useAdjustSize';
import { AdjustSizeToolProps } from './AdjustSizeTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    width: theme.spacing(4.5),
    color: theme.palette.text.primary,
  },
}));

export const AdjustSizeTool = ({ diagramElementId }: AdjustSizeToolProps) => {
  const { classes } = useStyle();
  const { adjustSize } = useAdjustSize();
  return (
    <Tooltip title="Adjust size" key={'tooltip_adjust_element_tool'}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label="Adjust element"
        onClick={() => adjustSize(diagramElementId)}
        data-testid="adjust-element">
        <AdjustIcon fontSize="small" />
      </IconButton>
    </Tooltip>
  );
};

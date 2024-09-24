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

import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { PinIcon } from '../../../icons/PinIcon';
import { UnpinIcon } from '../../../icons/UnpinIcon';
import { usePinDiagramElements } from '../../pin/usePinDiagramElements';
import { PinUnPinToolProps } from './PinUnPinTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    width: theme.spacing(4.5),
    color: theme.palette.text.primary,
  },
}));

export const PinUnPinTool = ({ diagramElementId, isPined }: PinUnPinToolProps) => {
  const { classes } = useStyle();
  const { pinDiagramElements } = usePinDiagramElements();
  if (isPined) {
    return (
      <Tooltip title="Unpin element" key={'tooltip_unpin_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Unpin element"
          onClick={() => pinDiagramElements([diagramElementId], false)}
          data-testid="Unpin-element">
          <UnpinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
  } else {
    return (
      <Tooltip title="Pin element" key={'tooltip_pin_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Pin element"
          onClick={() => pinDiagramElements([diagramElementId], true)}
          data-testid="Pin-element">
          <PinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
  }
};

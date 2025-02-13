/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import DirectionsOffIcon from '@mui/icons-material/DirectionsOff';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { useEditableEdgePath } from '../../edge/useEditableEdgePath';
import { ResetEditedEdgePathToolProps } from './ResetEditedEdgePathTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const ResetEditedEdgePathTool = ({ diagramElementId }: ResetEditedEdgePathToolProps) => {
  const { classes } = useStyle();
  const { removeEdgeLayoutData } = useEditableEdgePath();
  return (
    <Tooltip title="Reset path">
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label="Reset path"
        onClick={() => removeEdgeLayoutData(diagramElementId)}
        data-testid="Reset-path">
        <DirectionsOffIcon fontSize="small" />
      </IconButton>
    </Tooltip>
  );
};

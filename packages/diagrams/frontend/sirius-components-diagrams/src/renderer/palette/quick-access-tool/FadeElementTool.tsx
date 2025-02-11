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

import TonalityIcon from '@mui/icons-material/Tonality';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { useFadeDiagramElements } from '../../fade/useFadeDiagramElements';
import { FadeElementToolProps } from './FadeElementTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const FadeElementTool = ({ diagramElementId, isFaded }: FadeElementToolProps) => {
  const { classes } = useStyle();
  const { fadeDiagramElements } = useFadeDiagramElements();
  if (isFaded) {
    return (
      <Tooltip title="Unfade element" key={'tooltip_unfade_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Unfade element"
          onClick={() => fadeDiagramElements([diagramElementId], false)}
          data-testid="Fade-element">
          <TonalityIcon sx={{ fontSize: 16 }} />
        </IconButton>
      </Tooltip>
    );
  } else {
    return (
      <Tooltip title="Fade element" key={'tooltip_fade_element_tool'}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Fade element"
          onClick={() => fadeDiagramElements([diagramElementId], true)}
          data-testid="Fade-element">
          <TonalityIcon sx={{ fontSize: 16 }} />
        </IconButton>
      </Tooltip>
    );
  }
};

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

import AdjustIcon from '@mui/icons-material/Adjust';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { useAdjustSize } from '../../adjust-size/useAdjustSize';
import { AdjustSizeToolProps } from './AdjustSizeTool.types';
import { useTranslation } from 'react-i18next';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const AdjustSizeTool = ({ diagramElementId }: AdjustSizeToolProps) => {
  const { classes } = useStyle();
  const { adjustSize } = useAdjustSize();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'adjustSizeTool' });
  return (
    <Tooltip title={t('adjustSize')} key={'tooltip_adjust_element_tool'}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={t('adjustElement')}
        onClick={() => adjustSize(diagramElementId)}
        data-testid="adjust-element">
        <AdjustIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};

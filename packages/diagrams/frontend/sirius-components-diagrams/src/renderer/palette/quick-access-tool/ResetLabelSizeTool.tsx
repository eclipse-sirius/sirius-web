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

import AspectRatioIcon from '@mui/icons-material/AspectRatio';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useLabelResetSize } from '../../resize/useLabelResetSize';
import { ResetLabelSizeToolProps } from './ResetLabelSizeTool.types';
import { useTranslation } from 'react-i18next';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const ResetLabelSizeTool = ({ diagramElementId }: ResetLabelSizeToolProps) => {
  const { classes } = useStyle();
  const { removeEdgeLabelLayoutData, removeNodeLabelLayoutData } = useLabelResetSize();
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'resetLabelSizeTool' });

  const handleRemoveLayoutData = (diagramElementId: string) => {
    const node = nodeLookup.get(diagramElementId);
    if (node) {
      removeNodeLabelLayoutData(diagramElementId);
    } else {
      const edge = edgeLookup.get(diagramElementId);
      if (edge) {
        removeEdgeLabelLayoutData(diagramElementId);
      }
    }
  };

  return (
    <Tooltip title={t('resetLabelSize')}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={t('resetLabelSize')}
        onClick={() => handleRemoveLayoutData(diagramElementId)}
        data-testid="reset-label-size">
        <AspectRatioIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};

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

import LabelOffIcon from '@mui/icons-material/LabelOff';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useLabelResetPosition } from '../../move/useLabelResetPosition';
import { ResetLabelPositionToolProps } from './ResetLabelPositionTool.types';
import { useTranslation } from 'react-i18next';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const ResetLabelPositionTool = ({ diagramElementId }: ResetLabelPositionToolProps) => {
  const { classes } = useStyle();
  const { removeEdgeLabelLayoutData, removeNodeLabelLayoutData } = useLabelResetPosition();
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'resetLabelPositionTool' });

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
    <Tooltip title={t('resetLabelPosition')}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={t('resetLabelPosition')}
        onClick={() => handleRemoveLayoutData(diagramElementId)}
        data-testid="reset-label-position">
        <LabelOffIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};

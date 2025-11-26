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

import SwipeRightAltIcon from '@mui/icons-material/SwipeRightAlt';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { Edge, InternalNode, Node, useStoreApi } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useHandlesLayout } from '../../handles/useHandlesLayout';
import { ResetManuallyLaidOutHandlesToolProps } from './ResetManuallyLaidOutHandlesTool.types';
import { useTranslation } from 'react-i18next';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const ResetManuallyLaidOutHandlesTool = ({ diagramElementId }: ResetManuallyLaidOutHandlesToolProps) => {
  const { classes } = useStyle();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { removeNodeHandleLayoutData } = useHandlesLayout();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'resetManuallyLaidOutHandlesTool' });
  const edge = store.getState().edgeLookup.get(diagramElementId);
  const sourceNodeId = edge?.source;
  const tagetNodeId = edge?.target;

  const nodes = [
    store.getState().nodeLookup.get(sourceNodeId ?? ''),
    store.getState().nodeLookup.get(tagetNodeId ?? ''),
  ].filter((node): node is InternalNode<Node<NodeData>> => !!node);

  const areHandlesLaidOut = nodes.flatMap((node) => node.data.connectionHandles).find((handle) => handle?.XYPosition);

  const handleToolClick = () => {
    if (edge?.id) {
      removeNodeHandleLayoutData(
        nodes.map((node) => node.id),
        edge.id
      );
    }
  };

  if (!areHandlesLaidOut) {
    return null;
  }

  return (
    <Tooltip title={t('resetHandles')}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={t('resetHandles')}
        onClick={handleToolClick}
        data-testid="Reset-handles">
        <SwipeRightAltIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};

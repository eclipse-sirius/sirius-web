/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { getCSSColor, IconOverlay } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@mui/material/IconButton';
import { Theme, useTheme } from '@mui/material/styles';
import { Node, NodeProps } from '@xyflow/react';
import { memo, useMemo } from 'react';
import { makeStyles } from 'tss-react/mui';

import { Label } from '../Label';
import { useActions } from '../actions/useActions';
import { GQLAction } from '../actions/useActions.types';
import { useConnectorNodeStyle } from '../connector/useConnectorNodeStyle';
import { useDrop } from '../drop/useDrop';
import { useDropNodeStyle } from '../dropNode/useDropNodeStyle';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import { ConnectionHandles } from '../handles/ConnectionHandles';
import { ConnectionTargetHandle } from '../handles/ConnectionTargetHandle';
import { useRefreshConnectionHandles } from '../handles/useRefreshConnectionHandles';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { ListNodeData } from './ListNode.types';
import { NodeComponentsMap } from './NodeTypes';
import { Resizer } from './Resizer';

const listNodeStyle = (
  theme: Theme,
  style: React.CSSProperties,
  selected: boolean,
  hovered: boolean,
  faded: boolean
): React.CSSProperties => {
  const listNodeStyle: React.CSSProperties = {
    width: '100%',
    height: '100%',
    opacity: faded ? '0.4' : '',
    ...style,
    background: getCSSColor(String(style.background), theme),
    borderTopColor: getCSSColor(String(style.borderTopColor), theme),
    borderBottomColor: getCSSColor(String(style.borderBottomColor), theme),
    borderLeftColor: getCSSColor(String(style.borderLeftColor), theme),
    borderRightColor: getCSSColor(String(style.borderRightColor), theme),
  };
  if (selected || hovered) {
    listNodeStyle.outline = `${theme.palette.selected} solid 1px`;
  }

  return listNodeStyle;
};

const useStyles = makeStyles()((theme) => ({
  labelAndAction: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '1fr',
  },
  label: {
    gridRow: 1,
    gridColumn: 1,
  },
  actionsContainer: {
    gridRow: 1,
    gridColumn: 1,
    zIndex: 10,
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
    gap: theme.spacing(1),
  },
  actionIcon: {
    '&:hover': {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

export const ListNode: NodeComponentsMap['listNode'] = memo(
  ({ data, id, selected, dragging }: NodeProps<Node<ListNodeData>>) => {
    const theme = useTheme();
    const { classes } = useStyles();
    const { onDrop, onDragOver } = useDrop();
    const { style: connectionFeedbackStyle } = useConnectorNodeStyle(id, data.nodeDescription.id);
    const { style: dropFeedbackStyle } = useDropNodeStyle(data.isDropNodeTarget, data.isDropNodeCandidate, dragging);
    const nodeStyle = useMemo(
      () => listNodeStyle(theme, data.style, !!selected, data.isHovered, data.faded),
      [data.style, selected, data.isHovered, data.faded]
    );

    const { invokeAction, actions } = useActions({
      diagramElementId: id,
      targetElementId: data.targetObjectId,
      nodeDescriptionId: data.descriptionId,
    });

    const handleOnDrop = (event: React.DragEvent) => {
      onDrop(event, id);
    };

    useRefreshConnectionHandles(id, data.connectionHandles);

    let actionsSection: JSX.Element | null = null;
    if (data.isHovered && actions) {
      actionsSection = (
        <>
          <div className={classes.actionsContainer}>
            {actions.map((action: GQLAction) => {
              return (
                <IconButton
                  key={`action_${action.label}_on_${data.targetObjectLabel}`}
                  className={classes.actionIcon}
                  size="small"
                  onClick={() => invokeAction(action)}>
                  <IconOverlay iconURL={action.iconURL} title={action.label} alt={action.label} />
                </IconButton>
              );
            })}
          </div>
        </>
      );
    }

    return (
      <>
        <Resizer data={data} selected={!!selected} />
        <div
          style={{
            ...nodeStyle,
            ...connectionFeedbackStyle,
            ...dropFeedbackStyle,
          }}
          data-svg="rect"
          onDragOver={onDragOver}
          onDrop={handleOnDrop}
          data-testid={`List - ${data?.insideLabel?.text}`}>
          <div className={classes.labelAndAction}>
            <div className={classes.label}>
              {data.insideLabel && <Label diagramElementId={id} label={data.insideLabel} faded={data.faded} />}
            </div>
            {actionsSection}
          </div>
          {selected ? (
            <DiagramElementPalette
              diagramElementId={id}
              targetObjectId={data.targetObjectId}
              labelId={data.insideLabel ? data.insideLabel.id : null}
            />
          ) : null}
          {selected ? <ConnectionCreationHandles nodeId={id} /> : null}
          <ConnectionTargetHandle nodeId={id} nodeDescription={data.nodeDescription} isHovered={data.isHovered} />
          <ConnectionHandles connectionHandles={data.connectionHandles} />
        </div>
      </>
    );
  }
);

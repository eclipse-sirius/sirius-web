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

import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { diagramPaletteToolExtensionPoint } from '../extensions/DiagramPaletteToolExtensionPoints';
import { Tool } from './../../Tool';
import { DiagramPaletteToolContributionProps } from './../extensions/DiagramPaletteToolContribution.types';
import { AdjustSizeTool } from './AdjustSizeTool';
import { FadeElementTool } from './FadeElementTool';
import { PaletteQuickAccessToolBarProps } from './PaletteQuickAccessToolBar.types';
import { PinUnPinTool } from './PinUnPinTool';
import { ResetEditedEdgePathTool } from './ResetEditedEdgePathTool';
const isPinnable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Node<NodeData> => {
  return !!diagramElement.data && 'pinned' in diagramElement.data;
};
const isFadable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Node<NodeData> => {
  return !!diagramElement.data && 'faded' in diagramElement.data;
};
const isBendable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Edge<EdgeData> => {
  return !!diagramElement.data && 'bendingPoints' in diagramElement.data && !!diagramElement.data.bendingPoints;
};

const useStyle = makeStyles()(() => ({
  quickAccessTools: {
    display: 'flex',
    flexWrap: 'nowrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    overflowX: 'auto',
  },
}));

export const PaletteQuickAccessToolBar = ({
  diagramElementId,
  quickAccessTools,
  onToolClick,
  x,
  y,
}: PaletteQuickAccessToolBarProps) => {
  const { classes } = useStyle();

  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const diagramElement = nodeLookup.get(diagramElementId) || edgeLookup.get(diagramElementId);

  const quickAccessToolComponents: JSX.Element[] = [];
  quickAccessTools.forEach((tool) =>
    quickAccessToolComponents.push(<Tool tool={tool} onClick={onToolClick} thumbnail key={'tool_' + tool.id} />)
  );

  if (diagramElement) {
    if (isPinnable(diagramElement)) {
      quickAccessToolComponents.push(
        <PinUnPinTool
          diagramElementId={diagramElementId}
          isPined={diagramElement.data.pinned}
          key={'tool_pinUnPinTool'}></PinUnPinTool>
      );
    }
    if (isFadable(diagramElement)) {
      quickAccessToolComponents.push(
        <FadeElementTool
          diagramElementId={diagramElementId}
          isFaded={diagramElement.data.faded}
          key={'tool_fadeElementTool'}></FadeElementTool>
      );
    }
    if (isBendable(diagramElement)) {
      quickAccessToolComponents.push(
        <ResetEditedEdgePathTool
          diagramElementId={diagramElementId}
          key={'tool_resetEditedEdgePathTool'}></ResetEditedEdgePathTool>
      );
    }

    quickAccessToolComponents.push(
      <AdjustSizeTool diagramElementId={diagramElementId} key={'tool_adjustSizeTool'}></AdjustSizeTool>
    );

    const paletteToolData: DataExtension<DiagramPaletteToolContributionProps[]> = useData(
      diagramPaletteToolExtensionPoint
    );

    paletteToolData.data
      .filter((data) => data.canHandle(diagramElement))
      .map((data) => data.component)
      .forEach((PaletteToolComponent, index) =>
        quickAccessToolComponents.push(
          <PaletteToolComponent
            x={x}
            y={y}
            diagramElementId={diagramElementId}
            key={'paletteToolComponents_' + index.toString()}
          />
        )
      );
  }

  if (quickAccessToolComponents.length > 0) {
    return (
      <>
        <Box className={classes.quickAccessTools}>{quickAccessToolComponents}</Box>
        <Divider />
      </>
    );
  } else {
    return null;
  }
};

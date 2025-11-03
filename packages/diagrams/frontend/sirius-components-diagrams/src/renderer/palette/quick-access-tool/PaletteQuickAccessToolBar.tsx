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

import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import { Theme } from '@mui/material/styles';
import { Edge, InternalNode, Node, useStoreApi, XYPosition } from '@xyflow/react';
import { EdgeLookup, NodeLookup } from '@xyflow/system';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../../edge/MultiLabelEdge.types';
import { diagramPaletteToolExtensionPoint } from '../extensions/DiagramPaletteToolExtensionPoints';
import { DiagramPaletteToolContributionProps } from './../extensions/DiagramPaletteToolContribution.types';
import { AdjustSizeTool } from './AdjustSizeTool';
import { FadeElementTool } from './FadeElementTool';
import { HideElementTool } from './HideElementTool';
import { PaletteQuickAccessToolBarProps } from './PaletteQuickAccessToolBar.types';
import { PinUnPinTool } from './PinUnPinTool';
import { ResetEditedEdgePathTool } from './ResetEditedEdgePathTool';
import { ResetLabelPositionTool } from './ResetLabelPositionTool';
import { ResetManuallyLaidOutHandlesTool } from './ResetManuallyLaidOutHandlesTool';
import { Tool } from './Tool';
import { ResetMovedByUserTool } from './ResetMovedByUserTool';

/**
 *
 * @technical-debt This component should not render additional quicktools based on the diagramElements used to open the palette.
 * The quicktools available should be computed by the backend see https://github.com/eclipse-sirius/sirius-web/issues/4348
 * This component should not have any coupling with the xyflow library in order to reuse the palette in another representation see https://github.com/eclipse-sirius/sirius-web/issues/5534
 */

const isPinnable = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Node<NodeData>[] => {
  return diagramElements.every((diagramElement) => !!diagramElement.data && 'pinned' in diagramElement.data);
};
const isPined = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Node<NodeData>[] => {
  return diagramElements.every((diagramElement) => !!diagramElement.data && diagramElement.data.pinned);
};
const isFadable = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Node<NodeData>[] => {
  return diagramElements.every((diagramElement) => !!diagramElement.data && 'faded' in diagramElement.data);
};
const isFaded = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Node<NodeData>[] => {
  return diagramElements.every((diagramElement) => !!diagramElement.data && diagramElement.data.faded);
};
const isBendable = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Edge<EdgeData>[] => {
  return diagramElements.every(
    (diagramElement) =>
      !!diagramElement.data &&
      'bendingPoints' in diagramElement.data &&
      (diagramElement.data.bendingPoints as XYPosition[]).length > 0
  );
};
const isMovedByUser = (diagramElements: (Node<NodeData> | Edge<EdgeData>)[]): diagramElements is Node<NodeData>[] => {
  return diagramElements.every(
    (diagramElement) => !!diagramElement.data && diagramElement.data.movedByUser && diagramElement.data.isBorderNode
  );
};
const isPositionSet = (position: XYPosition | undefined) => position && position.x && position.y;
const containsNodeOutsideLabels = (diagramElement: InternalNode<Node<NodeData>> | undefined) => {
  return diagramElement && isPositionSet(diagramElement.data.outsideLabels.BOTTOM_MIDDLE?.position);
};
const containsEdgeOutsideLabels = (diagramElement: Edge<MultiLabelEdgeData> | undefined) => {
  return (
    diagramElement &&
    diagramElement.data &&
    (isPositionSet(diagramElement.data.endLabel?.position) ||
      isPositionSet(diagramElement.data.label?.position) ||
      isPositionSet(diagramElement.data.beginLabel?.position))
  );
};
const containsOutSideLabels = (
  diagramElements: (Node<NodeData> | Edge<EdgeData>)[],
  edgeLookup: EdgeLookup<Edge<EdgeData>>,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>
) => {
  return diagramElements.every((diagramElement) => {
    const edge = edgeLookup.get(diagramElement.id);
    const node = nodeLookup.get(diagramElement.id);
    return (edge && containsEdgeOutsideLabels(edge)) || (node && containsNodeOutsideLabels(node));
  });
};

const useStyle = makeStyles()((theme: Theme) => ({
  quickAccessTools: {
    display: 'flex',
    flexWrap: 'nowrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    gap: theme.spacing(0.5),
    paddingLeft: theme.spacing(0.5),
    overflowX: 'auto',
  },
}));

/**
 *
 * @technical-debt The extension contributed with the diagramPaletteToolExtensionPoint should support a list of diagramElement
 * They should also be contributed using a more generic extension (without coupling to XYFlow library) see https://github.com/eclipse-sirius/sirius-web/pull/5413
 */
export const PaletteQuickAccessToolBar = ({
  diagramElementIds,
  quickAccessTools,
  onToolClick,
  x,
  y,
}: PaletteQuickAccessToolBarProps) => {
  const { classes } = useStyle();

  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  let diagramElements: (InternalNode<Node<NodeData>> | Edge<EdgeData>)[] = diagramElementIds.flatMap((id) => {
    let diagramElement = edgeLookup.get(id) || nodeLookup.get(id);
    if (diagramElement) {
      return [diagramElement];
    } else {
      return [];
    }
  });

  const quickAccessToolComponents: JSX.Element[] = [];
  quickAccessTools.forEach((tool) =>
    quickAccessToolComponents.push(<Tool tool={tool} onClick={onToolClick} key={'tool_' + tool.id} />)
  );

  if (diagramElements.length > 0) {
    if (isPinnable(diagramElements)) {
      quickAccessToolComponents.push(
        <PinUnPinTool
          diagramElementIds={diagramElementIds}
          isPined={isPined(diagramElements)}
          key="tool_pinUnPinTool"
        />
      );
    }
    if (isFadable(diagramElements)) {
      quickAccessToolComponents.push(
        <FadeElementTool
          diagramElementIds={diagramElementIds}
          isFaded={isFaded(diagramElements)}
          key="tool_fadeElementTool"
        />
      );
    }
    if (diagramElements.length > 1) {
      quickAccessToolComponents.push(
        <HideElementTool diagramElementIds={diagramElementIds} key="tool_hideElementTool" />
      );
    }

    if (diagramElements.length === 1 && diagramElements[0]) {
      if (isMovedByUser(diagramElements)) {
        quickAccessToolComponents.push(
          <ResetMovedByUserTool diagramElementId={diagramElements[0].id} key="tool_resetMovedByUser" />
        );
      }

      if (isBendable(diagramElements)) {
        quickAccessToolComponents.push(
          <ResetEditedEdgePathTool diagramElementId={diagramElements[0].id} key="tool_resetEditedEdgePathTool" />
        );
      }

      if (containsOutSideLabels(diagramElements, edgeLookup, nodeLookup))
        quickAccessToolComponents.push(
          <ResetLabelPositionTool
            diagramElementId={diagramElements[0].id}
            key="tool_resetLabelPosition"></ResetLabelPositionTool>
        );

      quickAccessToolComponents.push(
        <ResetManuallyLaidOutHandlesTool
          diagramElementId={diagramElements[0].id}
          key="tool_resetManuallyLaidOutHandlesTool"
        />
      );

      quickAccessToolComponents.push(
        <AdjustSizeTool diagramElementId={diagramElements[0].id} key="tool_adjustSizeTool" />
      );
    }
  }

  if (diagramElementIds.length === 1) {
    const paletteToolData: DataExtension<DiagramPaletteToolContributionProps[]> = useData(
      diagramPaletteToolExtensionPoint
    );

    paletteToolData.data
      .filter((data) => data.canHandle(diagramElements[0] ?? null))
      .map((data) => data.component)
      .forEach((PaletteToolComponent, index) =>
        quickAccessToolComponents.push(
          <PaletteToolComponent
            x={x}
            y={y}
            diagramElementId={diagramElementIds[0] || ''}
            key={'paletteToolComponents_' + index.toString()}
          />
        )
      );
  }

  if (quickAccessToolComponents.length > 0) {
    return (
      <>
        <div>
          <Box className={classes.quickAccessTools}>{quickAccessToolComponents}</Box>
        </div>
        <Divider />
      </>
    );
  } else {
    return null;
  }
};

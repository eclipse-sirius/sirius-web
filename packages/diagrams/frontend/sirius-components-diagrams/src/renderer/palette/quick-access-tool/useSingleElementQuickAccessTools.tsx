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
import { Edge, Node } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { Tool } from '../../Tool';
import { DiagramPaletteToolContributionProps } from '../extensions/DiagramPaletteToolContribution.types';
import { diagramPaletteToolExtensionPoint } from '../extensions/DiagramPaletteToolExtensionPoints';
import { AdjustSizeTool } from './../quick-access-tool/AdjustSizeTool';
import { FadeElementTool } from './../quick-access-tool/FadeElementTool';
import { PinUnPinTool } from './../quick-access-tool/PinUnPinTool';
import { ResetEditedEdgePathTool } from './../quick-access-tool/ResetEditedEdgePathTool';
import {
  UseSingleElementQuickAccessToolProps,
  UseSingleElementQuickAccessToolValue,
} from './useSingleElementQuickAccessTools.types';

const isPinnable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Node<NodeData> => {
  return !!diagramElement.data && 'pinned' in diagramElement.data;
};
const isFadable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Node<NodeData> => {
  return !!diagramElement.data && 'faded' in diagramElement.data;
};
const isBendable = (diagramElement: Node<NodeData> | Edge<EdgeData>): diagramElement is Edge<EdgeData> => {
  return !!diagramElement.data && 'bendingPoints' in diagramElement.data && !!diagramElement.data.bendingPoints;
};

export const useSingleElementQuickAccessTools = ({
  diagramElement,
  diagramElementId,
  quickAccessTools,
  onToolClick,
  x,
  y,
}: UseSingleElementQuickAccessToolProps): UseSingleElementQuickAccessToolValue => {
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

  return { quickAccessToolComponents };
};

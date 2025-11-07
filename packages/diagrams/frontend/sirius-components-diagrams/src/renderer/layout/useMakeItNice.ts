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

import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { RawDiagram } from './layout.types';
import { UseMakeItNiceValue } from './useMakeItNice.types';
import { useSynchronizeLayoutData } from './useSynchronizeLayoutData';

const getNodeWidth = (node: Node<NodeData>): number => {
  if (typeof node.width === 'number' && !Number.isNaN(node.width)) {
    return node.width;
  }
  if (node.measured && typeof node.measured.width === 'number' && !Number.isNaN(node.measured.width)) {
    return node.measured.width;
  }
  if (node.style && typeof node.style.width === 'number' && !Number.isNaN(node.style.width)) {
    return node.style.width;
  }
  if (node.style && typeof node.style.width === 'string') {
    const parsedWidth = parseFloat(node.style.width);
    if (!Number.isNaN(parsedWidth)) {
      return parsedWidth;
    }
  }
  if (typeof node.data.defaultWidth === 'number' && !Number.isNaN(node.data.defaultWidth)) {
    return node.data.defaultWidth;
  }
  return 0;
};

const isResizableDiagramNode = (node: Node<NodeData>): boolean => {
  const isBorderNode = node.data.isBorderNode;
  const isPinned = node.data.pinned;
  const userResizable = node.data.nodeDescription?.userResizable ?? 'BOTH';
  const width = getNodeWidth(node);

  return !isBorderNode && !isPinned && userResizable !== 'NONE' && width > 0;
};

export const useMakeItNice = (): UseMakeItNiceValue => {
  const { getNodes, setNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const makeItNice = useCallback(() => {
    if (readOnly) {
      return;
    }

    const diagramNodes = getNodes();
    console.log(`[make it beautifull] number of nodes : ${diagramNodes.length}`);

    if (diagramNodes.length === 0) {
      return;
    }

    const nodesByDescriptionId = new Map<string, { maxWidth: number; resizableCount: number }>();

    diagramNodes.forEach((node) => {
      const descriptionId = node.data.descriptionId ?? '__no_description__';
      const group = nodesByDescriptionId.get(descriptionId) ?? { maxWidth: 0, resizableCount: 0 };
      if (isResizableDiagramNode(node)) {
        group.resizableCount += 1;
        const width = getNodeWidth(node);
        if (width > group.maxWidth) {
          group.maxWidth = width;
        }
      }
      nodesByDescriptionId.set(descriptionId, group);
    });

    const resizableGroups = Array.from(nodesByDescriptionId.entries()).filter(([, info]) => info.resizableCount > 0);
    console.log(`[make it beautifull] resizable groups: ${resizableGroups.length}`);
    resizableGroups.forEach(([descriptionId, info]) => {
      console.log(
        `[make it beautifull] group ${descriptionId} -> count=${info.resizableCount}, maxWidth=${info.maxWidth}`
      );
    });

    if (resizableGroups.length === 0) {
      return;
    }

    let hasChanges = false;

    const updatedNodes = diagramNodes.map((node) => {
      if (!isResizableDiagramNode(node)) {
        return node;
      }

      const descriptionId = node.data.descriptionId ?? '__no_description__';
      const group = nodesByDescriptionId.get(descriptionId);
      const targetWidth = group?.resizableCount ? group.maxWidth : 0;

      if (targetWidth === 0) {
        return node;
      }

      const currentWidth = getNodeWidth(node);
      if (Math.abs(currentWidth - targetWidth) < 0.5) {
        return node;
      }

      hasChanges = true;

      const nextStyle = node.style ? { ...node.style, width: `${targetWidth}px` } : { width: `${targetWidth}px` };
      const nextMeasured = node.measured
        ? { ...node.measured, width: targetWidth }
        : node.height
        ? { width: targetWidth, height: node.height }
        : undefined;

      console.log(
        `[make it beautifull] updating node ${node.id} (description=${descriptionId}) width ${currentWidth} -> ${targetWidth}`
      );

      return {
        ...node,
        width: targetWidth,
        style: nextStyle,
        measured: nextMeasured,
        data: {
          ...node.data,
          resizedByUser: true,
        },
      };
    });

    if (!hasChanges) {
      return;
    }

    setNodes(updatedNodes);

    const diagram: RawDiagram = {
      nodes: updatedNodes as Node<NodeData, DiagramNodeType>[],
      edges: getEdges(),
    };

    synchronizeLayoutData(crypto.randomUUID(), 'layout', diagram);
  }, [getEdges, getNodes, readOnly, setNodes, synchronizeLayoutData]);

  return { makeItNice };
};

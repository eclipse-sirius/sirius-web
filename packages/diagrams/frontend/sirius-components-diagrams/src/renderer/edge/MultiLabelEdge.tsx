/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { getSmartEdge, pathfindingJumpPointNoDiagonal, svgDrawStraightLinePath } from '@tisoap/react-flow-smart-edge';
import { memo, useCallback, useContext, useEffect, useMemo } from 'react';
import {
  BaseEdge,
  BezierEdge,
  Edge,
  EdgeLabelRenderer,
  EdgeProps,
  Node,
  Position,
  ReactFlowState,
  useNodes,
  useReactFlow,
  useStore,
} from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';

const multiLabelEdgeStyle = (
  theme: Theme,
  style: React.CSSProperties | undefined,
  selected: boolean | undefined,
  faded: boolean | undefined
): React.CSSProperties => {
  const multiLabelEdgeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
    stroke: style?.stroke ? getCSSColor(String(style.stroke), theme) : undefined,
  };

  if (selected) {
    multiLabelEdgeStyle.stroke = `${theme.palette.selected}`;
  }

  return multiLabelEdgeStyle;
};

const roundToNearestTwenty = (num: number): number => Math.round(num / 20) * 20;

const getGreatestNodeAncestor = (node: Node, getNode) => {
  if (node?.parentNode) {
    return getGreatestNodeAncestor(getNode(node?.parentNode), getNode);
  }
  return node.id;
};

const isAncestorOf = (child: Node, candidate: Node, nodeById: (arg0: string) => Node | undefined): boolean => {
  if (child.parentNode === candidate.id) {
    return true;
  } else {
    const chlidParent: Node | undefined = child.parentNode ? nodeById(child.parentNode) : undefined;
    return chlidParent !== undefined && isAncestorOf(chlidParent, candidate, nodeById);
  }
};

const getAncestors = (
  node: Node | undefined,
  nodes: Node[],
  maxAncestorToSearch: Node | undefined = undefined,
  ancestors: Node[] = []
): Node[] => {
  if (!node) {
    return ancestors;
  }
  ancestors.push(node);
  if (node.parentNode || node.id === maxAncestorToSearch?.id) {
    const parentNode = nodes.find((n) => n.id === node.parentNode);
    return getAncestors(parentNode, nodes, maxAncestorToSearch, ancestors);
  } else {
    return ancestors;
  }
};

const getLowestCommunAncestor = (node: Node, nodes: Node[], ancestorIds: string[]): Node | undefined => {
  if (ancestorIds.includes(node.id)) {
    return node;
  }
  if (node.parentNode) {
    const parentNode = nodes.find((n) => n.id === node.parentNode);
    if (parentNode) {
      return getLowestCommunAncestor(parentNode, nodes, ancestorIds);
    } else {
      return undefined;
    }
  } else {
    return undefined;
  }
};

function findLowestCommonAncestor(nodes, sourceNode, targetNode) {
  const sourceAncestorIds = getAncestors(sourceNode, nodes).map((ancestor) => ancestor.id);
  return getLowestCommunAncestor(targetNode, nodes, sourceAncestorIds);
}

export const MultiLabelEdge = memo((props: EdgeProps<MultiLabelEdgeData>) => {
  const {
    id,
    source,
    target,
    data,
    style,
    markerEnd,
    markerStart,
    selected,
    sourcePosition,
    targetPosition,
    sourceHandleId,
    targetHandleId,
  } = props;
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const theme = useTheme();
  const { setEdges, getNode } = useReactFlow<NodeData, EdgeData>();
  const nodes = useNodes();
  const sourceNode = useStore<Node<NodeData> | undefined>(
    useCallback((store: ReactFlowState) => store.nodeInternals.get(source), [source])
  );
  const targetNode = useStore<Node<NodeData> | undefined>(
    useCallback((store: ReactFlowState) => store.nodeInternals.get(target), [target])
  );

  if (!sourceNode || !targetNode) {
    return null;
  }

  let { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(sourceNode, sourcePosition, sourceHandleId ?? '');
  let { x: targetX, y: targetY } = getHandleCoordinatesByPosition(targetNode, targetPosition, targetHandleId ?? '');

  // trick to have the source of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleSourceRadius = markerStart == undefined || markerStart.includes('None') ? 2 : 3;
  switch (sourcePosition) {
    case Position.Right:
      sourceX = sourceX + handleSourceRadius;
      sourceY = roundToNearestTwenty(sourceY);
      break;
    case Position.Left:
      sourceX = sourceX - handleSourceRadius;
      sourceY = roundToNearestTwenty(sourceY);
      break;
    case Position.Top:
      sourceY = sourceY - handleSourceRadius;
      sourceX = roundToNearestTwenty(sourceX);
      break;
    case Position.Bottom:
      sourceY = sourceY + handleSourceRadius;
      sourceX = roundToNearestTwenty(sourceX);
      break;
  }
  // trick to have the target of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleTargetRadius = markerEnd == undefined || markerEnd.includes('None') ? 2 : 3;
  switch (targetPosition) {
    case Position.Right:
      targetX = targetX + handleTargetRadius;
      targetY = roundToNearestTwenty(targetY);
      break;
    case Position.Left:
      targetX = targetX - handleTargetRadius;
      targetY = roundToNearestTwenty(targetY);
      break;
    case Position.Top:
      targetY = targetY - handleTargetRadius;
      targetX = roundToNearestTwenty(targetX);
      break;
    case Position.Bottom:
      targetY = targetY + handleTargetRadius;
      targetX = roundToNearestTwenty(targetX);
      break;
  }

  const nodesHierarchy = nodes.map((node) => ({
    id: node.id,
    parentNode: node.parentNode,
  }));
  const nodesPosition = nodes.map((node) => {
    const position =
      node.id === sourceNode.id || node.id === targetNode.id
        ? node.position
        : {
            x: roundToNearestTwenty(node.position.x),
            y: roundToNearestTwenty(node.position.y),
          };

    return {
      id: node.id,
      position: position,
      width: node.width,
      height: node.height,
    };
  });
  const lowestCommonAncestor = useMemo(
    () => findLowestCommonAncestor(nodes, sourceNode, targetNode),
    [JSON.stringify(nodesHierarchy)]
  );

  const sourceAncestorIds: string[] = useMemo(
    () => getAncestors(getNode(sourceNode.parentNode ?? ''), nodes, lowestCommonAncestor).map((node) => node.id),
    [JSON.stringify(nodesHierarchy), lowestCommonAncestor, sourceNode]
  );

  const targetAncestorIds: string[] = useMemo(
    () => getAncestors(getNode(targetNode.parentNode ?? ''), nodes, lowestCommonAncestor).map((node) => node.id),
    [JSON.stringify(nodesHierarchy), lowestCommonAncestor, targetNode]
  );

  const nodeIdsToConsider: string[] = useMemo(() => {
    return nodes
      .filter((node) => {
        if (node.id === sourceNode.id || node.id === targetNode.id) {
          return true;
        }
        if (
          (sourceNode.data.isBorderNode && node.id === sourceNode.parentNode) ||
          (targetNode.data.isBorderNode && node.id === targetNode.parentNode)
        ) {
          return true;
        }
        const sourceAncestorSiblings =
          sourceAncestorIds.includes(node.parentNode ?? '') || node.parentNode === lowestCommonAncestor;
        const targetAncestorSiblings = targetAncestorIds.includes(node.parentNode ?? '');
        const isDirectAncestor = sourceAncestorIds.includes(node.id) || targetAncestorIds.includes(node.id);
        return (sourceAncestorSiblings || targetAncestorSiblings) && !isDirectAncestor;
      })
      .map((node) => node.id);
  }, [JSON.stringify(nodesHierarchy), JSON.stringify(sourceAncestorIds), JSON.stringify(targetAncestorIds)]);

  const nodesToConsider: Node[] = useMemo(() => {
    return nodes.filter((node) => nodeIdsToConsider.includes(node.id));
  }, [JSON.stringify(nodeIdsToConsider), JSON.stringify(nodesPosition)]);

  const getSmartEdgeResponse = useMemo(() => {
    return getSmartEdge({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
      nodes: nodesToConsider,
      options: {
        nodePadding: 5,
        gridRatio: 20,
        drawEdge: svgDrawStraightLinePath,
        generatePath: pathfindingJumpPointNoDiagonal,
      },
    });
  }, [JSON.stringify(nodesToConsider)]);

  const { beginLabel, endLabel, label, faded } = data || {};

  useEffect(() => {
    setEdges((edges: Edge<EdgeData>[]) =>
      edges.map((edge) => {
        if (edge.id === id) {
          edge.updatable = selected && !readOnly;
        }
        return edge;
      })
    );
  }, [selected]);

  const getTranslateFromHandlePositon = (position: Position) => {
    switch (position) {
      case Position.Right:
        return 'translate(2%, -100%)';
      case Position.Left:
        return 'translate(-102%, -100%)';
      case Position.Top:
        return 'translate(2%, -100%)';
      case Position.Bottom:
        return 'translate(2%, 0%)';
    }
  };

  if (getSmartEdgeResponse === null) {
    return <BezierEdge {...props} />;
  }
  const { edgeCenterX, edgeCenterY, svgPathString } = getSmartEdgeResponse;
  return (
    <>
      <BaseEdge
        id={id}
        path={svgPathString}
        style={multiLabelEdgeStyle(theme, style, selected, faded)}
        markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 1)}--selected)` : markerEnd}
        markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 1)}--selected)` : markerStart}
      />
      {selected ? <DiagramElementPalette diagramElementId={id} labelId={label ? label.id : null} /> : null}
      <EdgeLabelRenderer>
        {beginLabel && (
          <Label
            diagramElementId={id}
            transform={`${getTranslateFromHandlePositon(sourcePosition)} translate(${sourceX}px,${sourceY}px)`}
            label={beginLabel}
            faded={faded || false}
          />
        )}
        {label && (
          <Label
            diagramElementId={id}
            transform={`translate(${edgeCenterX}px,${edgeCenterY}px)`}
            label={label}
            faded={false}
          />
        )}
        {endLabel && (
          <Label
            diagramElementId={id}
            transform={`${getTranslateFromHandlePositon(targetPosition)} translate(${targetX}px,${targetY}px)`}
            label={endLabel}
            faded={faded || false}
          />
        )}
      </EdgeLabelRenderer>
    </>
  );
});

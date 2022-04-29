/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import 'reflect-metadata';
import {
  AnchorComputerRegistry,
  CommandExecutionContext,
  EdgeRouterRegistry,
  ElementMove,
  getAbsoluteBounds,
  MoveAction,
  RoutedPoint,
  SEdge,
  SGraphIndex,
  SLabel,
  SModelElement,
  SModelRoot,
  SNode,
  SParentElement,
  SRoutingHandle,
} from 'sprotty';
import { Bounds, centerOfLine, Point } from 'sprotty-protocol';
import { Edge, Ratio } from '../../Diagram.types';
import { SiriusRectangleAnchor } from '../../routing/siriusPolylineAnchor';
import { SiriusPolylineEdgeRouter } from '../../routing/siriusPolylineEdgeRouter';
import { SiriusMoveCommand } from '../siriusMove';

const createNode = (nodeId: string, parent: SParentElement, bounds: Bounds): SNode => {
  const node: SNode = new SNode();
  node.id = nodeId;
  node.bounds = { ...bounds };

  parent.add(node);
  return node;
};

const createLabel = (text: string, routedPoints: RoutedPoint[]): SLabel => {
  const label = new SLabel();
  label.text = text;
  label.size = {
    width: 60,
    height: 8,
  };

  let labelPosition = Point.ORIGIN;
  if (routedPoints.length & 1) {
    labelPosition = routedPoints[Math.floor(routedPoints.length / 2)];
    labelPosition = Point.subtract(labelPosition, { x: 30, y: 10 });
  } else {
    const firstSegmentBoundPositionIndex = Math.floor(routedPoints.length / 2);
    labelPosition = centerOfLine(
      routedPoints[firstSegmentBoundPositionIndex - 1],
      routedPoints[firstSegmentBoundPositionIndex]
    );
    if (routedPoints.length === 2) {
      labelPosition = { x: labelPosition.x - 30, y: labelPosition.y + 2 };
    } else {
      labelPosition = Point.subtract(labelPosition, { x: 30, y: 10 });
    }
  }
  label.position = labelPosition;
  return label;
};

const createEdge = (
  edgeRouterRegistry: EdgeRouterRegistry,
  sourceId: string,
  targetId: string,
  parent: SParentElement,
  routingPoints: Point[] = [],
  sourceAnchorRatio: Ratio,
  targetAnchorRatio: Ratio
): Edge => {
  const edge = new Edge();
  edge.routerKind = 'polyline';
  edge.sourceId = sourceId;
  edge.targetId = targetId;
  edge.sourceAnchorRelativePosition = sourceAnchorRatio;
  edge.targetAnchorRelativePosition = targetAnchorRatio;
  edge.routingPoints = [...routingPoints];
  parent.add(edge);

  const routedPoints: RoutedPoint[] = edgeRouterRegistry.route(edge);

  const label = createLabel(`${sourceId} -> ${targetId}`, routedPoints);
  edge.add(label);

  return edge;
};

const createDefaultEdgeRouterRegistry = (): EdgeRouterRegistry => {
  const anchorComputerRegistry = new AnchorComputerRegistry([new SiriusRectangleAnchor()]);

  const siriusPolylineEdgeRouter = new SiriusPolylineEdgeRouter();
  siriusPolylineEdgeRouter.anchorRegistry = anchorComputerRegistry;

  return new EdgeRouterRegistry([siriusPolylineEdgeRouter]);
};

const createMoveCommand = (moveAction: MoveAction, edgeRouterRegistry: EdgeRouterRegistry): SiriusMoveCommand => {
  const command = new SiriusMoveCommand(moveAction);
  command.edgeRouterRegistry = edgeRouterRegistry;
  return command;
};

const getRoutingHandleToMove = (edge: SEdge): SRoutingHandle => {
  return edge.children.filter(isRoutingHandle).find((handle) => handle.pointIndex === 0 && handle.kind === 'junction');
};

const isRoutingHandle = (element: SModelElement): element is SRoutingHandle => {
  return element instanceof SRoutingHandle;
};

// const createSwitchEditCommand = (
//   switchEditAction: SwitchEditModeAction,
//   edgeRouterRegistry: EdgeRouterRegistry
// ): SwitchEditModeCommand => {
//   const command = new SwitchEditModeCommand(switchEditAction);
//   command.edgeRouterRegistry = edgeRouterRegistry;
//   return command;
// };

const createCommandExecutionContext = (model: SModelRoot): CommandExecutionContext => {
  return {
    root: model,
    modelFactory: undefined,
    duration: 0,
    modelChanged: undefined!,
    logger: undefined,
    syncer: undefined,
  };
};

describe('Sirius move', () => {
  it('Edge end move - update "contained" edge routing points', () => {
    // The moved node contains two children nodes connected between each other by an edge that have one routing point.
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const nodeContainer = createNode('nodeContainer', modelRoot, { x: 10, y: 10, width: 100, height: 100 });
    const node1 = createNode('node1', nodeContainer, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', nodeContainer, { x: 40, y: 50, width: 40, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [{ x: 25, y: 80 }],
      { x: 0.25, y: 0.25 },
      { x: 0.75, y: 0.25 }
    );

    const moves: ElementMove[] = [
      {
        elementId: nodeContainer.id,
        toPosition: {
          x: nodeContainer.position.x + 10,
          y: nodeContainer.position.y,
        },
      },
    ];

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    expect(nodeContainer.position).toEqual<Point>({ x: 20, y: 10 });
    expect(node1.position).toEqual<Point>({ x: 10, y: 10 });
    expect(node2.position).toEqual<Point>({ x: 40, y: 50 });
    expect(edge.routingPoints).toHaveLength(1);
    expect(edge.routingPoints[0]).toEqual<Point>({ x: 35, y: 80 });
    expect(edge.children).toHaveLength(1);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 5, y: 70 });
  });

  it('Edge end move - update edge label for a straigh edge', () => {
    // The moved node contains a child node that have a straight edge to another node outside of the moved node.
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const nodeContainer = createNode('nodeContainer', modelRoot, { x: 10, y: 10, width: 40, height: 40 });
    const node1 = createNode('node1', nodeContainer, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', modelRoot, { x: 90, y: 20, width: 20, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [],
      { x: 0.5, y: 0.5 },
      { x: 0.5, y: 0.5 }
    );

    const moves: ElementMove[] = [
      {
        elementId: nodeContainer.id,
        toPosition: {
          x: 80,
          y: 80,
        },
      },
    ];

    expect(edge.children).toHaveLength(1);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 35, y: 32 });

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    expect(nodeContainer.position).toEqual<Point>({ x: 80, y: 80 });
    const node1AbsoluteBounds = getAbsoluteBounds(node1);
    expect(node1AbsoluteBounds.x).toBe(90);
    expect(node1AbsoluteBounds.y).toBe(90);
    expect(edge.routingPoints).toHaveLength(0);

    // Check the label is updated
    expect(edge.children).toHaveLength(1);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 70, y: 67 });
  });

  it('Edge end move - do not update neither the edge routing points, nor the edge label if it has routing points', () => {
    // The moved node should contain a child node have a edge with couting to another node outside of the moved node.
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const nodeContainer = createNode('nodeContainer', modelRoot, { x: 10, y: 10, width: 40, height: 40 });
    const node1 = createNode('node1', nodeContainer, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', modelRoot, { x: 90, y: 90, width: 20, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [{ x: 100, y: 30 }],
      { x: 0.5, y: 0.5 },
      { x: 0.5, y: 0.5 }
    );

    const moves: ElementMove[] = [
      {
        elementId: nodeContainer.id,
        toPosition: {
          x: 10,
          y: 80,
        },
      },
    ];

    expect(edge.children).toHaveLength(1);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 70, y: 20 });

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    expect(nodeContainer.position).toEqual<Point>({ x: 10, y: 80 });
    const node1AbsoluteBounds = getAbsoluteBounds(node1);
    expect(node1AbsoluteBounds.x).toBe(20);
    expect(node1AbsoluteBounds.y).toBe(90);

    // Check the routing point has not been updated
    expect(edge.routingPoints).toHaveLength(1);
    expect(edge.routingPoints[0]).toEqual<Point>({ x: 100, y: 30 });

    // Check the label has not been updated
    expect(edge.children).toHaveLength(1);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 70, y: 20 });
  });

  it('Edge handle move - update edge label position (odd number of routing points)', () => {
    // It should give an edge2Move to the `SiriusMoveCommand.doMove`. The moved handled should be in the middle of the edge.
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const node1 = createNode('node1', modelRoot, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', modelRoot, { x: 60, y: 10, width: 20, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [{ x: 45, y: 60 }],
      { x: 0.5, y: 0.5 },
      { x: 0.5, y: 0.5 }
    );

    const edgeRouter = edgeRouterRegistry.get(edge.kind);
    edgeRouter.createRoutingHandles(edge);

    const routingHandleToMove = getRoutingHandleToMove(edge);

    const moves: ElementMove[] = [
      {
        elementId: routingHandleToMove.id,
        toPosition: {
          x: 45,
          y: 100,
        },
      },
    ];

    expect(edge.children).toHaveLength(6);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 15, y: 50 });

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    // Check the routing points has been moved
    expect(edge.routingPoints).toHaveLength(1);
    expect(edge.routingPoints[0]).toEqual<Point>({ x: 45, y: 100 });

    // Check the label has been moved
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 15, y: 90 });
  });

  it('Edge handle move - update edge label position (even number of routing points)', () => {
    // It should give an edge2Move to the `SiriusMoveCommand.doMove`. The moved handled should move the middle section of the edge.
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const node1 = createNode('node1', modelRoot, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', modelRoot, { x: 60, y: 10, width: 20, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [
        { x: 35, y: 60 },
        { x: 55, y: 60 },
      ],
      { x: 0.5, y: 0.5 },
      { x: 0.5, y: 0.5 }
    );

    const edgeRouter = edgeRouterRegistry.get(edge.kind);
    edgeRouter.createRoutingHandles(edge);

    const routingHandleToMove = getRoutingHandleToMove(edge);

    const moves: ElementMove[] = [
      {
        elementId: routingHandleToMove.id,
        toPosition: {
          x: 35,
          y: 90,
        },
      },
    ];

    expect(edge.children).toHaveLength(8);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 15, y: 50 });

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    // Check the routing points has been moved
    expect(edge.routingPoints).toHaveLength(2);
    expect(edge.routingPoints[0]).toEqual<Point>({ x: 35, y: 90 });

    // Check the label has been moved
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 15, y: 65 });
  });

  it('Edge handle move - do not update edge label whether the label is neither on the moving handle, nor a moving edge section', () => {
    // It should give an edge2Move to the `SiriusMoveCommand.doMove`. The moved handled should neither be in the middle of the edge nor move the middle section of the edge
    const modelRoot = new SModelRoot(new SGraphIndex());
    const executionContext = createCommandExecutionContext(modelRoot);
    const edgeRouterRegistry = createDefaultEdgeRouterRegistry();

    const node1 = createNode('node1', modelRoot, { x: 10, y: 10, width: 20, height: 20 });
    const node2 = createNode('node2', modelRoot, { x: 60, y: 10, width: 20, height: 20 });

    const edge = createEdge(
      edgeRouterRegistry,
      node1.id,
      node2.id,
      modelRoot,
      [
        { x: 35, y: 20 },
        { x: 35, y: 60 },
        { x: 55, y: 60 },
      ],
      { x: 0.5, y: 0.5 },
      { x: 0.5, y: 0.5 }
    );

    const edgeRouter = edgeRouterRegistry.get(edge.kind);
    edgeRouter.createRoutingHandles(edge);

    const routingHandleToMove = getRoutingHandleToMove(edge);

    const moves: ElementMove[] = [
      {
        elementId: routingHandleToMove.id,
        toPosition: {
          x: 45,
          y: 20,
        },
      },
    ];

    expect(edge.children).toHaveLength(10);
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 5, y: 50 });

    const siriusCommand = createMoveCommand(MoveAction.create(moves, { animate: false }), edgeRouterRegistry);
    siriusCommand.execute(executionContext);

    // Check the routing points has been moved
    expect(edge.routingPoints).toHaveLength(3);
    expect(edge.routingPoints[0]).toEqual<Point>({ x: 45, y: 20 });

    // Check the label has not been moved
    expect(edge.children[0]).toBeInstanceOf(SLabel);
    expect((edge.children[0] as SLabel).position).toEqual({ x: 5, y: 50 });
  });
});

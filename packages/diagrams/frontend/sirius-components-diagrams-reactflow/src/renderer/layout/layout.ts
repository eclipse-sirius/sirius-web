/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import ELK, { ElkExtendedEdge, ElkLabel, ElkNode, LayoutOptions } from 'elkjs/lib/elk.bundled.js';
import { Fragment, createElement } from 'react';
import ReactDOM from 'react-dom';
import { Box, Edge, Node, ReactFlowProvider, Rect, boxToRect, rectToBox } from 'reactflow';
import { Diagram, NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramDirectEditContextProvider } from '../direct-edit/DiagramDirectEditContext';
import { ListNode } from '../node/ListNode';
import { ListNodeData } from '../node/ListNode.types';
import { RectangularNodeData } from '../node/RectangularNode.types';

const emptyNodeProps = {
  selected: false,
  isConnectable: true,
  dragging: false,
  xPos: 0,
  yPos: 0,
  zIndex: -1,
};

const emptyListNodeProps = {
  ...emptyNodeProps,
  type: 'listNode',
};

const elk = new ELK();

export const prepareLayoutArea = (diagram: Diagram, renderCallback: () => void): HTMLDivElement => {
  const hiddenContainer: HTMLDivElement = document.createElement('div');
  hiddenContainer.id = 'hidden-container';
  hiddenContainer.style.display = 'inline-block';
  hiddenContainer.style.position = 'absolute';
  hiddenContainer.style.visibility = 'hidden';
  hiddenContainer.style.zIndex = '-1';
  document.body.appendChild(hiddenContainer);
  const elements: JSX.Element[] = [];
  const visibleNodes = diagram.nodes.filter((node) => !node.hidden);

  // Render all label first
  const labelElements: JSX.Element[] = [];
  visibleNodes.forEach((node, index) => {
    if (hiddenContainer && node.data.label) {
      const children: JSX.Element[] = [
        createElement(Label, { label: node.data.label, faded: false, key: node.data.label.id }),
      ];
      const element: JSX.Element = createElement('div', {
        id: `${node.id}-label-${index}`,
        key: `${node.id}-label-${index}`,
        children,
      });
      labelElements.push(element);
    }
  });

  // The container used to render label is a flex container authorizing wrapping.
  // It wrap direct children, and thus, prevent label to wrap (because their container will have always enough place to render on the line, unless the label is larger than the screen).
  const labelContainerElement: JSX.Element = createElement('div', {
    id: 'hidden-label-container',
    key: 'hidden-label-container',
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      alignItems: 'flex-start',
    },
    children: labelElements,
  });
  elements.push(labelContainerElement);

  // then, render list node with list item only
  const listNodeElements: JSX.Element[] = [];
  visibleNodes.forEach((node, index) => {
    if (hiddenContainer && node.type === 'listNode') {
      const data = node.data as ListNodeData;
      const listNode = createElement(ListNode, {
        ...emptyListNodeProps,
        id: node.id,
        data,
        key: `${node.id}-${index}`,
      });

      const element: JSX.Element = createElement('div', {
        id: `${node.id}-${index}`,
        key: node.id,
        children: listNode,
      });
      listNodeElements.push(element);
    }
  });
  const nodeListContainerElement: JSX.Element = createElement('div', {
    id: 'hidden-nodeList-container',
    key: 'hidden-nodeList-container',
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      alignItems: 'flex-start',
    },
    children: listNodeElements,
  });
  elements.push(nodeListContainerElement);

  const hiddenContainerContentElements: JSX.Element = createElement(Fragment, { children: elements });
  const diagramDirectEditContextProvider = createElement(DiagramDirectEditContextProvider, {
    children: hiddenContainerContentElements,
  });
  ReactDOM.render(
    createElement(ReactFlowProvider, { children: diagramDirectEditContextProvider }),
    hiddenContainer,
    renderCallback
  );
  return hiddenContainer;
};

export const cleanLayoutArea = (container: HTMLDivElement) => {
  if (container?.parentNode) {
    container.parentNode.removeChild(container);
  }
};

const gap = 20;
const rectangularNodePadding = 8;
const defaultWidth = 150;
const defaultHeight = 70;
const borderLeftAndRight = 2;

export const layout = (diagram: Diagram): Diagram => {
  layoutDiagram(diagram);
  return diagram;
};

const findNodeIndex = (nodes: Node[], refNodeId: string): number => nodes.findIndex((node) => node.id === refNodeId);

const layoutDiagram = (diagram: Diagram) => {
  const allVisibleNodes = diagram.nodes.filter((node) => !node.hidden);
  const nodesToLayout = allVisibleNodes.filter((node) => !node.parentNode);

  layoutNodes(allVisibleNodes, nodesToLayout);
  // Update position of root nodes
  nodesToLayout.forEach((rootNode, index) => {
    rootNode.position = { x: 0, y: 0 };
    if (index > 0) {
      const previousSibling = nodesToLayout[index - 1];
      rootNode.position = { x: previousSibling.position.x + (previousSibling.width ?? 0) + gap, y: 0 };
    }
  });
};

const layoutNodes = (allVisibleNodes: Node[], nodesToLayout: Node<NodeData>[]) => {
  nodesToLayout.forEach((nodeToLayout) => {
    const directChildren = allVisibleNodes.filter((node) => node.parentNode === nodeToLayout.id);
    if (nodeToLayout.type === 'rectangularNode') {
      if (directChildren.length > 0) {
        layoutNodes(allVisibleNodes, directChildren);

        const labelElement = document.getElementById(
          `${nodeToLayout.id}-label-${findNodeIndex(allVisibleNodes, nodeToLayout.id)}`
        );

        // Update children position to be under the label and at the right padding.
        directChildren.forEach((child, index) => {
          child.position = {
            x: rectangularNodePadding,
            y: rectangularNodePadding + (labelElement?.getBoundingClientRect().height ?? 0) + rectangularNodePadding,
          };
          if (index > 0) {
            const previousSibling = directChildren[index - 1];
            child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
          }
        });

        // Update node to layout size
        // WARN: We suppose label are always on top of children (that wrong)
        const childrenFootprint = getChildrenFootprint(directChildren);
        const childrenAwareNodeWidth = childrenFootprint.x + childrenFootprint.width + rectangularNodePadding;
        const labelOnlyWidth =
          rectangularNodePadding + (labelElement?.getBoundingClientRect().width ?? 0) + rectangularNodePadding;
        const nodeWidth = Math.max(childrenAwareNodeWidth, labelOnlyWidth);
        nodeToLayout.width = getNodeOrMinWidth(nodeWidth + borderLeftAndRight);
        nodeToLayout.height = getNodeOrMinHeight(
          rectangularNodePadding +
            (labelElement?.getBoundingClientRect().height ?? 0) +
            rectangularNodePadding +
            childrenFootprint.height +
            rectangularNodePadding
        );
      } else {
        const labelElement = document.getElementById(
          `${nodeToLayout.id}-label-${findNodeIndex(allVisibleNodes, nodeToLayout.id)}`
        );

        const labelWidth =
          rectangularNodePadding +
          (labelElement?.getBoundingClientRect().width ?? 0) +
          rectangularNodePadding +
          borderLeftAndRight;
        const labelHeight =
          rectangularNodePadding + (labelElement?.getBoundingClientRect().height ?? 0) + rectangularNodePadding;
        const nodeWidth = getNodeOrMinWidth(labelWidth);
        const nodeHeight = getNodeOrMinHeight(labelHeight);
        nodeToLayout.width = nodeWidth;
        nodeToLayout.height = nodeHeight;
      }
    } else if (nodeToLayout.type === 'imageNode') {
      nodeToLayout.width = defaultWidth;
      nodeToLayout.height = defaultHeight;
    } else if (nodeToLayout.type === 'listNode') {
      const nodeList = document.getElementById(`${nodeToLayout.id}-${findNodeIndex(allVisibleNodes, nodeToLayout.id)}`)
        ?.children[0];

      nodeToLayout.width = getNodeOrMinWidth(nodeList?.getBoundingClientRect().width);
      nodeToLayout.height = getNodeOrMinHeight(nodeList?.getBoundingClientRect().height);
    }
    nodeToLayout.style = {
      ...nodeToLayout.style,
      width: `${nodeToLayout.width}px`,
      height: `${nodeToLayout.height}px`,
    };
  });
};

const getNodeOrMinWidth = (nodeWidth: number | undefined): number => {
  return Math.max(nodeWidth ?? -Infinity, defaultWidth);
};

const getNodeOrMinHeight = (nodeHeight: number | undefined): number => {
  return Math.max(nodeHeight ?? -Infinity, defaultHeight);
};

const getChildrenFootprint = (children: Node[]): Rect => {
  const footPrint: Box = children.reduce(
    (currentFootPrint, node) => {
      const { x, y } = node.position;
      const nodeBox = rectToBox({
        x,
        y,
        width: node.width ?? 0,
        height: node.height ?? 0,
      });

      return getBoundsOfBoxes(currentFootPrint, nodeBox);
    },
    { x: Infinity, y: Infinity, x2: -Infinity, y2: -Infinity }
  );
  return boxToRect(footPrint);
};

const getBoundsOfBoxes = (box1: Box, box2: Box): Box => {
  return {
    x: Math.min(box1.x, box2.x),
    y: Math.min(box1.y, box2.y),
    x2: Math.max(box1.x2, box2.x2),
    y2: Math.max(box1.y2, box2.y2),
  };
};

export const performDefaultAutoLayout = (nodes: Node[], edges: Edge[]): Promise<{ nodes: Node[] }> => {
  const layoutOptions: LayoutOptions = {
    'elk.algorithm': 'layered',
    'elk.layered.spacing.nodeNodeBetweenLayers': '100',
    'org.eclipse.elk.hierarchyHandling': 'INCLUDE_CHILDREN',
    'layering.strategy': 'NETWORK_SIMPLEX',
    'elk.spacing.nodeNode': '80',
    'elk.direction': 'DOWN',
    'elk.layered.spacing.edgeNodeBetweenLayers': '30',
  };
  return performAutoLayout(nodes, edges, layoutOptions);
};

export const performAutoLayout = (
  nodes: Node[],
  edges: Edge[],
  layoutOptions: LayoutOptions
): Promise<{ nodes: Node[] }> => {
  const graph: ElkNode = {
    id: 'root',
    layoutOptions,
    children: [],
    edges: [],
  };
  const hiddenNodes = nodes.filter((node) => node.hidden);
  const visibleNodes = nodes.filter((node) => !node.hidden);
  const nodeId2Node = new Map<string, Node>();
  visibleNodes.forEach((node) => nodeId2Node.set(node.id, node));
  const nodeId2ElkNode = new Map<String, ElkNode>();
  visibleNodes.forEach((node) => {
    const elkNode: ElkNode = {
      id: node.id,
      children: [],
      labels: [],
      layoutOptions: {
        'nodeLabels.placement': '[H_CENTER, V_TOP, INSIDE]',
      },
    };
    if (node.type === 'rectangularNode') {
      const rectangularNodeData: RectangularNodeData = node.data as RectangularNodeData;

      const label = document.querySelector<HTMLDivElement>(`[data-id="${rectangularNodeData.label?.id}"]`);
      if (label) {
        const elkLabel: ElkLabel = {
          width: label.getBoundingClientRect().width,
          height: label.getBoundingClientRect().height,
          text: rectangularNodeData.label?.text,
        };

        elkNode.labels?.push(elkLabel);
      }
    }

    const element = document.querySelector(`[data-id="${node.id}"]`);
    if (element) {
      elkNode.width = element.getBoundingClientRect().width;
      elkNode.height = element.getBoundingClientRect().height;
    }

    nodeId2ElkNode.set(elkNode.id, elkNode);
  });
  visibleNodes.forEach((node) => {
    if (graph.children) {
      if (!!node.parentNode && !!nodeId2ElkNode.get(node.parentNode)) {
        const elknodeChild = nodeId2ElkNode.get(node.id);
        const elkNodeParent = nodeId2ElkNode.get(node.parentNode);
        if (elkNodeParent && elkNodeParent.children && elknodeChild) {
          elkNodeParent.children.push(elknodeChild);
        }
      } else {
        const elkNodeRoot = nodeId2ElkNode.get(node.id);
        if (elkNodeRoot) {
          graph.children.push(elkNodeRoot);
        }
      }
    }
  });

  edges
    .filter((edge) => !edge.hidden)
    .forEach((edge) => {
      if (graph.edges) {
        const elkEdge: ElkExtendedEdge = {
          id: edge.id,
          sources: [edge.source],
          targets: [edge.target],
        };
        graph.edges.push(elkEdge);
      }
    });

  return elk
    .layout(graph)
    .then((laidoutGraph) => elkToReactFlow(laidoutGraph.children ?? [], nodeId2Node))
    .then((laidoutNodes) => {
      return {
        nodes: [...laidoutNodes, ...hiddenNodes],
      };
    });
};

const elkToReactFlow = (elkNodes: ElkNode[], nodeId2Node: Map<string, Node>): Node[] => {
  const nodes: Node[] = [];
  elkNodes.forEach((elkNode) => {
    const node = nodeId2Node.get(elkNode.id);
    if (node) {
      if (!node.position) {
        node.position = { x: 0, y: 0 };
      }
      node.position.x = elkNode.x ?? 0;
      node.position.y = elkNode.y ?? 0;
      node.width = elkNode.width ?? 150;
      node.height = elkNode.height ?? 70;
      if (node.style) {
        if (node.style.width) {
          node.style.width = `${node.width}px`;
        }
        if (node.style.height) {
          node.style.height = `${node.height}px`;
        }
      }
      nodes.push(node);
    }
    if (elkNode.children && elkNode.children.length > 0) {
      const laidoutChildren = elkToReactFlow(elkNode.children, nodeId2Node);
      nodes.push(...laidoutChildren);
    }
  });
  return nodes;
};

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

import { ApolloClient, InMemoryCache } from '@apollo/client/core';
import { ApolloProvider } from '@apollo/client/react';
import { MessageOptions, ServerContext, ToastContext, theme } from '@eclipse-sirius/sirius-components-core';
import { ThemeProvider } from '@material-ui/core/styles';
import ELK, { ElkExtendedEdge, ElkLabel, ElkNode, LayoutOptions } from 'elkjs/lib/elk.bundled.js';
import { Fragment, createElement } from 'react';
import ReactDOM from 'react-dom';
import { Edge, Node, ReactFlowProvider } from 'reactflow';
import { Diagram, NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramDirectEditContextProvider } from '../direct-edit/DiagramDirectEditContext';
import { IconLabelNodeData } from '../node/IconsLabelNode.types';
import { ListNode } from '../node/ListNode';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { RectangularNode } from '../node/RectangularNode';
import { RectangularNodeData } from '../node/RectangularNode.types';
import { ReferencePosition } from './LayoutContext.types';
import { LayoutEngine } from './LayoutEngine';
import { isEastBorderNode, isWestBorderNode } from './layoutBorderNodes';
import { getChildren } from './layoutNode';

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

const emptyRectangularNodeProps = {
  ...emptyNodeProps,
  type: 'rectangularNode',
};

const isIconLabelNode = (node: Node<NodeData>): node is Node<IconLabelNodeData> => node.type === 'iconLabelNode';
const isListNode = (node: Node<NodeData>): node is Node<ListNodeData> => node.type === 'listNode';
const isRectangularNode = (node: Node<NodeData>): node is Node<RectangularNodeData> => node.type === 'rectangularNode';

const elk = new ELK();

export const prepareLayoutArea = (diagram: Diagram, renderCallback: () => void, httpOrigin: string): HTMLDivElement => {
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
  /**
   * WARN: The height label computed in the hidden-container will be slightly different from the label rendered in the reactflow diagram.
   * This difference is due to the line-height which is set in `variables.css` to 1.5 globaly but in `reset.css` the line-height is
   * overridden to `normal` for element with [role=button] which seem to be the case for all reactflow nodes.
   * That should not impact the node size since it is calculated with the higher value of line-height.
   */
  const labelElements: JSX.Element[] = [];
  visibleNodes.forEach((node, index) => {
    if (hiddenContainer && node.data.label) {
      const children: JSX.Element[] = [
        createElement(Label, {
          diagramElementId: node.id,
          label: node.data.label,
          faded: false,
          transform: '',
          key: node.data.label.id,
        }),
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

  const nodeElements: JSX.Element[] = [];
  visibleNodes.forEach((node, index) => {
    if (hiddenContainer && node) {
      const children: JSX.Element[] = [];
      if (isRectangularNode(node)) {
        const element = createElement(RectangularNode, {
          ...emptyRectangularNodeProps,
          id: node.id,
          data: node.data,
          key: `${node.id}-${index}`,
        });
        children.push(element);
      }
      if (isListNode(node)) {
        const element = createElement(ListNode, {
          ...emptyListNodeProps,
          id: node.id,
          data: node.data,
          key: `${node.id}-${index}`,
        });
        children.push(element);
      }
      if (children.length > 0) {
        const elementWrapper: JSX.Element = createElement('div', {
          id: `${node.id}-${node.type}-${index}`,
          key: node.id,
          children,
        });
        nodeElements.push(elementWrapper);
      }
    }
  });

  const nodeContainerElement: JSX.Element = createElement('div', {
    id: 'hidden-node-container',
    key: 'hidden-node-container',
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      alignItems: 'flex-start',
    },
    children: nodeElements,
  });
  elements.push(nodeContainerElement);

  const hiddenContainerContentElements: JSX.Element = createElement(Fragment, { children: elements });

  const element = (
    <ReactFlowProvider>
      <ApolloProvider client={new ApolloClient({ cache: new InMemoryCache(), uri: '' })}>
        <ThemeProvider theme={theme}>
          <ServerContext.Provider value={{ httpOrigin }}>
            <ToastContext.Provider value={{ enqueueSnackbar: (_body: string, _options?: MessageOptions) => {} }}>
              <DiagramDirectEditContextProvider>{hiddenContainerContentElements}</DiagramDirectEditContextProvider>
            </ToastContext.Provider>
          </ServerContext.Provider>
        </ThemeProvider>
      </ApolloProvider>
    </ReactFlowProvider>
  );

  ReactDOM.render(element, hiddenContainer, renderCallback);
  return hiddenContainer;
};

export const cleanLayoutArea = (container: HTMLDivElement) => {
  if (container?.parentNode) {
    container.parentNode.removeChild(container);
  }
};

const gap = 20;

export const layout = (
  previousDiagram: Diagram | null,
  diagram: Diagram,
  referencePosition: ReferencePosition | null
): Diagram => {
  layoutDiagram(previousDiagram, diagram, referencePosition);
  return diagram;
};

const layoutDiagram = (
  previousDiagram: Diagram | null,
  diagram: Diagram,
  referencePosition: ReferencePosition | null
) => {
  const allVisibleNodes = diagram.nodes.filter((node) => !node.hidden);
  const nodesToLayout = allVisibleNodes.filter((node) => !node.parentNode);

  let newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined = undefined;
  if (referencePosition) {
    newlyAddedNode = allVisibleNodes
      .filter((node) => !previousDiagram?.nodes.map((n) => n.id).find((n) => n === node.id))
      .find((node) => {
        if (node.parentNode) {
          return referencePosition.parentId === node.parentNode;
        }
        return !referencePosition.parentId || referencePosition.parentId === '';
      });
    if (newlyAddedNode) {
      newlyAddedNode = { ...newlyAddedNode, position: referencePosition.position };
    }
  }

  new LayoutEngine().layoutNodes(previousDiagram, allVisibleNodes, nodesToLayout, newlyAddedNode);

  // Update position of root nodes
  nodesToLayout.forEach((node, index) => {
    const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === node.id);
    const createdNode = newlyAddedNode?.id === node.id ? newlyAddedNode : undefined;

    if (!!createdNode) {
      node.position = createdNode.position;
    } else if (previousNode) {
      node.position = previousNode.position;
    } else {
      const maxBorderNodeWidthWest = getChildren(node, allVisibleNodes)
        .filter(isWestBorderNode)
        .map((borderNode) => borderNode.width || 0)
        .reduce((a, b) => Math.max(a, b), 0);

      node.position = { x: 0, y: 0 };
      const previousSibling = nodesToLayout[index - 1];
      if (previousSibling) {
        const previousSiblingMaxBorderNodeWidthEast = getChildren(previousSibling, allVisibleNodes)
          .filter(isEastBorderNode)
          .map((borderNode) => borderNode.width || 0)
          .reduce((a, b) => Math.max(a, b), 0);

        node.position = {
          x:
            previousSibling.position.x +
            maxBorderNodeWidthWest +
            previousSiblingMaxBorderNodeWidthEast +
            (previousSibling.width ?? 0) +
            gap,
          y: 0,
        };
      }
    }
  });
};

export const performDefaultAutoLayout = (
  nodes: Node<NodeData>[],
  edges: Edge[],
  zoomLevel: number
): Promise<{ nodes: Node<NodeData>[] }> => {
  const layoutOptions: LayoutOptions = {
    'elk.algorithm': 'layered',
    'elk.layered.spacing.nodeNodeBetweenLayers': '100',
    'org.eclipse.elk.hierarchyHandling': 'INCLUDE_CHILDREN',
    'layering.strategy': 'NETWORK_SIMPLEX',
    'elk.spacing.nodeNode': '80',
    'elk.direction': 'DOWN',
    'elk.layered.spacing.edgeNodeBetweenLayers': '30',
  };
  return performAutoLayout(nodes, edges, layoutOptions, zoomLevel);
};

export const performAutoLayout = (
  nodes: Node<NodeData>[],
  edges: Edge[],
  layoutOptions: LayoutOptions,
  zoomLevel: number
): Promise<{ nodes: Node<NodeData>[] }> => {
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
    if (isRectangularNode(node)) {
      const rectangularNodeData: RectangularNodeData = node.data;

      const label = document.querySelector<HTMLDivElement>(`[data-id="${rectangularNodeData.label?.id}"]`);
      if (label) {
        const elkLabel: ElkLabel = {
          width: label.getBoundingClientRect().width / zoomLevel,
          height: label.getBoundingClientRect().height / zoomLevel,
          text: rectangularNodeData.label?.text,
        };

        elkNode.labels?.push(elkLabel);
      }
    }
    if (isListNode(node)) {
      const listNodeData: ListNodeData = node.data;

      const label = document.querySelector<HTMLDivElement>(`[data-id="${listNodeData.label?.id}"]`);
      if (label) {
        const elkLabel: ElkLabel = {
          width: label.getBoundingClientRect().width / zoomLevel,
          height: label.getBoundingClientRect().height / zoomLevel,
          text: listNodeData.label?.text,
        };

        elkNode.labels?.push(elkLabel);
      }
    }
    if (isIconLabelNode(node)) {
      const iconLabelNodeData: IconLabelNodeData = node.data;
      const label = document.querySelector<HTMLDivElement>(`[data-id="${iconLabelNodeData.label?.id}"]`);
      if (label) {
        const elkLabel: ElkLabel = {
          width: label.getBoundingClientRect().width / zoomLevel,
          height: label.getBoundingClientRect().height / zoomLevel,
          text: iconLabelNodeData.label?.text,
        };

        elkNode.labels?.push(elkLabel);
      }
    }

    const element = document.querySelector(`[data-id="${node.id}"]`);
    if (element) {
      elkNode.width = element.getBoundingClientRect().width / zoomLevel;
      elkNode.height = element.getBoundingClientRect().height / zoomLevel;
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

/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { ThemeProvider } from '@mui/material/styles';
import { Edge, Node, ReactFlowProvider } from '@xyflow/react';
import { createElement, useEffect } from 'react';
import { Root, createRoot } from 'react-dom/client';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { GQLArrangeLayoutDirection } from '../../representation/DiagramRepresentation.types';
import { NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramDirectEditContextProvider } from '../direct-edit/DiagramDirectEditContext';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { isEdgeAnchorNode } from '../node/EdgeAnchorNode.types';
import { isHandleNode } from '../node/HandleNode.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { LayoutEngine } from './LayoutEngine';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousPosition } from './bounds';
import { getNodeBorderWidth } from './getNodeBorderWidth';
import { InsideLabelMeasurer, RawDiagram } from './layout.types';
import { isEastBorderNode, isWestBorderNode } from './layoutBorderNodes';
import { computeNewlyNodePosition, getChildren } from './layoutNode';
import { gap } from './layoutParams';

const isListNode = (node: Node<NodeData>): node is Node<ListNodeData> => node.type === 'listNode';

const getNodeBorderWidthForLabel = (node: Node<NodeData>, visibleNodes: Node<NodeData>[]): number => {
  if (node.parentId) {
    const parentNodeParent = visibleNodes.find((candidate) => candidate.id === node.parentId);
    if (parentNodeParent && parentNodeParent.type === 'listNode') {
      return getNodeBorderWidthForLabel(parentNodeParent, visibleNodes);
    }
  }
  return getNodeBorderWidth(node.data.style);
};

const getInsideLabelConstraintWidth = (node: Node<NodeData>, visibleNodes: Node<NodeData>[]): number => {
  const borderWidth = getNodeBorderWidthForLabel(node, visibleNodes);
  let width = node.width ?? 0;
  if (node.parentId) {
    const parentNode = visibleNodes.find((candidate) => candidate.id === node.parentId);
    if (parentNode && isListNode(parentNode) && parentNode.width) {
      width = parentNode.width;
    }
  }
  return Math.max(0, width - borderWidth * 2);
};

export const createInsideLabelMeasurer = (hiddenContainer: HTMLDivElement): InsideLabelMeasurer => {
  const labelElements = new Map(
    Array.from(hiddenContainer.querySelectorAll<HTMLElement>('[id]')).map((element) => [element.id, element])
  );
  return (node, contentWidth) => {
    const label = node.data.insideLabel;
    if (!label || label.overflowStrategy !== 'WRAP') {
      return null;
    }
    const element = labelElements.get(`${label.id}-label`);
    if (!element) {
      return null;
    }
    element.style.maxWidth = `${Math.max(0, contentWidth)}px`;
    const { width, height } = element.getBoundingClientRect();
    return { width, height };
  };
};

export const prepareLayoutArea = (
  diagram: RawDiagram,
  renderCallback: () => void,
  httpOrigin: string
): { hiddenContainer: HTMLDivElement; root: Root } => {
  const hiddenContainer: HTMLDivElement = document.createElement('div');
  hiddenContainer.id = 'hidden-container';
  hiddenContainer.style.display = 'inline-block';
  hiddenContainer.style.position = 'absolute';
  hiddenContainer.style.visibility = 'hidden';
  hiddenContainer.style.zIndex = '-1';
  document.body.appendChild(hiddenContainer);
  const visibleNodes = diagram.nodes.filter((node) => !node.hidden);

  // Render all label first
  const labelElements: React.JSX.Element[] = [];
  visibleNodes.forEach((node) => {
    if (hiddenContainer && node.data.insideLabel) {
      const children: React.JSX.Element[] = [
        createElement(Label, {
          diagramElementId: node.id,
          label: node.data.insideLabel,
          faded: false,
          key: node.data.insideLabel.id,
        }),
      ];
      const element: React.JSX.Element = createElement('div', {
        id: `${node.data.insideLabel.id}-label`,
        key: `${node.data.insideLabel.id}-label`,
        style: {
          maxWidth:
            node.data.insideLabel.overflowStrategy === 'NONE'
              ? undefined
              : getInsideLabelConstraintWidth(node, visibleNodes),
        },
        children,
      });
      labelElements.push(element);
    }
    if (hiddenContainer && node.data.outsideLabels.BOTTOM_MIDDLE) {
      const outsideLabel = node.data.outsideLabels.BOTTOM_MIDDLE;
      const children: React.JSX.Element[] = [
        createElement(Label, {
          diagramElementId: node.id,
          label: outsideLabel,
          faded: false,
          key: outsideLabel.id,
        }),
      ];
      const element: React.JSX.Element = createElement('div', {
        id: `${outsideLabel.id}-label`,
        key: `${outsideLabel.id}-label`,
        style: { maxWidth: outsideLabel.overflowStrategy === 'NONE' ? undefined : node.width },
        children,
      });
      labelElements.push(element);
    }
  });
  diagram.edges.forEach((edge: Edge<MultiLabelEdgeData>) => {
    if (hiddenContainer && edge.data?.label) {
      const children: React.JSX.Element[] = [
        createElement(Label, {
          diagramElementId: edge.id,
          label: edge.data.label,
          faded: false,
          key: edge.data.label.id,
        }),
      ];
      const element: React.JSX.Element = createElement('div', {
        id: `${edge.data.label.id}-label`,
        key: `${edge.data.label.id}-label`,
        children,
      });
      labelElements.push(element);
    }
    if (hiddenContainer && edge.data?.beginLabel) {
      const children: React.JSX.Element[] = [
        createElement(Label, {
          diagramElementId: edge.id,
          label: edge.data.beginLabel,
          faded: false,
          key: edge.data.beginLabel.id,
        }),
      ];
      const element: React.JSX.Element = createElement('div', {
        id: `${edge.data.beginLabel.id}-label`,
        key: `${edge.data.beginLabel.id}-label`,
        children,
      });
      labelElements.push(element);
    }
    if (hiddenContainer && edge.data?.endLabel) {
      const children: React.JSX.Element[] = [
        createElement(Label, {
          diagramElementId: edge.id,
          label: edge.data.endLabel,
          faded: false,
          key: edge.data.endLabel.id,
        }),
      ];
      const element: React.JSX.Element = createElement('div', {
        id: `${edge.data.endLabel.id}-label`,
        key: `${edge.data.endLabel.id}-label`,
        children,
      });
      labelElements.push(element);
    }
  });

  // The container used to render label is a flex container authorizing wrapping.
  // It wrap direct children, and thus, prevent label to wrap (because their container will have always enough place to render on the line, unless the label is larger than the screen).
  const labelContainerElement: React.JSX.Element = createElement('div', {
    id: 'hidden-label-container',
    key: 'hidden-label-container',
    style: {
      display: 'flex',
      flexWrap: 'wrap',
      alignItems: 'flex-start',
    },
    children: labelElements,
  });
  const Element = () => {
    useEffect(() => {
      renderCallback();
    }, []);
    return (
      <ReactFlowProvider>
        <ApolloProvider client={new ApolloClient({ cache: new InMemoryCache(), uri: '' })}>
          <ThemeProvider theme={theme}>
            <ServerContext.Provider value={{ httpOrigin }}>
              <ToastContext.Provider
                value={{
                  enqueueSnackbar: (_body: string, _options?: MessageOptions) => {},
                }}>
                <DiagramDirectEditContextProvider>{labelContainerElement}</DiagramDirectEditContextProvider>
              </ToastContext.Provider>
            </ServerContext.Provider>
          </ThemeProvider>
        </ApolloProvider>
      </ReactFlowProvider>
    );
  };

  const root = createRoot(hiddenContainer!);
  root.render(<Element />);

  return { hiddenContainer, root };
};

export const cleanLayoutArea = (container: HTMLDivElement, root: Root | null) => {
  if (container?.parentNode) {
    if (root) {
      setTimeout(() => root.unmount(), 100);
    }
    container.parentNode.removeChild(container);
  }
};

export const layout = (
  previousDiagram: RawDiagram | null,
  diagram: RawDiagram,
  referencePosition: GQLReferencePosition | null,
  layoutDirection: GQLArrangeLayoutDirection,
  nodeLayoutHandlerContributions: INodeLayoutHandler<NodeData>[],
  autoLayout: boolean,
  labelMeasurer: InsideLabelMeasurer
): RawDiagram => {
  layoutDiagram(
    previousDiagram,
    diagram,
    referencePosition,
    layoutDirection,
    nodeLayoutHandlerContributions,
    autoLayout,
    labelMeasurer
  );

  return diagram;
};

const layoutDiagram = (
  previousDiagram: RawDiagram | null,
  diagram: RawDiagram,
  referencePosition: GQLReferencePosition | null,
  layoutDirection: GQLArrangeLayoutDirection,
  nodeLayoutHandlerContributions: INodeLayoutHandler<NodeData>[],
  autoLayout: boolean,
  labelMeasurer: InsideLabelMeasurer
) => {
  const allVisibleNodes = diagram.nodes.filter(
    (node) => !node.hidden && !isEdgeAnchorNode(node) && !isHandleNode(node)
  );
  const nodesToLayout = allVisibleNodes.filter((node) => !node.parentId);

  const layoutEngine: ILayoutEngine = new LayoutEngine(labelMeasurer);

  nodeLayoutHandlerContributions.forEach((nodeLayoutHandler) =>
    layoutEngine.registerNodeLayoutHandlerContribution(nodeLayoutHandler)
  );

  let newlyAddedNodes: Node<NodeData, DiagramNodeType>[] = computeNewlyNodePosition(
    allVisibleNodes,
    previousDiagram?.nodes ?? [],
    referencePosition,
    layoutDirection
  );

  layoutEngine.layoutNodes(previousDiagram, allVisibleNodes, nodesToLayout, newlyAddedNodes);

  // With autoLayout, positions are already computed
  if (!autoLayout) {
    // Update position of root nodes
    nodesToLayout.forEach((node, index) => {
      const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === node.id);
      const previousPosition = computePreviousPosition(previousNode, node);

      const createdNode = newlyAddedNodes.find((n) => n.id === node.id);

      if (!!createdNode) {
        node.position = createdNode.position;
      } else if (previousPosition) {
        node.position = previousPosition;
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
  }
};

export const prepareLayoutLabels = (previousDiagram: RawDiagram | null, diagram: RawDiagram): void => {
  layoutNodeLabels(previousDiagram, diagram.nodes);
  layoutEdgeLabels(previousDiagram, diagram.edges);
};

export const prepareListNodeLayout = (
  previousDiagram: RawDiagram | null,
  diagram: RawDiagram,
  labelMeasurer: InsideLabelMeasurer
): void => {
  const layoutEngine: ILayoutEngine = new LayoutEngine(labelMeasurer);

  const visibleNodes: Node<NodeData, DiagramNodeType>[] = diagram.nodes.filter((node) => !node.hidden);
  const listNodes: Node<ListNodeData, DiagramNodeType>[] = visibleNodes.filter(isListNode);
  const listNodeIds = new Set(listNodes.map((node) => node.id));
  const listRoots = listNodes.filter((node) => !node.parentId || !listNodeIds.has(node.parentId));

  layoutEngine.layoutNodes(previousDiagram, visibleNodes, listRoots, []);
};

const layoutNodeLabels = (previousDiagram: RawDiagram | null, nodes: Node<NodeData>[]) => {
  nodes.forEach((node) => {
    const previousNode = (previousDiagram?.nodes ?? []).find((prevNode) => prevNode.id === node.id);
    if (node.data.outsideLabels.BOTTOM_MIDDLE) {
      const outsideLabel = node.data.outsideLabels.BOTTOM_MIDDLE;
      const labelElement = document.getElementById(`${outsideLabel.id}-label`);
      const labelHeight = labelElement?.getBoundingClientRect().height ?? 0;
      const labelWidth = labelElement?.getBoundingClientRect().width ?? 0;
      if (!outsideLabel.resizedByUser) {
        outsideLabel.width = labelWidth;
        outsideLabel.height = labelHeight;
      } else if (previousNode) {
        outsideLabel.width = previousNode.data.outsideLabels.BOTTOM_MIDDLE?.width ?? labelWidth;
        outsideLabel.height = previousNode.data.outsideLabels.BOTTOM_MIDDLE?.height ?? labelHeight;
      }
    }
    if (node.data.insideLabel) {
      const insideLabel = node.data.insideLabel;
      const labelElement = document.getElementById(`${insideLabel.id}-label`);
      insideLabel.width = labelElement?.getBoundingClientRect().width ?? 0;
      insideLabel.height = labelElement?.getBoundingClientRect().height ?? 0;
    }
  });
};

const layoutEdgeLabels = (previousDiagram: RawDiagram | null, edges: Edge<MultiLabelEdgeData>[]) => {
  edges.forEach((edge) => {
    const previousEdge: Edge<MultiLabelEdgeData> | undefined = (previousDiagram?.edges ?? []).find(
      (prevEdge) => prevEdge.id === edge.id
    );
    if (edge.data?.beginLabel) {
      const labelElement = document.getElementById(`${edge.data.beginLabel.id}-label`);
      if (labelElement) {
        const labelHeight = labelElement.getBoundingClientRect().height;
        const labelWidth = labelElement.getBoundingClientRect().width;
        if (!edge.data.beginLabel.resizedByUser) {
          edge.data.beginLabel.width = labelWidth;
          edge.data.beginLabel.height = labelHeight;
        } else if (previousEdge) {
          edge.data.beginLabel.width = previousEdge.data?.beginLabel?.width ?? labelWidth;
          edge.data.beginLabel.height = previousEdge.data?.beginLabel?.height ?? labelHeight;
        }
      }
    }
    if (edge.data?.label) {
      const labelElement = document.getElementById(`${edge.data.label.id}-label`);
      if (labelElement) {
        const labelHeight = labelElement.getBoundingClientRect().height;
        const labelWidth = labelElement.getBoundingClientRect().width;
        if (!edge.data.label.resizedByUser) {
          edge.data.label.width = labelWidth;
          edge.data.label.height = labelHeight;
        } else if (previousEdge) {
          edge.data.label.width = previousEdge.data?.label?.width ?? labelWidth;
          edge.data.label.height = previousEdge.data?.label?.height ?? labelHeight;
        }
      }
    }
    if (edge.data?.endLabel) {
      const labelElement = document.getElementById(`${edge.data.endLabel.id}-label`);
      if (labelElement) {
        const labelHeight = labelElement.getBoundingClientRect().height;
        const labelWidth = labelElement.getBoundingClientRect().width;
        if (!edge.data.endLabel.resizedByUser) {
          edge.data.endLabel.width = labelWidth;
          edge.data.endLabel.height = labelHeight;
        } else if (previousEdge) {
          edge.data.endLabel.width = previousEdge.data?.endLabel?.width ?? labelWidth;
          edge.data.endLabel.height = previousEdge.data?.endLabel?.height ?? labelHeight;
        }
      }
    }
  });
};

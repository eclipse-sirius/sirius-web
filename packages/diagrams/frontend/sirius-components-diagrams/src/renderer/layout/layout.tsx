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

import { ApolloClient, InMemoryCache } from '@apollo/client/core';
import { ApolloProvider } from '@apollo/client/react';
import { MessageOptions, ServerContext, ToastContext, theme } from '@eclipse-sirius/sirius-components-core';
import { ThemeProvider } from '@material-ui/core/styles';
import { Fragment, createElement } from 'react';
import ReactDOM from 'react-dom';
import { Node, ReactFlowProvider } from 'reactflow';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramDirectEditContextProvider } from '../direct-edit/DiagramDirectEditContext';
import { FreeFormNode } from '../node/FreeFormNode';
import { FreeFormNodeData } from '../node/FreeFormNode.types';
import { ListNode } from '../node/ListNode';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { LayoutEngine } from './LayoutEngine';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousPosition } from './bounds';
import { RawDiagram } from './layout.types';
import { isEastBorderNode, isWestBorderNode, getNewlyAddedBorderNodePosition } from './layoutBorderNodes';
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

const isListNode = (node: Node<NodeData>): node is Node<ListNodeData> => node.type === 'listNode';
const isRectangularNode = (node: Node<NodeData>): node is Node<FreeFormNodeData> => node.type === 'rectangularNode';

export const prepareLayoutArea = (
  diagram: RawDiagram,
  renderCallback: () => void,
  httpOrigin: string
): HTMLDivElement => {
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
    if (hiddenContainer && node.data.insideLabel) {
      const children: JSX.Element[] = [
        createElement(Label, {
          diagramElementId: node.id,
          label: node.data.insideLabel,
          faded: false,
          transform: '',
          key: node.data.insideLabel.id,
        }),
      ];
      const element: JSX.Element = createElement('div', {
        id: `${node.id}-label-${index}`,
        key: `${node.id}-label-${index}`,
        role: 'button', // role applied by react flow
        style: {
          maxWidth: node.data.insideLabel?.overflowStrategy === 'NONE' ? undefined : node.width,
          borderWidth: node.data.style.borderWidth,
          borderStyle: node.data.style.borderStyle,
        },
        children,
      });
      labelElements.push(element);
    }
    if (hiddenContainer && node.data.outsideLabels.BOTTOM_MIDDLE) {
      const outsideLabel = node.data.outsideLabels.BOTTOM_MIDDLE;
      const children: JSX.Element[] = [
        createElement(Label, {
          diagramElementId: node.id,
          label: outsideLabel,
          faded: false,
          transform: '',
          key: outsideLabel.id,
        }),
      ];
      const element: JSX.Element = createElement('div', {
        id: `${outsideLabel.id}-label`,
        key: `${outsideLabel.id}-label`,
        role: 'button', // role applied by react flow
        style: { maxWidth: node.width },
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
        const element = createElement(FreeFormNode, {
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
            <ToastContext.Provider
              value={{
                enqueueSnackbar: (_body: string, _options?: MessageOptions) => {},
              }}>
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
  previousDiagram: RawDiagram | null,
  diagram: RawDiagram,
  referencePosition: GQLReferencePosition | null,
  nodeLayoutHandlerContributions: INodeLayoutHandler<NodeData>[]
): RawDiagram => {
  layoutDiagram(previousDiagram, diagram, referencePosition, nodeLayoutHandlerContributions);
  return diagram;
};

const layoutDiagram = (
  previousDiagram: RawDiagram | null,
  diagram: RawDiagram,
  referencePosition: GQLReferencePosition | null,
  nodeLayoutHandlerContributions: INodeLayoutHandler<NodeData>[]
) => {
  const allVisibleNodes = diagram.nodes.filter((node) => !node.hidden);
  const nodesToLayout = allVisibleNodes.filter((node) => !node.parentNode);

  const layoutEngine: ILayoutEngine = new LayoutEngine();

  nodeLayoutHandlerContributions.forEach((nodeLayoutHandler) =>
    layoutEngine.registerNodeLayoutHandlerContribution(nodeLayoutHandler)
  );

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
      if (newlyAddedNode.data.isBorderNode) {
        getNewlyAddedBorderNodePosition(
          newlyAddedNode,
          allVisibleNodes.find((node) => node.id === newlyAddedNode?.parentNode),
          referencePosition
        );
      }
    }
  }

  layoutEngine.layoutNodes(previousDiagram, allVisibleNodes, nodesToLayout, newlyAddedNode);

  // Update position of root nodes
  nodesToLayout.forEach((node, index) => {
    const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === node.id);
    const previousPosition = computePreviousPosition(previousNode, node);

    const createdNode = newlyAddedNode?.id === node.id ? newlyAddedNode : undefined;

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
};

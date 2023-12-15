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
import { Fragment, createElement } from 'react';
import ReactDOM from 'react-dom';
import { Node, ReactFlowProvider } from 'reactflow';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramDirectEditContextProvider } from '../direct-edit/DiagramDirectEditContext';
import { DefaultNode } from '../node/DefaultNode';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { LayoutEngine } from './LayoutEngine';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousPosition } from './bounds';
import { RawDiagram } from './layout.types';
import { isEastBorderNode, isWestBorderNode } from './layoutBorderNodes';
import { layoutHandles } from './layoutHandles';
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

const isListNode = (node: Node<NodeData>): node is Node<NodeData> => node.type === 'listNode';
const isRectangularNode = (node: Node<NodeData>): node is Node<NodeData> => node.type === 'rectangularNode';

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
  /**
   * WARN: The height label computed in the hidden-container will be slightly different from the label rendered in the reactflow diagram.
   * This difference is due to the line-height which is set in `variables.css` to 1.5 globaly but in `reset.css` the line-height is
   * overridden to `normal` for element with [role=button] which seem to be the case for all reactflow nodes.
   * That should not impact the node size since it is calculated with the higher value of line-height.
   */
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
        children,
      });
      labelElements.push(element);
    }
    if (hiddenContainer && Object.keys(node.data.outsideLabels).find((key) => key.startsWith('BOTTOM_'))) {
      const outsideLabels = node.data.outsideLabels;
      if (outsideLabels.BOTTOM_BEGIN) {
        const children: JSX.Element[] = [
          createElement(Label, {
            diagramElementId: node.id,
            label: outsideLabels.BOTTOM_BEGIN,
            faded: false,
            transform: '',
            key: outsideLabels.BOTTOM_BEGIN.id,
          }),
        ];
        const element: JSX.Element = createElement('div', {
          id: `${node.id}-outside-label-BOTTOM_BEGIN-${index}`,
          key: `${node.id}-outside-label-BOTTOM_BEGIN-${index}`,
          children,
        });
        labelElements.push(element);
      }
      if (outsideLabels.BOTTOM_MIDDLE) {
        const children: JSX.Element[] = [
          createElement(Label, {
            diagramElementId: node.id,
            label: outsideLabels.BOTTOM_MIDDLE,
            faded: false,
            transform: '',
            key: outsideLabels.BOTTOM_MIDDLE.id,
          }),
        ];
        const element: JSX.Element = createElement('div', {
          id: `${node.id}-outside-label-BOTTOM_MIDDLE-${index}`,
          key: `${node.id}-outside-label-BOTTOM_MIDDLE-${index}`,
          children,
        });
        labelElements.push(element);
      }
      if (outsideLabels.BOTTOM_END) {
        const children: JSX.Element[] = [
          createElement(Label, {
            diagramElementId: node.id,
            label: outsideLabels.BOTTOM_END,
            faded: false,
            transform: '',
            key: outsideLabels.BOTTOM_END.id,
          }),
        ];
        const element: JSX.Element = createElement('div', {
          id: `${node.id}-outside-label-BOTTOM_END-${index}`,
          key: `${node.id}-outside-label-BOTTOM_END-${index}`,
          children,
        });
        labelElements.push(element);
      }
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
        const element = createElement(DefaultNode, {
          ...emptyRectangularNodeProps,
          id: node.id,
          data: node.data,
          key: `${node.id}-${index}`,
        });
        children.push(element);
      }
      if (isListNode(node)) {
        const element = createElement(DefaultNode, {
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

  layoutHandles(diagram);
};

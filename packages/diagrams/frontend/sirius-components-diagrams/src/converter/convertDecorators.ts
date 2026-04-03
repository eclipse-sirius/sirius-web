/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { GQLNodeDecorator, GQLNodeDecoratorPosition } from '../graphql/subscription/nodeFragment.types';
import { NodeDecorator, NodeDecoratorPosition } from '../renderer/DiagramRenderer.types';

export const convertDecorators = (decorators: GQLNodeDecorator[]): Map<NodeDecoratorPosition, NodeDecorator[]> => {
  return decorators
    .map((decorator) => convertDecorator(decorator))
    .reduce((acc, nodeDecorator) => {
      const existingDecorators = acc.get(nodeDecorator.position) ?? [];
      existingDecorators.push(nodeDecorator);
      acc.set(nodeDecorator.position, existingDecorators);
      return acc;
    }, new Map());
};

export const convertDecoratorPosition = (position: GQLNodeDecoratorPosition): NodeDecoratorPosition => {
  let decoratorPosition: NodeDecoratorPosition;
  switch (position) {
    case 'NORTH':
      decoratorPosition = NodeDecoratorPosition.NORTH;
      break;
    case 'NORTH_EAST':
      decoratorPosition = NodeDecoratorPosition.NORTH_EAST;
      break;
    case 'EAST':
      decoratorPosition = NodeDecoratorPosition.EAST;
      break;
    case 'SOUTH_EAST':
      decoratorPosition = NodeDecoratorPosition.SOUTH_EAST;
      break;
    case 'SOUTH':
      decoratorPosition = NodeDecoratorPosition.SOUTH;
      break;
    case 'SOUTH_WEST':
      decoratorPosition = NodeDecoratorPosition.SOUTH_WEST;
      break;
    case 'WEST':
      decoratorPosition = NodeDecoratorPosition.WEST;
      break;
    case 'NORTH_WEST':
      decoratorPosition = NodeDecoratorPosition.NORTH_WEST;
      break;
    case 'CENTER':
      decoratorPosition = NodeDecoratorPosition.CENTER;
      break;
  }
  return decoratorPosition;
};

export const convertDecorator = (decorator: GQLNodeDecorator): NodeDecorator => {
  const nodeDecorator: NodeDecorator = {
    label: decorator.label,
    iconURL: decorator.iconURL,
    position: convertDecoratorPosition(decorator.position),
  };
  return nodeDecorator;
};

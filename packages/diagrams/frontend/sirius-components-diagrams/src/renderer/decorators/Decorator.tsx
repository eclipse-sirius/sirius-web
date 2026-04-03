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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import Tooltip from '@mui/material/Tooltip';
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { NodeDecorator, NodeDecoratorPosition } from '../DiagramRenderer.types';
import { DecoratorProps } from './Decorator.types';

const useStyles = makeStyles()((_) => ({
  northDecorator: {
    gridArea: '1 / 2',
    justifySelf: 'center',
    alignSelf: 'start',
  },
  northEastDecorator: {
    gridArea: '1 / 3',
    justifySelf: 'end',
    alignSelf: 'start',
  },
  eastDecorator: {
    gridArea: '2 / 3',
    justifySelf: 'end',
    alignSelf: 'center',
  },
  southEastDecorator: {
    gridArea: '3 / 3',
    justifySelf: 'end',
    alignSelf: 'end',
  },
  southDecorator: {
    gridArea: '3 / 2',
    justifySelf: 'center',
    alignSelf: 'end',
  },
  southWestDecorator: {
    gridArea: '3 / 1',
    justifySelf: 'start',
    alignSelf: 'end',
  },
  westDecorator: {
    gridArea: '2 / 1',
    justifySelf: 'start',
    alignSelf: 'center',
  },
  northWestDecorator: {
    gridArea: '1 / 1',
    justifySelf: 'start',
    alignSelf: 'start',
  },
  centerDecorator: {
    gridArea: '2 / 2',
    justifySelf: 'center',
    alignSelf: 'center',
  },
}));

export const Decorator = ({ position, decorators }: DecoratorProps) => {
  const { classes } = useStyles();

  let cssClass: string;
  switch (position) {
    case NodeDecoratorPosition.NORTH:
      cssClass = classes.northDecorator;
      break;
    case NodeDecoratorPosition.NORTH_EAST:
      cssClass = classes.northEastDecorator;
      break;
    case NodeDecoratorPosition.EAST:
      cssClass = classes.eastDecorator;
      break;
    case NodeDecoratorPosition.SOUTH_EAST:
      cssClass = classes.southEastDecorator;
      break;
    case NodeDecoratorPosition.SOUTH:
      cssClass = classes.southDecorator;
      break;
    case NodeDecoratorPosition.SOUTH_WEST:
      cssClass = classes.southWestDecorator;
      break;
    case NodeDecoratorPosition.WEST:
      cssClass = classes.westDecorator;
      break;
    case NodeDecoratorPosition.NORTH_WEST:
      cssClass = classes.northWestDecorator;
      break;
    case NodeDecoratorPosition.CENTER:
      cssClass = classes.centerDecorator;
      break;
  }

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const firstDecorator: NodeDecorator | undefined = decorators.at(0);
  if (firstDecorator) {
    return (
      <div className={cssClass}>
        <Tooltip title={firstDecorator.label || ''}>
          <img alt={firstDecorator.label} src={httpOrigin + firstDecorator.iconURL} />
        </Tooltip>
      </div>
    );
  } else {
    return null;
  }
};

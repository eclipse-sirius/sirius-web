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
import { makeStyles } from 'tss-react/mui';
import { GQLNodeDecorator, GQLNodeDecoratorPosition } from '../../graphql/subscription/nodeFragment.types';
import { Decorator } from './Decorator';
import { DecoratorContainerProps } from './DecoratorContainer.types';

const useStyles = makeStyles()((_) => ({
  decoratorContainer: {
    position: 'absolute',
    top: 0,
    left: 0,
    display: 'grid',
    gridTemplateAreas: `
                        'NW N NE'
                        'W C E'
                        'SW S SE'
                       `,
    gridTemplateRows: '1fr 1fr 1fr',
    gridTemplateColumns: '1fr 1fr 1fr',
    height: '100%',
    width: '100%',
    overflow: 'hidden',
  },
}));

export const decoratorListToMap = (
  decorators: GQLNodeDecorator[]
): Map<GQLNodeDecoratorPosition, GQLNodeDecorator[]> => {
  return decorators.reduce((accumulator, nodeDecorator) => {
    const existingDecorators = accumulator.get(nodeDecorator.position) ?? [];
    existingDecorators.push(nodeDecorator);
    accumulator.set(nodeDecorator.position, existingDecorators);
    return accumulator;
  }, new Map());
};

export const DecoratorContainer = ({ decorators }: DecoratorContainerProps) => {
  const { classes } = useStyles();

  const decoratorMap = decoratorListToMap(decorators);

  const renderedDecorators: JSX.Element[] = [];

  for (let entry of decoratorMap.entries()) {
    const position = entry[0];
    const decoratorsAtPosition = entry[1];
    const renderedDecorator = (
      <Decorator key={position.toLowerCase()} position={position} decorators={decoratorsAtPosition} />
    );
    if (renderedDecorator) {
      renderedDecorators.push(renderedDecorator);
    }
  }

  return <div className={classes.decoratorContainer}>{renderedDecorators}</div>;
};

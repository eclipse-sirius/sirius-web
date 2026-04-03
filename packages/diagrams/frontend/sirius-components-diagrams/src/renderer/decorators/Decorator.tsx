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
import Typography from '@mui/material/Typography';
import React, { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { GQLNodeDecorator } from '../../graphql/subscription/nodeFragment.types';
import { DecoratorProps } from './Decorator.types';

const useStyles = makeStyles()((theme) => ({
  north: {
    gridArea: 'N',
    justifySelf: 'center',
    alignSelf: 'start',
  },
  north_east: {
    gridArea: 'NE',
    justifySelf: 'end',
    alignSelf: 'start',
  },
  east: {
    gridArea: 'E',
    justifySelf: 'end',
    alignSelf: 'center',
  },
  south_east: {
    gridArea: 'SE',
    justifySelf: 'end',
    alignSelf: 'end',
  },
  south: {
    gridArea: 'S',
    justifySelf: 'center',
    alignSelf: 'end',
  },
  south_west: {
    gridArea: 'SW',
    justifySelf: 'start',
    alignSelf: 'end',
  },
  west: {
    gridArea: 'W',
    justifySelf: 'start',
    alignSelf: 'center',
  },
  north_west: {
    gridArea: 'NW',
    justifySelf: 'start',
    alignSelf: 'start',
  },
  center: {
    gridArea: 'C',
    justifySelf: 'center',
    alignSelf: 'center',
  },
  tooltip: {
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(2),
  },
}));

export const Decorator = ({ position, decorators }: DecoratorProps) => {
  const { classes } = useStyles();

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const firstDecorator: GQLNodeDecorator | undefined = decorators.at(0);

  if (firstDecorator) {
    const tooltipContent = decorators.map((decorator) => (
      <div key={decorator.id} className={classes.tooltip}>
        <img width={16} height={16} alt={decorator.label} src={httpOrigin + decorator.iconURL} />
        <Typography style={{ maxWidth: '100%' }}>{decorator.label}</Typography>
      </div>
    ));

    return (
      <div className={classes[position.toLowerCase()]}>
        <Tooltip title={<React.Fragment>{tooltipContent}</React.Fragment>}>
          <img alt={firstDecorator.label} src={httpOrigin + firstDecorator.iconURL} />
        </Tooltip>
      </div>
    );
  } else {
    return null;
  }
};

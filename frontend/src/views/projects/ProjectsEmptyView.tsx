/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { makeStyles, Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import React from 'react';
import { View } from 'views/View';

const useStyles = makeStyles((theme) => ({
  projectsEmptyView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
    justifyItems: 'center',
    alignItems: 'center',
  },

  emptyContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'repeat(3, min-content)',
    rowGap: theme.spacing(2),
    justifyItems: 'center',
    alignItems: 'center',
  },

  // title: {
  //   fontSize: var(--font-size-1),
  //   font-weight: var(--font-weight-bold),
  //   color: var(--daintree),
  // }

  // subtitle: {
  //   font-size: var(--font-size-5),
  //   color: var(--daintree-lighten-30),
  // }

  actions: {
    display: 'grid',
    gridTemplateColumns: 'repeat(2, 1fr)',
    columnGap: theme.spacing(2),
    marginTop: theme.spacing(3),
  },
}));

export const ProjectsEmptyView = () => {
  const classes = useStyles();
  return (
    <View>
      <div className={classes.projectsEmptyView}>
        <div className={classes.emptyContainer}>
          <Typography variant="h4">There are no projects yet</Typography>
          <Typography variant="subtitle1">Start creating your first project and it will appear here</Typography>
          <div className={classes.actions}>
            <Button data-testid="create" color="primary" href="/new/project" variant="contained">
              New
            </Button>
            <Button data-testid="upload" color="secondary" href="/upload/project" variant="outlined">
              Upload
            </Button>
          </div>
        </div>
      </div>
    </View>
  );
};

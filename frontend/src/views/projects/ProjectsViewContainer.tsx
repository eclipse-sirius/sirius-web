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
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  children: PropTypes.node.isRequired,
};

const useStyles = makeStyles((theme) => ({
  projectsViewContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr',
    rowGap: theme.spacing(4),
  },

  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: theme.spacing(5),
  },

  actions: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: 'repeat(2, min-content)',
    columnGap: theme.spacing(2),
  },

  content: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  },
}));

export const ProjectsViewContainer = ({ children }) => {
  const classes = useStyles();
  return (
    <div className={classes.projectsViewContainer}>
      <div className={classes.header}>
        <Typography variant="h5">Projects</Typography>
        <div className={classes.actions}>
          <Button data-testid="create" color="primary" href="/new/project" variant="contained">
            New
          </Button>
          <Button data-testid="upload" color="secondary" href="/upload/project" variant="outlined">
            Upload
          </Button>
        </div>
      </div>
      <Paper className={classes.content}>{children}</Paper>
    </div>
  );
};
ProjectsViewContainer.propTypes = propTypes;

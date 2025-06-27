/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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

import Typography from '@mui/material/Typography';
import React from 'react';
import { makeStyles } from 'tss-react/mui';
import { WorkbenchPartProps } from './WorkbenchPart.types';

const useSiteStyles = makeStyles()((theme) => ({
  view: {
    display: 'flex',
    flexDirection: 'column',
    minWidth: 0,
    overflow: 'auto',
  },
  viewHeader: {
    display: 'flex',
    flexDirection: 'row',
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  viewHeaderIcon: {
    margin: theme.spacing(1),
  },
  viewHeaderTitle: {
    marginTop: theme.spacing(1),
    marginRight: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
  viewContent: {
    flexGrow: 1,
    overflow: 'auto',
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      flexGrow: 1,
    },
  },
}));

export const WorkbenchPart = ({ editingContextId, readOnly, side, contribution }: WorkbenchPartProps) => {
  const { classes } = useSiteStyles();

  const { title, icon, component: Component } = contribution;
  return (
    <div className={classes.view} data-testid={`site-${side}`}>
      <div className={classes.viewHeader}>
        {React.cloneElement(icon, { className: classes.viewHeaderIcon })}
        <Typography className={classes.viewHeaderTitle}>{title}</Typography>
      </div>
      <div className={classes.viewContent} data-testid={`view-${title}`}>
        <Component editingContextId={editingContextId} readOnly={readOnly} />
      </div>
    </div>
  );
};

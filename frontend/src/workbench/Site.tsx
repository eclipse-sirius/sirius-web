/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import React, { useState } from 'react';
import { SiteProps } from './Site.types';

const useSiteStyles = makeStyles((theme) => ({
  leftSite: {
    display: 'grid',
    gridTemplateColumns: '32px 1fr',
    justifyItems: 'stretch',
    alignItems: 'stretch',
  },
  rightSite: {
    display: 'grid',
    gridTemplateColumns: '1fr 32px',
    justifyItems: 'stretch',
    alignItems: 'stretch',
  },
  viewSelector: {
    display: 'flex',
    flexDirection: 'column',
    background: 'var(--blue-lagoon-lighten-60)',
  },
  viewIcon: {
    padding: theme.spacing(1),
  },
  view: {
    display: 'grid',
    gridTemplateRows: 'min-content 1fr',
    justifyItems: 'stretch',
    alignItems: 'stretch',
    overflow: 'auto',
  },
  viewHeader: {
    display: 'flex',
    flexDirection: 'row',
    padding: theme.spacing(1),
    columnGap: theme.spacing(1),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  viewContent: {},
}));

export const Site = ({ editingContextId, selection, setSelection, readOnly, side, contributions }: SiteProps) => {
  const classes = useSiteStyles();
  const [selectedViewIndex, setSelectedViewIndex] = useState<number | null>(0);

  const viewSelector = (
    <div className={classes.viewSelector}>
      {contributions.map((contribution, index) => {
        const title = contribution.props.title;
        const icon = contribution.props.icon;
        return (
          <Tooltip enterDelay={250} title={title} key={index} className={classes.viewIcon}>
            <IconButton
              color={index === selectedViewIndex ? 'primary' : 'secondary'}
              aria-label={title}
              data-testid={`viewselector-${title}`}
              onClick={() => setSelectedViewIndex(index)}
            >
              {icon}
            </IconButton>
          </Tooltip>
        );
      })}
    </div>
  );

  let selectedView = undefined;
  if (selectedViewIndex !== null && selectedViewIndex < contributions.length) {
    const { title, icon, component: Component } = contributions[selectedViewIndex].props;
    selectedView = (
      <div className={classes.view}>
        <div className={classes.viewHeader}>
          {icon}
          <Typography>{title}</Typography>
        </div>
        <div className={classes.viewContent} data-testid={`view-${title}`}>
          <Component
            editingContextId={editingContextId}
            selection={selection}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        </div>
      </div>
    );
  }

  let content = undefined;
  if (side === 'left') {
    content = (
      <>
        {viewSelector}
        {selectedView}
      </>
    );
  } else {
    content = (
      <>
        {selectedView}
        {viewSelector}
      </>
    );
  }
  let classSite = side === 'left' ? classes.leftSite : classes.rightSite;
  return (
    <div className={classSite} data-testid={`site-${side}`}>
      {content}
    </div>
  );
};

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
    display: 'flex',
    flexDirection: 'row',
  },
  rightSite: {
    display: 'flex',
    flexDirection: 'row-reverse',
  },
  viewSelectorLeft: {
    display: 'flex',
    flexDirection: 'column',
    background: theme.palette.navigation.leftBackground,
  },
  viewSelectorRight: {
    display: 'flex',
    flexDirection: 'column',
    background: theme.palette.navigation.rightBackground,
  },
  viewSelectorIcon: {
    color: theme.palette.text.disabled,
  },
  viewSelectorIconSelectedLeft: {
    color: theme.palette.primary.main,
    borderLeft: 'solid 2px',
    borderRadius: 0,
  },
  viewSelectorIconSelectedRight: {
    color: theme.palette.primary.main,
    borderRight: 'solid 2px',
    borderRadius: 0,
  },
  viewIcon: {
    padding: theme.spacing(1),
  },
  view: {
    flexGrow: 1,
    display: 'flex',
    flexDirection: 'column',
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
  viewContent: {
    flexGrow: 1,
    overflow: 'auto',
  },
}));

export const Site = ({ editingContextId, selection, setSelection, readOnly, side, contributions }: SiteProps) => {
  const classes = useSiteStyles();
  const [selectedViewIndex, setSelectedViewIndex] = useState<number>(0);

  const viewSelector = (
    <div className={side === 'left' ? classes.viewSelectorLeft : classes.viewSelectorRight}>
      {contributions.map((contribution, index) => {
        const title = contribution.props.title;
        const icon = contribution.props.icon;
        let iconClassName = classes.viewSelectorIcon;
        if (index === selectedViewIndex) {
          iconClassName =
            side === 'left' ? classes.viewSelectorIconSelectedLeft : classes.viewSelectorIconSelectedRight;
        }
        return (
          <Tooltip enterDelay={250} title={title} key={index} className={classes.viewIcon}>
            <IconButton
              className={iconClassName}
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
  if (selectedViewIndex < contributions.length) {
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

  let classSite = side === 'left' ? classes.leftSite : classes.rightSite;
  return (
    <div className={classSite} data-testid={`site-${side}`}>
      {viewSelector}
      {selectedView}
    </div>
  );
};

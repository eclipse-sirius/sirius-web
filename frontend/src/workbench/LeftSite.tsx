/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import { makeStyles } from '@material-ui/core/styles';
import Tab from '@material-ui/core/Tab';
import Tabs from '@material-ui/core/Tabs';
import { ExplorerWebSocketContainer } from 'explorer/ExplorerWebSocketContainer';
import React from 'react';
import { LeftSiteProps } from './LeftSite.types';

const useSiteStyles = makeStyles((theme) => ({
  site: {
    display: 'flex',
    flexDirection: 'column',
    '& > *:nth-child(2)': {
      flexGrow: 1,
    },
  },
  tabsRoot: {
    minHeight: theme.spacing(4),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  tabRoot: {
    minHeight: theme.spacing(4),
    textTransform: 'none',
  },
  tabLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *:nth-child(2)': {
      marginLeft: theme.spacing(1),
    },
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const LeftSite = ({ editingContextId, setSelection, selection, readOnly }: LeftSiteProps) => {
  const classes = useSiteStyles();

  return (
    <div className={classes.site}>
      <Tabs
        classes={{ root: classes.tabsRoot }}
        textColor="primary"
        indicatorColor="primary"
        variant="fullWidth"
        value={0}>
        <Tab
          {...a11yProps('explorer')}
          classes={{ root: classes.tabRoot }}
          data-testid="explorer"
          label={<div className={classes.tabLabel}>Explorer</div>}
        />
      </Tabs>
      <ExplorerWebSocketContainer
        editingContextId={editingContextId}
        setSelection={setSelection}
        selection={selection}
        readOnly={readOnly}
      />
    </div>
  );
};

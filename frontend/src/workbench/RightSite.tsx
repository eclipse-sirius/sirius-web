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
import { PropertiesWebSocketContainer } from 'properties/PropertiesWebSocketContainer';
import React, { useState } from 'react';
import { ValidationWebSocketContainer } from 'validation/ValidationWebSocketContainer';
import { RightSiteProps, TabPanelProps } from './RightSite.types';

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

const TabPanel = ({ children, value, index }: TabPanelProps) => {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}>
      {value === index && <div>{children}</div>}
    </div>
  );
};

export const RightSite = ({ editingContextId, selection, readOnly }: RightSiteProps) => {
  const classes = useSiteStyles();
  const [value, setValue] = useState(0);

  return (
    <div className={classes.site}>
      <Tabs
        classes={{ root: classes.tabsRoot }}
        textColor="primary"
        indicatorColor="primary"
        value={value}
        onChange={(_, newValue: number) => setValue(newValue)}
        variant="fullWidth">
        <Tab
          {...a11yProps('properties')}
          classes={{ root: classes.tabRoot }}
          data-testid="properties"
          label={<div className={classes.tabLabel}>Properties</div>}
        />
        <Tab
          {...a11yProps('validation')}
          classes={{ root: classes.tabRoot }}
          data-testid="validation"
          label={<div className={classes.tabLabel}>Validation</div>}
        />
      </Tabs>
      <TabPanel value={value} index={0}>
        <PropertiesWebSocketContainer editingContextId={editingContextId} selection={selection} readOnly={readOnly} />
      </TabPanel>
      <TabPanel value={value} index={1}>
        <ValidationWebSocketContainer editingContextId={editingContextId} />
      </TabPanel>
    </div>
  );
};

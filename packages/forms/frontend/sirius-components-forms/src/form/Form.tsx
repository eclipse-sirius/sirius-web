/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import React, { useEffect, useState } from 'react';
import { Page } from '../pages/Page';
import { FormProps, FormState } from './Form.types';

const useFormStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
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
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const Form = ({ editingContextId, form, widgetSubscriptions, setSelection, readOnly }: FormProps) => {
  const classes = useFormStyles();
  const { id, pages } = form;

  const [state, setState] = useState<FormState>({ selectedPage: pages[0], pages });

  useEffect(() => {
    setState(() => {
      const selectedPage = pages.find((page) => page.id === state.selectedPage.id);
      if (selectedPage) {
        return { selectedPage, pages };
      }
      return { selectedPage: pages[0], pages };
    });
  }, [pages, state.selectedPage.id]);

  const onChangeTab = (_: React.ChangeEvent<{}>, value: string) => {
    const selectedPage = pages.find((page) => page.id === value);
    setState((prevState) => {
      return { ...prevState, selectedPage };
    });
  };

  return (
    <div data-testid="form" className={classes.form}>
      <Tabs
        classes={{ root: classes.tabsRoot }}
        value={state.selectedPage.id}
        onChange={onChangeTab}
        variant="scrollable"
        scrollButtons="on"
        textColor="primary"
        indicatorColor="primary"
      >
        {state.pages.map((page) => {
          return (
            <Tab
              {...a11yProps(page.id)}
              classes={{ root: classes.tabRoot }}
              value={page.id}
              label={<div className={classes.tabLabel}>{page.label}</div>}
              key={page.id}
            />
          );
        })}
      </Tabs>
      <Page
        editingContextId={editingContextId}
        formId={id}
        page={state.selectedPage}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    </div>
  );
};

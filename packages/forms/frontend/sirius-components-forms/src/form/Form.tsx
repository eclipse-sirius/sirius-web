/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
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
    width: 'inherit',
  },
  tabLabelText: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  toolbarAction: {
    paddingRight: theme.spacing(1),
    whiteSpace: 'nowrap',
  },
  pagesListAndToolbar: {
    display: 'flex',
    justifyContent: 'space-between',
    paddingLeft: theme.spacing(1),
    overflowX: 'auto',
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

  let selectedPageToolbar = null;
  if (state.selectedPage.toolbarActions?.length > 0) {
    selectedPageToolbar = (
      <div className={classes.toolbar}>
        {state.selectedPage.toolbarActions.map((toolbarAction) => (
          <div className={classes.toolbarAction} key={toolbarAction.id}>
            <ToolbarAction editingContextId={editingContextId} formId={id} readOnly={readOnly} widget={toolbarAction} />
          </div>
        ))}
      </div>
    );
  }

  return (
    <div data-testid="form" className={classes.form}>
      <div className={classes.pagesListAndToolbar}>
        <Tabs
          classes={{ root: classes.tabsRoot }}
          value={state.selectedPage.id}
          onChange={onChangeTab}
          variant="scrollable"
          scrollButtons="on"
          textColor="primary"
          indicatorColor="primary">
          {state.pages.map((page) => {
            return (
              <Tab
                {...a11yProps(page.id)}
                classes={{ root: classes.tabRoot }}
                value={page.id}
                label={
                  <div className={classes.tabLabel}>
                    <div className={classes.tabLabelText}>{page.label}</div>
                  </div>
                }
                key={page.id}
              />
            );
          })}
        </Tabs>
        {selectedPageToolbar}
      </div>
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

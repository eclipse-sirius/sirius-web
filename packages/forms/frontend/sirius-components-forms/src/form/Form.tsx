/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import React, { ForwardedRef, forwardRef, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { Page } from '../pages/Page';
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
import { FormHandle, FormProps, FormState } from './Form.types';
import { GQLPage } from './FormEventFragments.types';
import { useFormHandle } from './useFormHandle';

const useFormStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    gap: theme.spacing(1),
  },
  tabsRoot: {
    minHeight: theme.spacing(3),
    borderBottomColor: theme.palette.divider,
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
  },
  tabRoot: {
    minHeight: theme.spacing(3),
    textTransform: 'none',
    padding: '1px 16px',
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
  },
}));

const a11yProps = (id: string) => {
  return {
    id: `simple-tab-${id}`,
    'aria-controls': `simple-tabpanel-${id}`,
  };
};

export const Form = forwardRef<FormHandle | null, FormProps>(
  ({ editingContextId, form, initialSelectedPageLabel, readOnly }: FormProps, ref: ForwardedRef<FormHandle | null>) => {
    const { classes } = useFormStyles();
    const { id, pages } = form;

    let selectedPage: GQLPage | null = null;
    if (initialSelectedPageLabel) {
      selectedPage = pages.find((page) => page.label === initialSelectedPageLabel) ?? null;
    } else if (pages[0]) {
      selectedPage = pages[0];
    }
    const [state, setState] = useState<FormState>({ selectedPage, pages });

    useFormHandle(state.selectedPage?.label ?? null, ref);

    useEffect(() => {
      setState(() => {
        const selectedPage: GQLPage | null = pages.find((page) => page.label === state.selectedPage?.label) ?? null;
        if (selectedPage) {
          return { selectedPage, pages };
        }
        return { selectedPage: pages[0] ?? null, pages };
      });
    }, [pages, state.selectedPage?.label]);

    const onChangeTab = (_: React.ChangeEvent<{}>, value: string) => {
      const selectedPage: GQLPage | null = pages.find((page) => page.id === value) ?? null;
      setState((prevState) => {
        return { ...prevState, selectedPage };
      });
    };

    let selectedPageToolbar: JSX.Element | null = null;
    if (state.selectedPage && state.selectedPage.toolbarActions?.length > 0) {
      selectedPageToolbar = (
        <div className={classes.toolbar}>
          {state.selectedPage.toolbarActions.map((toolbarAction) => (
            <div className={classes.toolbarAction} key={toolbarAction.id}>
              <ToolbarAction
                editingContextId={editingContextId}
                formId={id}
                readOnly={readOnly}
                widget={toolbarAction}
              />
            </div>
          ))}
        </div>
      );
    }

    let page: JSX.Element | null = null;
    if (state.selectedPage) {
      page = <Page editingContextId={editingContextId} formId={id} page={state.selectedPage} readOnly={readOnly} />;
    }
    const maxWidth: number = state.pages.length > 1 ? 100 : 390; // 390 is the maxWidth to fit to the default Details view
    const variant: 'scrollable' | 'standard' = state.pages.length > 1 ? 'scrollable' : 'standard';
    return (
      <div data-testid="form" className={classes.form}>
        <div className={classes.pagesListAndToolbar}>
          <Tabs
            classes={{ root: classes.tabsRoot }}
            value={state.selectedPage?.id}
            onChange={onChangeTab}
            variant={variant}
            scrollButtons
            textColor="primary"
            indicatorColor="primary">
            {state.pages.map((page) => {
              return (
                <Tab
                  {...a11yProps(page.id)}
                  classes={{ root: classes.tabRoot }}
                  style={{ minWidth: 1, maxWidth: maxWidth }} // Set minWidth to one to force tab width to fit the page label length
                  value={page.id}
                  title={page.label}
                  label={
                    <div className={classes.tabLabel}>
                      <div className={classes.tabLabelText}>{page.label}</div>
                    </div>
                  }
                  key={page.id}
                  data-testid={`page-tab-${page.label}`}
                />
              );
            })}
          </Tabs>
          {selectedPageToolbar}
        </div>
        {page}
      </div>
    );
  }
);

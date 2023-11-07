/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { ApolloProvider } from '@apollo/client';
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import CssBaseline from '@material-ui/core/CssBaseline';
import { Theme, ThemeProvider } from '@material-ui/core/styles';
import React, { useMemo } from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ToastProvider } from '../../src/toast/ToastProvider';
import { createApolloGraphQLClient } from '../ApolloGraphQLClient';
import { RepresentationContextProvider } from '../representations/RepresentationContextProvider';
import { Router } from '../router/Router';
import { siriusWebTheme as defaultTheme } from '../theme/siriusWebTheme';
import { Views, defaultValue } from '../views/Views';
import { ViewsProps } from '../views/Views.types';
import { ViewsContext } from '../views/ViewsContext';
import { ViewsContextValue } from '../views/ViewsContext.types';
import { SiriusWebApplicationProps } from './SiriusWebApplication.types';

const style = {
  display: 'grid',
  gridTemplateColumns: '1fr',
  gridTemplateRows: '1fr',
  minHeight: '100vh',
};

export const SiriusWebApplication = ({ httpOrigin, wsOrigin, theme, children }: SiriusWebApplicationProps) => {
  const siriusWebTheme: Theme = theme ? theme : defaultTheme;

  const apolloClient = useMemo(() => createApolloGraphQLClient(httpOrigin, wsOrigin), [httpOrigin, wsOrigin]);

  const value: ViewsContextValue = { ...defaultValue };
  React.Children.forEach(children, (child) => {
    if (React.isValidElement(child) && child.type === Views) {
      const { applicationIcon, applicationBarMenu } = child.props as ViewsProps;
      if (applicationIcon) {
        value.applicationIcon = applicationIcon;
      }
      if (applicationBarMenu) {
        value.applicationBarMenu = applicationBarMenu;
      }
    }
  });

  return (
    <ApolloProvider client={apolloClient}>
      <BrowserRouter>
        <ThemeProvider theme={siriusWebTheme}>
          <CssBaseline />
          <ServerContext.Provider value={{ httpOrigin }}>
            <ToastProvider>
              <RepresentationContextProvider>
                <ViewsContext.Provider value={value}>
                  <div style={style}>
                    <Router />
                  </div>
                </ViewsContext.Provider>
              </RepresentationContextProvider>
            </ToastProvider>
          </ServerContext.Provider>
        </ThemeProvider>
      </BrowserRouter>
    </ApolloProvider>
  );
};

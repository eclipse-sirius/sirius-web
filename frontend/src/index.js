/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { ApolloProvider } from '@apollo/client';
import purple from '@material-ui/core/colors/purple';
import CssBaseline from '@material-ui/core/CssBaseline';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import { ApolloGraphQLClient } from 'ApolloGraphQLClient';
import { Main } from 'main/Main';
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import './fonts.css';
import styles from './index.module.css';
import './reset.css';
import './Sprotty.css';
import './variables.css';

const theme = createMuiTheme({
  palette: {
    type: 'light',
    primary: purple
  }
});

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <div className={styles.app}>
          <Main />
        </div>
      </ThemeProvider>
    </BrowserRouter>
  </ApolloProvider>,
  document.getElementById('root')
);

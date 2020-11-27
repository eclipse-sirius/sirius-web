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
import { GraphQLClient, graphQLHttpClient, graphQLWebSocketClient } from '@eclipse-sirius/sirius-components';
import { ApolloProvider } from '@apollo/client';
import { ApolloGraphQLClient } from 'ApolloGraphQLClient';
import { Main } from 'main/Main';
import React, { StrictMode } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import styles from './index.module.css';
import './fonts.css';
import './reset.css';
import './variables.css';
import './Sprotty.css';

/**
 * Entry point of the frontend, defines the main layout of the application.
 *
 * @author sbegaudeau
 */
export const App = () => {
  return (
    <div className={styles.app}>
      <Main />
    </div>
  );
};

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <GraphQLClient.Provider value={{ graphQLHttpClient, graphQLWebSocketClient }}>
      <BrowserRouter>
        <StrictMode>
          <App />
        </StrictMode>
      </BrowserRouter>
    </GraphQLClient.Provider>
  </ApolloProvider>,
  document.getElementById('root')
);

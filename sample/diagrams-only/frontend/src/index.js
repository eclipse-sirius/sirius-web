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
import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import { ApolloProvider, ApolloClient, HttpLink, InMemoryCache, split } from '@apollo/client';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';
import { DiagramWebSocketContainer } from '@eclipse-sirius/sirius-components';

import './reset.css';
import './variables.css';
import './Sprotty.css';

const App = () => {
  const [state, setState] = useState({
    projectId: '',
    diagramId: '',
    selection: null,
    load: false,
  });

  const onProjectIdChange = (event) => {
    const { value } = event.target;
    setState((prevState) => {
      return { ...prevState, projectId: value, load: false };
    });
  };

  const onDiagramIdChange = (event) => {
    const { value } = event.target;
    setState((prevState) => {
      return { ...prevState, diagramId: value, load: false };
    });
  };

  const onClick = () => {
    setState((prevState) => {
      return { ...prevState, load: true };
    });
  };

  const setSelection = (selection) => {
    setState((prevState) => {
      return { ...prevState, selection };
    });
  };

  const setSubscribers = (subscribers) => {};

  const appStyle = {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr',
    rowGap: '16px',
    height: '1000px',
    width: '1000px',
    padding: '32px',
  };
  const headerStyle = {
    display: 'grid',
    gridTemplateColumns: 'min-content min-content min-content min-content min-content',
    gridTemplateRows: '1fr',
    columnGap: '16px',
  };
  const buttonStyle = {
    border: '1px solid black',
    padding: '2px 8px',
    borderRadius: '5px',
  };
  const diagramStyle = {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  };

  return (
    <div style={appStyle}>
      <div style={headerStyle}>
        ProjectId
        <input type="text" value={state.projectId} onChange={onProjectIdChange} />
        DiagramId
        <input type="text" value={state.diagramId} onChange={onDiagramIdChange} />
        <button style={buttonStyle} onClick={onClick}>
          Load
        </button>
      </div>
      {state.projectId && state.diagramId && state.load ? (
        <div style={diagramStyle}>
          <DiagramWebSocketContainer
            projectId={state.projectId}
            representationId={state.diagramId}
            readOnly={true}
            selection={state.selection}
            setSelection={setSelection}
            setSubscribers={setSubscribers}
          />
        </div>
      ) : null}
    </div>
  );
};

const httpLink = new HttpLink({
  uri: `http://localhost:8080/api/graphql`,
});

const wsLink = new WebSocketLink({
  uri: `ws://localhost:8080/subscriptions`,
  options: {
    reconnect: true,
    lazy: true,
  },
});

const splitLink = split(
  ({ query }) => {
    const definition = getMainDefinition(query);
    return definition.kind === 'OperationDefinition' && definition.operation === 'subscription';
  },
  wsLink,
  httpLink
);

export const ApolloGraphQLClient = new ApolloClient({
  link: splitLink,
  cache: new InMemoryCache(),
});

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <App />
  </ApolloProvider>,
  document.getElementById('root')
);

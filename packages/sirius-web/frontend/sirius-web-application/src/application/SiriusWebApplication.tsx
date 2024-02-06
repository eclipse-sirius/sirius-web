/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import {
  ExtensionProvider,
  ExtensionRegistry,
  RepresentationPathContext,
  ServerContext,
} from '@eclipse-sirius/sirius-components-core';
import { NodeTypeContext, NodeTypeContextValue } from '@eclipse-sirius/sirius-components-diagrams';
import CssBaseline from '@material-ui/core/CssBaseline';
import { Theme, ThemeProvider } from '@material-ui/core/styles';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ToastProvider } from '../../src/toast/ToastProvider';
import {
  DiagramRepresentationConfiguration,
  defaultNodeTypeRegistry,
} from '../diagrams/DiagramRepresentationConfiguration';
import { DiagramRepresentationConfigurationProps } from '../diagrams/DiagramRepresentationConfiguration.types';
import { ApolloGraphQLProvider } from '../graphql/ApolloGraphQLProvider';
import { RepresentationContextProvider } from '../representations/RepresentationContextProvider';
import { Router } from '../router/Router';
import { siriusWebTheme as defaultTheme } from '../theme/siriusWebTheme';
import { SiriusWebApplicationProps } from './SiriusWebApplication.types';

const style = {
  display: 'grid',
  gridTemplateColumns: '1fr',
  gridTemplateRows: '1fr',
  minHeight: '100vh',
};

export const SiriusWebApplication = ({
  httpOrigin,
  wsOrigin,
  extensionRegistry,
  theme,
  children,
}: SiriusWebApplicationProps) => {
  const siriusWebTheme: Theme = theme ? theme : defaultTheme;

  let nodeTypeRegistryValue: NodeTypeContextValue = { ...defaultNodeTypeRegistry };
  React.Children.forEach(children, (child) => {
    if (React.isValidElement(child) && child.type === DiagramRepresentationConfiguration) {
      const { nodeTypeRegistry } = child.props as DiagramRepresentationConfigurationProps;
      if (nodeTypeRegistry) {
        nodeTypeRegistryValue = nodeTypeRegistry;
      }
    }
  });

  const getRepresentationPath = (editingContextId: string, representationId: string) => {
    // Note that this should match the corresponding route configuration
    return `/projects/${editingContextId}/edit/${representationId}`;
  };

  return (
    <ExtensionProvider registry={extensionRegistry ?? new ExtensionRegistry()}>
      <ApolloGraphQLProvider httpOrigin={httpOrigin} wsOrigin={wsOrigin}>
        <BrowserRouter>
          <ThemeProvider theme={siriusWebTheme}>
            <CssBaseline />
            <ServerContext.Provider value={{ httpOrigin }}>
              <RepresentationPathContext.Provider value={{ getRepresentationPath }}>
                <ToastProvider>
                  <RepresentationContextProvider>
                    <NodeTypeContext.Provider value={nodeTypeRegistryValue}>
                      <div style={style}>
                        <Router />
                      </div>
                    </NodeTypeContext.Provider>
                  </RepresentationContextProvider>
                </ToastProvider>
              </RepresentationPathContext.Provider>
            </ServerContext.Provider>
          </ThemeProvider>
        </BrowserRouter>
      </ApolloGraphQLProvider>
    </ExtensionProvider>
  );
};

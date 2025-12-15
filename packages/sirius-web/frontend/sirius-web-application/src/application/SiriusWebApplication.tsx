/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { ExtensionProvider, ServerContext } from '@eclipse-sirius/sirius-components-core';
import { NodeTypeContext, NodeTypeContextValue } from '@eclipse-sirius/sirius-components-diagrams';
import CssBaseline from '@mui/material/CssBaseline';
import { Theme, ThemeProvider } from '@mui/material/styles';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ToastProvider } from '../../src/toast/ToastProvider';
import {
  DiagramRepresentationConfiguration,
  defaultNodeTypeRegistry,
} from '../diagrams/DiagramRepresentationConfiguration';
import { DiagramRepresentationConfigurationProps } from '../diagrams/DiagramRepresentationConfiguration.types';
import { defaultExtensionRegistry } from '../extension/DefaultExtensionRegistry';
import { DefaultExtensionRegistryMergeStrategy } from '../extension/DefaultExtensionRegistryMergeStrategy';
import { ApolloGraphQLProvider } from '../graphql/ApolloGraphQLProvider';
import { I18nContextProvider } from '../i18n/I18nContext';
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
  extensionRegistryMergeStrategy,
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

  if (extensionRegistry) {
    defaultExtensionRegistry.addAll(
      extensionRegistry,
      extensionRegistryMergeStrategy ? extensionRegistryMergeStrategy : new DefaultExtensionRegistryMergeStrategy()
    );
  }

  return (
    <ExtensionProvider registry={defaultExtensionRegistry}>
      <ApolloGraphQLProvider httpOrigin={httpOrigin} wsOrigin={wsOrigin}>
        <I18nContextProvider httpOrigin={httpOrigin}>
          <BrowserRouter>
            <ThemeProvider theme={siriusWebTheme}>
              <CssBaseline />
              <ServerContext.Provider value={{ httpOrigin }}>
                <ToastProvider>
                  <NodeTypeContext.Provider value={nodeTypeRegistryValue}>
                    <div style={style}>
                      <Router />
                    </div>
                  </NodeTypeContext.Provider>
                </ToastProvider>
              </ServerContext.Provider>
            </ThemeProvider>
          </BrowserRouter>
        </I18nContextProvider>
      </ApolloGraphQLProvider>
    </ExtensionProvider>
  );
};

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
  ConfirmationDialogContextProvider,
  ExtensionProvider,
  ExtensionRegistry,
  RepresentationPathContext,
  ServerContext,
  WorkbenchViewContribution,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-core';
import { NodeTypeContext, NodeTypeContextValue } from '@eclipse-sirius/sirius-components-diagrams';
import {
  DetailsView,
  PropertySectionContext,
  RelatedElementsView,
  RepresentationsView,
} from '@eclipse-sirius/sirius-components-forms';
import { ExplorerView } from '@eclipse-sirius/sirius-components-trees';
import { ValidationView } from '@eclipse-sirius/sirius-components-validation';
import CssBaseline from '@material-ui/core/CssBaseline';
import { Theme, ThemeProvider } from '@material-ui/core/styles';
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import Filter from '@material-ui/icons/Filter';
import LinkIcon from '@material-ui/icons/Link';
import MenuIcon from '@material-ui/icons/Menu';
import WarningIcon from '@material-ui/icons/Warning';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ToastProvider } from '../../src/toast/ToastProvider';
import {
  DiagramRepresentationConfiguration,
  defaultNodeTypeRegistry,
} from '../diagrams/DiagramRepresentationConfiguration';
import { DiagramRepresentationConfigurationProps } from '../diagrams/DiagramRepresentationConfiguration.types';
import { DefaultExtensionRegistryMergeStrategy } from '../extension/DefaultExtensionRegistryMergeStrategy';
import { propertySectionsRegistry } from '../forms/defaultPropertySectionRegistry';
import { ApolloGraphQLProvider } from '../graphql/ApolloGraphQLProvider';
import { OnboardArea } from '../onboarding/OnboardArea';
import { RepresentationContextProvider } from '../representations/RepresentationContextProvider';
import { Router } from '../router/Router';
import { siriusWebTheme as defaultTheme } from '../theme/siriusWebTheme';
import { ProjectSettingTabContribution } from '../views/project-settings/ProjectSettingsView.types';
import { projectSettingsTabExtensionPoint } from '../views/project-settings/ProjectSettingsViewExtensionPoints';
import { ProjectImagesSettings } from '../views/project-settings/images/ProjectImagesSettings';
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

  const getRepresentationPath = (editingContextId: string, representationId: string) => {
    // Note that this should match the corresponding route configuration
    return `/projects/${editingContextId}/edit/${representationId}`;
  };

  const workbenchViewContributions: WorkbenchViewContribution[] = [
    {
      side: 'left',
      title: 'Explorer',
      icon: <AccountTreeIcon />,
      component: ExplorerView,
    },
    {
      side: 'left',
      title: 'Validation',
      icon: <WarningIcon />,
      component: ValidationView,
    },
    {
      side: 'right',
      title: 'Details',
      icon: <MenuIcon />,
      component: DetailsView,
    },
    {
      side: 'right',
      title: 'Representations',
      icon: <Filter />,
      component: RepresentationsView,
    },
    {
      side: 'right',
      title: 'Related Elements',
      icon: <LinkIcon />,
      component: RelatedElementsView,
    },
  ];

  const projectSettingsTabContributions: ProjectSettingTabContribution[] = [
    {
      title: 'Images',
      component: ProjectImagesSettings,
    },
  ];

  const internalExtensionRegistry = new ExtensionRegistry();
  internalExtensionRegistry.addComponent(workbenchMainAreaExtensionPoint, {
    identifier: 'siriusweb_workbench#mainArea',
    Component: OnboardArea,
  });
  internalExtensionRegistry.putData(workbenchViewContributionExtensionPoint, {
    identifier: 'siriusweb_workbench#viewContribution',
    data: workbenchViewContributions,
  });
  internalExtensionRegistry.putData(projectSettingsTabExtensionPoint, {
    identifier: 'sw_projectSettingsTab',
    data: projectSettingsTabContributions,
  });

  if (extensionRegistry) {
    internalExtensionRegistry.addAll(
      extensionRegistry,
      extensionRegistryMergeStrategy ? extensionRegistryMergeStrategy : new DefaultExtensionRegistryMergeStrategy()
    );
  }

  return (
    <ExtensionProvider registry={internalExtensionRegistry}>
      <ApolloGraphQLProvider httpOrigin={httpOrigin} wsOrigin={wsOrigin}>
        <BrowserRouter>
          <ThemeProvider theme={siriusWebTheme}>
            <CssBaseline />
            <ServerContext.Provider value={{ httpOrigin }}>
              <RepresentationPathContext.Provider value={{ getRepresentationPath }}>
                <ToastProvider>
                  <ConfirmationDialogContextProvider>
                    <RepresentationContextProvider>
                      <NodeTypeContext.Provider value={nodeTypeRegistryValue}>
                        <PropertySectionContext.Provider value={{ propertySectionsRegistry }}>
                          <div style={style}>
                            <Router />
                          </div>
                        </PropertySectionContext.Provider>
                      </NodeTypeContext.Provider>
                    </RepresentationContextProvider>
                  </ConfirmationDialogContextProvider>
                </ToastProvider>
              </RepresentationPathContext.Provider>
            </ServerContext.Provider>
          </ThemeProvider>
        </BrowserRouter>
      </ApolloGraphQLProvider>
    </ExtensionProvider>
  );
};

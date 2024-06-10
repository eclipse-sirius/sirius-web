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

export { SiriusWebApplication } from './application/SiriusWebApplication';
export type { SiriusWebApplicationProps } from './application/SiriusWebApplication.types';
export { DiagramRepresentationConfiguration } from './diagrams/DiagramRepresentationConfiguration';
export type { NodeTypeRegistry } from './diagrams/DiagramRepresentationConfiguration.types';
export {
  type ApolloClientOptionsConfigurer,
  type CacheOptionsConfigurer,
  type HttpOptionsConfigurer,
  type WebSocketOptionsConfigurer,
} from './graphql/useCreateApolloClient.types';
export {
  apolloClientOptionsConfigurersExtensionPoint,
  cacheOptionsConfigurersExtensionPoint,
  httpOptionsConfigurersExtensionPoint,
  webSocketOptionsConfigurersExtensionPoint,
} from './graphql/useCreateApolloClientExtensionPoints';
export { type NavigationBarIconProps, type NavigationBarMenuProps } from './navigationBar/NavigationBar.types';
export {
  navigationBarIconExtensionPoint,
  navigationBarMenuExtensionPoint,
} from './navigationBar/NavigationBarExtensionPoints';
export { routerExtensionPoint } from './router/RouterExtensionPoints';
export { projectActionButtonMenuItemExtensionPoint } from './views/project-browser/list-projects-area/ProjectActionButtonExtensionPoints';
export { type ProjectRowProps } from './views/project-browser/list-projects-area/ProjectRow.types';
export { projectsTableRowExtensionPoint } from './views/project-browser/list-projects-area/ProjectsTableExtensionPoints';
export { type ProjectSettingTabContribution } from './views/project-settings/ProjectSettingsView.types';
export * from './views/project-settings/ProjectSettingsViewExtensionPoints';

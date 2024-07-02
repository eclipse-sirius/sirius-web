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
export { DefaultExtensionRegistryMergeStrategy } from './extension/DefaultExtensionRegistryMergeStrategy';
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
export {
  type NavigationBarIconProps,
  type NavigationBarLeftContributionProps,
  type NavigationBarMenuProps,
  type NavigationBarRightContributionProps,
} from './navigationBar/NavigationBar.types';
export {
  navigationBarIconExtensionPoint,
  navigationBarLeftContributionExtensionPoint,
  navigationBarMenuExtensionPoint,
  navigationBarRightContributionExtensionPoint,
} from './navigationBar/NavigationBarExtensionPoints';
export { routerExtensionPoint } from './router/RouterExtensionPoints';
export { type EditProjectNavbarSubtitleProps } from './views/edit-project/EditProjectNavbar/EditProjectNavbar.types';
export { editProjectNavbarSubtitleExtensionPoint } from './views/edit-project/EditProjectNavbar/EditProjectNavbarExtensionPoints';
export { useCurrentProject } from './views/edit-project/useCurrentProject';
export type { UseCurrentProjectValue } from './views/edit-project/useCurrentProject.types';
export type { GQLProject } from './views/edit-project/useProjectAndRepresentationMetadata.types';
export { type CreateProjectAreaCardProps } from './views/project-browser/create-projects-area/CreateProjectArea.types';
export { createProjectAreaCardExtensionPoint } from './views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
export { projectActionButtonMenuItemExtensionPoint } from './views/project-browser/list-projects-area/ProjectActionButtonExtensionPoints';
export { type ProjectRowProps } from './views/project-browser/list-projects-area/ProjectRow.types';
export { projectsTableRowExtensionPoint } from './views/project-browser/list-projects-area/ProjectsTableExtensionPoints';

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

export { SiriusWebApplication } from './application/SiriusWebApplication';
export type { SiriusWebApplicationProps } from './application/SiriusWebApplication.types';
export { DiagramRepresentationConfiguration } from './diagrams/DiagramRepresentationConfiguration';
export type { NodeTypeRegistry } from './diagrams/DiagramRepresentationConfiguration.types';
export { DefaultExtensionRegistryMergeStrategy } from './extension/DefaultExtensionRegistryMergeStrategy';
export { ellipseNodeStyleDocumentTransform } from './extension/EllipseNodeDocumentTransform';
export { referenceWidgetDocumentTransform } from './extension/ReferenceWidgetDocumentTransform';
export type { FooterProps } from './footer/Footer.types';
export { footerExtensionPoint } from './footer/FooterExtensionPoints';
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
export { NewObjectModal } from './modals/new-object/NewObjectModal';
export { type NewObjectModalProps } from './modals/new-object/NewObjectModal.types';
export { NewRepresentationModal } from './modals/new-representation/NewRepresentationModal';
export { type NewRepresentationModalProps } from './modals/new-representation/NewRepresentationModal.types';
export { NewRootObjectModal } from './modals/new-root-object/NewRootObjectModal';
export { type NewRootObjectModalProps } from './modals/new-root-object/NewRootObjectModal.types';
export { NavigationBar } from './navigationBar/NavigationBar';
export {
  type NavigationBarIconProps,
  type NavigationBarLeftContributionProps,
  type NavigationBarProps,
  type NavigationBarRightContributionProps,
} from './navigationBar/NavigationBar.types';
export {
  navigationBarCenterContributionExtensionPoint,
  navigationBarIconExtensionPoint,
  navigationBarLeftContributionExtensionPoint,
  navigationBarRightContributionExtensionPoint,
} from './navigationBar/NavigationBarExtensionPoints';
export {
  type NavigationBarMenuContainerProps,
  type NavigationBarMenuIconProps,
  type NavigationBarMenuItemProps,
} from './navigationBar/NavigationBarMenu.types';
export {
  navigationBarMenuContainerExtensionPoint,
  navigationBarMenuEntryExtensionPoint,
  navigationBarMenuHelpURLExtensionPoint,
  navigationBarMenuIconExtensionPoint,
} from './navigationBar/NavigationBarMenuExtensionPoints';
export { routerExtensionPoint } from './router/RouterExtensionPoints';
export type {
  EditProjectNavbarMenuContainerProps,
  EditProjectNavbarMenuEntryProps,
  EditProjectNavbarSubtitleProps,
} from './views/edit-project/EditProjectNavbar/EditProjectNavbar.types';
export {
  editProjectNavbarMenuContainerExtensionPoint,
  editProjectNavbarMenuEntryExtensionPoint,
} from './views/edit-project/EditProjectNavbar/EditProjectNavbarMenuExtensionPoints';
export { type ProjectReadOnlyPredicate } from './views/edit-project/EditProjectView.types';
export { editProjectViewReadOnlyPredicateExtensionPoint } from './views/edit-project/EditProjectViewExtensionPoints';
export { useCurrentProject } from './views/edit-project/useCurrentProject';
export type { UseCurrentProjectValue } from './views/edit-project/useCurrentProject.types';
export type { GQLProject } from './views/edit-project/useProjectAndRepresentationMetadata.types';
export { DetailsView } from './views/edit-project/workbench-views/details/DetailsView';
export type { GQLDetailsEventPayload } from './views/edit-project/workbench-views/details/useDetailsViewSubscription.types';
export { useExplorerSubscription } from './views/edit-project/workbench-views/explorer/useExplorerSubscription';
export {
  type GQLTreeEventPayload,
  type GQLTreeRefreshedEventPayload,
  type UseExplorerSubscriptionValue,
} from './views/edit-project/workbench-views/explorer/useExplorerSubscription.types';
export { useRelatedElementsViewSubscription } from './views/edit-project/workbench-views/related-elements/useRelatedElementsViewSubscription';
export {
  type GQLFormRefreshedEventPayload,
  type GQLRelatedElementsEventPayload,
  type UseRelatedElementsViewSubscriptionValue,
} from './views/edit-project/workbench-views/related-elements/useRelatedElementsViewSubscription.types';
export { ErrorView } from './views/error/ErrorView';
export { type ErrorMessageProvider } from './views/error/ErrorView.types';
export { errorMessageProvidersExtensionPoint } from './views/error/ErrorViewExtensionPoints';
export { type CreateProjectAreaCardProps } from './views/project-browser/create-projects-area/CreateProjectArea.types';
export { createProjectAreaCardExtensionPoint } from './views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
export { ProjectActionButton } from './views/project-browser/list-projects-area/ProjectActionButton';
export { type ProjectContextMenuEntryProps } from './views/project-browser/list-projects-area/ProjectActionButton.types';
export {
  projectContextMenuContainerExtensionPoint,
  projectContextMenuEntryExtensionPoint,
} from './views/project-browser/list-projects-area/ProjectContextMenuExtensionPoints';
export { type ProjectRowProps } from './views/project-browser/list-projects-area/ProjectRow.types';
export { projectsTableRowExtensionPoint } from './views/project-browser/list-projects-area/ProjectsTableExtensionPoints';
export { type ProjectImagesSettingsModal } from './views/project-settings/images/ProjectImagesSettings.types';
export { UploadImageModal } from './views/project-settings/images/upload-image/UploadImageModal';
export { useProjectImages } from './views/project-settings/images/useProjectImages';
export { type GQLImageMetadata } from './views/project-settings/images/useProjectImages.types';
export {
  type ProjectSettingTabContribution,
  type ProjectSettingTabProps,
} from './views/project-settings/ProjectSettingsView.types';
export * from './views/project-settings/ProjectSettingsViewExtensionPoints';

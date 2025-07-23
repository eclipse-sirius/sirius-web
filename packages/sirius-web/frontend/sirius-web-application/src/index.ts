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
export { ellipseNodeStyleDocumentTransform } from './extension/ellipsenode/EllipseNodeDocumentTransform';
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
export { PublishLibraryDialog } from './libraries/PublishLibraryDialog';
export { type PublishLibraryDialogProps } from './libraries/PublishLibraryDialog.types';
export { DuplicateObjectModal } from './modals/duplicate-object/DuplicateObjectModal';
export { type DuplicateObjectModalProps } from './modals/duplicate-object/DuplicateObjectModal.types';
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
export { ImportLibraryCommand } from './omnibox/ImportLibraryCommand';
export { routerExtensionPoint } from './router/RouterExtensionPoints';
export { useCurrentViewer } from './viewer/useCurrentViewer';
export type { UseCurrentViewerValue } from './viewer/useCurrentViewer.types';
export { useViewer } from './viewer/useViewer';
export type { UseViewerValue } from './viewer/useViewer.types';
export { ViewerContext, ViewerContextProvider } from './viewer/ViewerContext';
export type { ViewerContextProviderProps, ViewerContextValue } from './viewer/ViewerContext.types';
export { DisplayLibraryView } from './views/display-library/DisplayLibraryView';
export { EditProjectView } from './views/edit-project/EditProjectView';
export type {
  EditProjectNavbarMenuContainerProps,
  EditProjectNavbarMenuEntryProps,
} from './views/edit-project/navbar/context-menu/EditProjectNavbarContextMenu.types';
export {
  editProjectNavbarMenuContainerExtensionPoint,
  editProjectNavbarMenuEntryExtensionPoint,
} from './views/edit-project/navbar/context-menu/EditProjectNavbarMenuExtensionPoints';
export type { EditProjectNavbarSubtitleProps } from './views/edit-project/navbar/EditProjectNavbar.types';
export { useCurrentProject } from './views/edit-project/useCurrentProject';
export type { UseCurrentProjectValue } from './views/edit-project/useCurrentProject.types';
export type { GQLProject } from './views/edit-project/useProjectAndRepresentationMetadata.types';
export { DetailsView } from './views/edit-project/workbench-views/details/DetailsView';
export type { GQLDetailsEventPayload } from './views/edit-project/workbench-views/details/useDetailsViewSubscription.types';
export { ExpandAllTreeItemContextMenuContribution } from './views/edit-project/workbench-views/explorer/context-menu-contributions/ExpandAllTreeItemContextMenuContribution';
export { UpdateLibraryModal } from './views/edit-project/workbench-views/explorer/context-menu-contributions/update-library/UpdateLibraryModal';
export * from './views/edit-project/workbench-views/explorer/context-menu-contributions/update-library/UpdateLibraryModal.types';
export { UpdateLibraryTreeItemContextMenuContribution } from './views/edit-project/workbench-views/explorer/context-menu-contributions/UpdateLibraryTreeItemContextMenuContribution';
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
export { LibraryBrowserView } from './views/library-browser/LibraryBrowserView';
export { NewProjectView } from './views/new-project/NewProjectView';
export { type CreateProjectAreaCardProps } from './views/project-browser/create-projects-area/CreateProjectArea.types';
export { createProjectAreaCardExtensionPoint } from './views/project-browser/create-projects-area/CreateProjectAreaExtensionPoints';
export { ProjectActionButton } from './views/project-browser/list-projects-area/ProjectActionButton';
export { type ProjectContextMenuEntryProps } from './views/project-browser/list-projects-area/ProjectActionButton.types';
export {
  projectContextMenuContainerExtensionPoint,
  projectContextMenuEntryExtensionPoint,
} from './views/project-browser/list-projects-area/ProjectContextMenuExtensionPoints';
export { ProjectsTable } from './views/project-browser/list-projects-area/ProjectsTable';
export type { ProjectsTableProps } from './views/project-browser/list-projects-area/ProjectsTable.types';
export { projectFilterCustomizersExtensionPoint } from './views/project-browser/list-projects-area/useProjectsExtensionPoints';
export { type ProjectFilterCustomizer } from './views/project-browser/list-projects-area/useProjectsExtensionPoints.types';
export { projectsTableColumnCustomizersExtensionPoint } from './views/project-browser/list-projects-area/useProjectsTableColumnsExtensionPoints';
export { type ProjectsTableColumnCustomizer } from './views/project-browser/list-projects-area/useProjectsTableColumnsExtensionPoints.types';
export { ProjectBrowserView } from './views/project-browser/ProjectBrowserView';
export { type ProjectImagesSettingsModal } from './views/project-settings/images/ProjectImagesSettings.types';
export { UploadImageModal } from './views/project-settings/images/upload-image/UploadImageModal';
export { useProjectImages } from './views/project-settings/images/useProjectImages';
export { type GQLImageMetadata } from './views/project-settings/images/useProjectImages.types';
export { ProjectSettingsView } from './views/project-settings/ProjectSettingsView';
export {
  type ProjectSettingTabContribution,
  type ProjectSettingTabProps,
} from './views/project-settings/ProjectSettingsView.types';
export * from './views/project-settings/ProjectSettingsViewExtensionPoints';
export { UploadProjectView } from './views/upload-project/UploadProjectView';

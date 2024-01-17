/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo and others.
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
// It should be the first import
// Otherwise the following error will be thrown: styled_default is not a function
// https://github.com/vitejs/vite/issues/12423#issuecomment-2080351394
import '@mui/material/styles/styled';

export * from './color/getCSSColor';
export * from './contexts/RepresentationPathContext';
export type * from './contexts/RepresentationPathContext.types';
export * from './contexts/ServerContext';
export type * from './contexts/ServerContext.types';
export * from './contexts/ToastContext';
export type * from './contexts/ToastContext.types';
export * from './dataTransferTypes';
export { ExtensionProvider } from './extension/ExtensionProvider';
export { type ExtensionProviderProps } from './extension/ExtensionProvider.types';
export { ExtensionRegistry } from './extension/ExtensionRegistry';
export {
  type ComponentExtension,
  type ComponentExtensionPoint,
  type DataExtension,
  type DataExtensionPoint,
} from './extension/ExtensionRegistry.types';
export { type ExtensionRegistryMergeStrategy } from './extension/ExtensionRegistryMergeStrategy';
export { useComponent } from './extension/useComponent';
export { useComponents } from './extension/useComponents';
export { useData } from './extension/useData';
export type * from './graphql/GQLTypes.types';
export * from './icon/IconOverlay';
export * from './label/StyledLabel';
export * from './label/StyledLabel.type';
export * from './materialui';
export * from './modals/confirmation/ConfirmationDialogContext';
export type * from './modals/confirmation/ConfirmationDialogContext.types';
export * from './modals/confirmation/useConfirmationDialog';
export type * from './modals/confirmation/useConfirmationDialog.types';
export * from './modals/share-representation/ShareRepresentationModal';
export type * from './modals/share-representation/ShareRepresentationModal.types';
export * from './representationmetadata/useRepresentationMetadata';
export * from './selection/SelectionContext';
export type * from './selection/SelectionContext.types';
export * from './selection/useSelection';
export type * from './selection/useSelection.types';
export * from './theme';
export * from './toast/MultiToast';
export * from './toast/Toast';
export * from './toast/useReporting';
export * from './workbench/Panels';
export * from './workbench/Workbench';
export type * from './workbench/Workbench.types';
export * from './workbench/WorkbenchExtensionPoints';

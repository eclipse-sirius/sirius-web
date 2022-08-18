/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
// Required because Sprotty uses Inversify and both frameworks are written in TypeScript with experimental features.
import 'reflect-metadata';
export * from './common/dataTransferTypes';
export * from './common/sendFile';
export * from './common/ServerContext';
export * from './common/URL';
export * from './core/contextmenu/ContextMenu';
export * from './core/file-upload/FileUpload';
export * from './core/form/Form';
export * from './diagram/DiagramWebSocketContainer';
export * from './diagram/DiagramWebSocketContainer.types';
export * from './errors/ErrorBoundary';
export * from './icons';
export * from './modals/delete-project/DeleteProjectModal';
export * from './modals/new-document/NewDocumentModal';
export * from './modals/new-object/NewObjectModal';
export * from './modals/new-representation/NewRepresentationModal';
export * from './modals/new-root-object/NewRootObjectModal';
export * from './modals/rename-project/RenameProjectModal';
export * from './modals/share-diagram/ShareDiagramModal';
export * from './modals/upload-document/UploadDocumentModal';
export * from './onboarding/OnboardArea';
export * from './views/FormContainer';

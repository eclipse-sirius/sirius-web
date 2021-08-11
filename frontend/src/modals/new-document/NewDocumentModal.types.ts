/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
export interface NewDocumentModalProps {
  editingContextId: string;
  onDocumentCreated: () => void;
  onClose: () => void;
}

export interface StereotypeDescription {
  id: string;
  label: string;
}

export interface GQLGetStereotypeDescriptionsQueryVariables {
  editingContextId: string;
}

export interface GQLGetStereotypeDescriptionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  stereotypeDescriptions: GQLStereotypeDescriptionConnection;
}

export interface GQLStereotypeDescriptionConnection {
  edges: GQLStereotypeDescriptionEdge[];
}

export interface GQLStereotypeDescriptionEdge {
  node: GQLStereotypeDescription;
}

export interface GQLStereotypeDescription {
  id: string;
  label: string;
}

export interface GQLCreateDocumentMutationData {
  createDocument: GQLCreateDocumentPayload;
}

export interface GQLCreateDocumentPayload {
  __typename: string;
}

export interface GQLCreateDocumentSuccessPayload extends GQLCreateDocumentPayload {
  id: string;
}

export interface GQLErrorPayload extends GQLCreateDocumentPayload {
  message: string;
}

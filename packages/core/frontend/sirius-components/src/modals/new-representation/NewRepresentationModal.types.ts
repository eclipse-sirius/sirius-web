/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { Selection } from '../../workbench/Workbench.types';
export interface NewRepresentationModalProps {
  editingContextId: string;
  item: any;
  onRepresentationCreated: (representation: Selection) => void;
  onClose: () => void;
}

export interface GQLGetRepresentationDescriptionsQueryVariables {
  editingContextId: string;
  objectId: string;
}

export interface GQLGetRepresentationDescriptionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representationDescriptions: GQLRepresentationDescriptionConnection;
}

export interface GQLRepresentationDescriptionConnection {
  edges: GQLRepresentationDescriptionEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLRepresentationDescriptionEdge {
  node: GQLRepresentationDescriptionMetadata;
}

export interface GQLRepresentationDescriptionMetadata {
  id: string;
  label: string;
  defaultName: string;
}

export interface GQLPageInfo {
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  startCursor: string;
  endCursor: string;
}

export interface GQLCreateRepresentationMutationData {
  createRepresentation: GQLCreateRepresentationPayload;
}

export interface GQLCreateRepresentationPayload {
  __typename: string;
}

export interface GQLCreateRepesentationSuccessPayload extends GQLCreateRepresentationPayload {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  id: string;
  label: string;
  kind: string;
}

export interface GQLErrorPayload extends GQLCreateRepresentationPayload {
  message: string;
}

/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { GQLReferenceWidget } from '../ReferenceWidgetFragment.types';

export interface CreateModalProps {
  editingContextId: string;
  widget: GQLReferenceWidget;
  onClose: (newElementId: string) => void;
}

export interface GQLCreateChildMutationData {
  createChild: GQLCreateChildPayload;
}

export interface GQLCreateChildPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLCreateChildPayload {
  message: string;
}

export interface GQLGetChildCreationDescriptionsQueryVariables {
  editingContextId: string;
  kind: string;
  referenceKind?: string;
}

export interface GQLGetChildCreationDescriptionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  childCreationDescriptions: GQLChildCreationDescription[];
}

export interface GQLChildCreationDescription {
  id: string;
  label: string;
  iconURL: string;
}

export interface GQLCreateRootObjectMutationData {
  createRootObject: GQLCreateRootObjectPayload;
}

export interface GQLCreateRootObjectPayload {
  __typename: string;
}

export interface GQLGetDomainsQueryVariables {
  editingContextId: string;
}

export interface GQLGetDomainsQueryData {
  viewer: GQLRootViewer;
}

export interface GQLGetRootObjectCreationDescriptionsQueryVariables {
  editingContextId: string;
  domainId: string;
  suggested: boolean;
  referenceKind?: string;
}

export interface GQLGetRootObjectCreationDescriptionsQueryData {
  viewer: GQLRootViewer;
}

export interface GQLRootViewer {
  editingContext: GQLRootEditingContext;
}

export interface GQLRootEditingContext {
  rootObjectCreationDescriptions: GQLRootChildCreationDescription[];
  domains: GQLDomain[];
}

export interface GQLRootChildCreationDescription {
  id: string;
  label: string;
  iconURL: string;
}

export interface GQLDomain {
  id: string;
  label: string;
}

export interface GQLCreateRootObjectSuccessPayload extends GQLCreateRootObjectPayload {
  id: string;
  object: GQLObject;
}

export interface Domain {
  id: string;
  label: string;
}

export interface GQLCreateChildSuccessPayload extends GQLCreateChildPayload {
  id: string;
  object: GQLObject;
}

export interface ChildCreationDescription {
  id: string;
  label: string;
  iconURL: string;
}

export interface GQLObject {
  id: string;
  label: string;
  kind: string;
}

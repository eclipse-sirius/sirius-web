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
  formId: string;
  onClose: (newElementId: string) => void;
}

export interface GQLErrorPayload extends GQLCreateElementInReferencePayload {
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

export interface Domain {
  id: string;
  label: string;
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

export interface GQLCreateElementInReferenceMutationVariables {
  input: GQLCreateElementInReferenceInput;
}

export interface GQLCreateElementInReferenceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  containerId: string;
  domainId: string | null;
  creationDescriptionId: string;
}

export interface GQLCreateElementInReferenceMutationData {
  createElementInReference: GQLCreateElementInReferencePayload;
}

export interface GQLCreateElementInReferencePayload {
  __typename: string;
}

export interface GQLCreateElementInReferenceSuccessPayload extends GQLCreateElementInReferencePayload {
  id: string;
  object: GQLObject;
}

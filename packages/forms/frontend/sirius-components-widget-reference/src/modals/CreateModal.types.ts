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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import { GQLReferenceWidget } from '../ReferenceWidgetFragment.types';

export interface CreateModalState {
  domains: Domain[];
  selectedDomainId: string;
  selectedChildCreationDescriptionId: string;
  creationDescriptions: ChildCreationDescription[];
  newObjectId: string | null;
  containerSelected: GQLTreeItem | null;
  containerId: string | null;
  containerKind: string | null;
}

export interface CreateModalProps {
  editingContextId: string;
  widget: GQLReferenceWidget;
  formId: string;
  onClose: (newElementId: string | null) => void;
}

export interface GQLErrorPayload extends GQLCreateElementInReferencePayload {
  messages: GQLMessage[];
}

export interface GQLGetChildCreationDescriptionsQueryVariables {
  editingContextId: string;
  containerId: string;
  referenceKind?: string;
  descriptionId: string;
}

export interface GQLGetChildCreationDescriptionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  referenceWidgetChildCreationDescriptions: GQLChildCreationDescription[];
}

export interface GQLChildCreationDescription {
  id: string;
  label: string;
  iconURL: string[];
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
  referenceKind?: string;
  descriptionId: string;
}

export interface GQLGetRootObjectCreationDescriptionsQueryData {
  viewer: GQLRootViewer;
}

export interface GQLRootViewer {
  editingContext: GQLRootEditingContext;
}

export interface GQLRootEditingContext {
  referenceWidgetRootCreationDescriptions: GQLRootChildCreationDescription[];
  domains: GQLDomain[];
}

export interface GQLRootChildCreationDescription {
  id: string;
  label: string;
  iconURL: string[];
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
  iconURL: string[];
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
  descriptionId: string;
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
  messages: GQLMessage[];
}

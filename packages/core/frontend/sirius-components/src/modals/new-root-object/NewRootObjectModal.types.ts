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

export interface NewRootObjectModalProps {
  editingContextId: string;
  item: any;
  onObjectCreated: (object: Selection) => void;
  onClose: () => void;
}

export interface Domain {
  id: string;
  label: string;
}

export interface ChildCreationDescription {
  id: string;
  label: string;
}

export interface GQLGetDomainsQueryVariables {
  editingContextId: string;
}

export interface GQLGetDomainsQueryData {
  viewer: GQLViewer;
}

export interface GQLGetRootObjectCreationDescriptionsQueryVariables {
  editingContextId: string;
  domainId: string;
  suggested: boolean;
}

export interface GQLGetRootObjectCreationDescriptionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  rootObjectCreationDescriptions: GQLChildCreationDescription[];
  domains: GQLDomain[];
}

export interface GQLChildCreationDescription {
  id: string;
  label: string;
}

export interface GQLDomain {
  id: string;
  label: string;
}

export interface GQLCreateRootObjectMutationData {
  createRootObject: GQLCreateRootObjectPayload;
}

export interface GQLCreateRootObjectPayload {
  __typename: string;
}

export interface GQLCreateRootObjectSuccessPayload extends GQLCreateRootObjectPayload {
  id: string;
  object: GQLObject;
}

export interface GQLObject {
  id: string;
  label: string;
  kind: string;
}

export interface GQLErrorPayload extends GQLCreateRootObjectPayload {
  message: string;
}

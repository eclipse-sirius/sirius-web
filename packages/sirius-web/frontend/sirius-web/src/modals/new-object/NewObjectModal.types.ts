/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';

export interface NewObjectModalProps {
  editingContextId: string;
  item: any;
  onObjectCreated: (object: Selection) => void;
  onClose: () => void;
}

export interface ChildCreationDescription {
  id: string;
  label: string;
}

export interface GQLGetChildCreationDescriptionsQueryVariables {
  editingContextId: string;
  kind: string;
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
}

export interface GQLCreateChildMutationData {
  createChild: GQLCreateChildPayload;
}

export interface GQLCreateChildPayload {
  __typename: string;
}

export interface GQLCreateChildSuccessPayload extends GQLCreateChildPayload {
  id: string;
  object: GQLObject;
}

export interface GQLObject {
  id: string;
  label: string;
  kind: string;
}

export interface GQLErrorPayload extends GQLCreateChildPayload {
  message: string;
}

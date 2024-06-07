/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { RepresentationMetadata } from '@eclipse-sirius/sirius-components-core';

export interface NewRepresentationAreaState {
  message: string;
}

export interface RepresentationDescriptionMetadata {
  id: string;
  label: string;
  defaultName: string;
}

export interface NewRepresentationAreaProps {
  editingContextId: string;
  representationDescriptions: RepresentationDescriptionMetadata[];
  readOnly: boolean;
}

export interface GQLCreateRepresentationPayload {
  __typename: string;
  representation: RepresentationMetadata;
}

export interface GQLErrorPayload extends GQLCreateRepresentationPayload {
  message: string;
}

export interface GQLCreateRepresentationInput {
  id: string;
  editingContextId: string;
  objectId: string;
  representationDescriptionId: string;
  representationName: string;
}
export interface GQLCreateRepresentationData {
  createRepresentation: GQLCreateRepresentationPayload;
}
export interface GQLCreateRepresentationVariables {
  input: GQLCreateRepresentationInput;
}

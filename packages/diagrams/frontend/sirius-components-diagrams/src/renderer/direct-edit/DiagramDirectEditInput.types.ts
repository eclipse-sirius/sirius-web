/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { GQLMessage } from '../Tool.types';

export interface DiagramDirectEditInputProps {
  labelId: string;
  editingKey: string | null;
  onClose: () => void;
}

export interface DiagramDirectEditInputState {
  newLabel: string;
}

export interface GQLInitialDirectEditElementLabelInput {
  editingContextId: string;
  diagramId: string;
  labelId: string;
}

export interface GQLInitialDirectEditElementLabelData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
  initialDirectEditElementLabel: string;
}

export interface GQLRenameElementPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLRenameElementPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLRenameElementPayload {
  messages: GQLMessage[];
}

export interface GQLEditLabelData {
  editLabel: GQLEditLabelPayload;
}

export interface GQLEditLabelPayload {
  __typename: string;
}

export interface GQLEditLabelVariables {
  input: GQLEditLabelInput;
}

export interface GQLEditLabelInput {
  id: string;
  editingContextId: string;
  representationId: string;
  labelId: string;
  newText: string;
}

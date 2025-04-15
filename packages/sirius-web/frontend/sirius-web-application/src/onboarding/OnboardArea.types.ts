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
export interface OnboardAreaState {
  editingContextActions: GQLEditingContextAction[];
  representationDescriptions: GQLRepresentationDescriptionMetadata[];
}

export interface GQLData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLEditingContextAction {
  id: string;
  label: string;
}

export interface GQLRepresentationDescriptionMetadata {
  id: string;
  label: string;
  defaultName: string;
}

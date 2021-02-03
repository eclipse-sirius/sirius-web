/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
export type Visibility = 'PUBLIC' | 'PRIVATE';
export type AccessLevel = 'READ' | 'WRITE' | 'ADMIN';

export type EditingContext = {
  id: string;
};

export type Project = {
  id: string;
  name: string;
  visibility: Visibility;
  accessLevel: AccessLevel;
  currentEditingContext: EditingContext;
};

export type GQLRepresentation = {
  __typename: string;
  id: string;
  label: string;
};

export type GQLEditingContext = {
  id: string;
};

export type GQLProject = {
  id: string;
  name: string;
  visibility: Visibility;
  accessLevel: AccessLevel;
  currentEditingContext: GQLEditingContext;
  representation: GQLRepresentation | undefined;
};

export type GQLViewer = {
  project: GQLProject;
};

export type GQLGetProjectQueryData = {
  viewer: GQLViewer;
};

export type GQLGetProjectQueryVariables = {
  projectId: string;
  representationId: string;
  includeRepresentation: boolean;
};

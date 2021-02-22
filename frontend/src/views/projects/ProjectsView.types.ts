/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
export type AccessLevel = 'READ' | 'WRITE' | 'ADMIN';

export interface Project {
  id: string;
  name: string;
  accessLevel: AccessLevel;
}

export interface GQLGetProjectsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  projects: GQLProject[];
}

export interface GQLProject {
  id: string;
  name: string;
  accessLevel: AccessLevel;
}

export interface GQLGetProjectsQueryVariables {}

export interface ProjectsTableProps {
  projects: Project[];
  onMore: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, project: Project) => void;
}

export interface ProjectContextMenuProps {
  menuAnchor: HTMLElement;
  project: Project;
  onClose: () => void;
  onRename: () => void;
  onDelete: () => void;
}

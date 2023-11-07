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
export interface Project {
  id: string;
  name: string;
}

export interface ProjectTemplate {
  id: string;
  label: string;
  imageURL: string;
}

export interface GQLGetProjectsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  projects: GQLViewerProjectConnection;
  projectTemplates: GQLViewerProjectTemplateConnection;
}

export interface GQLViewerProjectTemplateConnection {
  edges: GQLViewerProjectTemplateEdge[];
  pageInfo: GQLPageInfo;
}
export interface GQLViewerProjectTemplateEdge {
  node: GQLProjectTemplate;
}

export interface GQLProjectTemplate {
  id: string;
  label: string;
  imageURL: string;
}

export interface GQLViewerProjectConnection {
  edges: GQLViewerProjectEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLPageInfo {
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  count: number;
}
export interface GQLViewerProjectEdge {
  node: GQLProject;
}

export interface GQLProject {
  id: string;
  name: string;
}

export interface GQLGetProjectsQueryVariables {
  page: number;
}

export interface GQLCreateProjectFromTemplateMutationData {
  createProjectFromTemplate: GQLCreateProjectFromTemplatePayload;
}

export interface GQLCreateProjectFromTemplatePayload {
  __typename: string;
}

export interface GQLCreateProjectFromTemplateSuccessPayload extends GQLCreateProjectFromTemplatePayload {
  project: GQLProjectCreatedFromTemplate;
  representationToOpen: GQLRepresentationToOpen;
}

export interface GQLProjectCreatedFromTemplate {
  id: string;
}

export interface GQLRepresentationToOpen {
  id: string;
}

export interface GQLErrorPayload extends GQLCreateProjectFromTemplatePayload {
  message: string;
}

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

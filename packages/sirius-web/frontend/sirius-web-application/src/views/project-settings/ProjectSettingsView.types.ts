/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export interface ProjectSettingsParams {
  projectId: string;
}

export interface ProjectSettingTabContribution {
  id: string;
  title: string;
  icon: React.ReactElement;
  component: (props: ProjectSettingTabProps) => JSX.Element | null;
}

export interface ProjectSettingTabProps {}

export interface ProjectSettingsViewState {
  selectedTabId: string | null;
}

export interface GQLGetProjectVariables {
  projectId: string;
}

export interface GQLGetProjectData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject | null;
}

export interface GQLProject {
  id: string;
  name: string;
}

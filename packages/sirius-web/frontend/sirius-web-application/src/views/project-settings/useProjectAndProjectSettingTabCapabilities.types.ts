/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export interface UseProjectAndProjectSettingTabCapabilitiesValue {
  loading: boolean;
  data: GQLGetProjectAndProjectSettingsTabsCapabilitiesData | null;
}

export interface GQLGetProjectAndProjectSettingsTabsCapabilitiesData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject | null;
}

export interface GQLProject {
  id: string;
  name: string;
  capabilities: GQLProjectCapabilities;
}

export interface GQLProjectCapabilities {
  settings: GQLProjectSettingsCapabilities;
}

export interface GQLProjectSettingsCapabilities {
  canView: boolean;
  tabs: GQLProjectSettingsTabCapabilities[];
}

export interface GQLProjectSettingsTabCapabilities {
  tabId: string;
  canView: boolean;
}

export interface GQLGetProjectAndProjectSettingsTabsCapabilitiesVariables {
  projectId: string;
  tabIds: string[];
}

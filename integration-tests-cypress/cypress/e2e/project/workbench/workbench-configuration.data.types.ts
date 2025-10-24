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

export interface WorkbenchConfiguration {
  mainPanel: WorkbenchMainPanelConfiguration | null;
  workbenchPanels: WorkbenchSidePanelConfiguration[];
}

export interface WorkbenchSidePanelConfiguration {
  id: string;
  isOpen: boolean;
  views: WorkbenchViewConfiguration[];
}

export interface WorkbenchViewConfiguration {
  id: string;
  isActive: boolean;
}

export interface WorkbenchMainPanelConfiguration {
  id: string;
  representationEditors: WorkbenchRepresentationEditorConfiguration[];
}

export interface WorkbenchRepresentationEditorConfiguration {
  representationId: string;
  isActive: boolean;
}

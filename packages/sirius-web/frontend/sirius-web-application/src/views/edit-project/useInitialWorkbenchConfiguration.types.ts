/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { WorkbenchConfiguration } from '@eclipse-sirius/sirius-components-core';

export interface UseInitialWorkbenchConfigurationValue {
  workbenchConfiguration: WorkbenchConfiguration | null;
}

export interface UseInitialWorkbenchConfigurationState {
  workbenchConfiguration: WorkbenchConfiguration | null;
}

/*
 * Following types are defined only because users can enter anything in the URL,
 * thus, we need to handle every things they can do with it like adding nullable values
 * or providing incomplete data.
 */
export interface WorkbenchConfigurationPatch {
  mainPanel: WorkbenchMainPanelConfigurationPatch | null | undefined;
  workbenchPanels: WorkbenchSidePanelConfigurationPatch[] | null | undefined;
}

export interface WorkbenchSidePanelConfigurationPatch {
  id: string | null | undefined;
  isOpen: boolean | null | undefined;
  views: (WorkbenchViewConfigurationPatch & Record<string, any>)[] | null | undefined;
}

export interface WorkbenchViewConfigurationPatch {
  id: string | null | undefined;
  isActive: boolean | null | undefined;
}

export interface WorkbenchMainPanelConfigurationPatch {
  id: string | null | undefined;
  representationEditors: WorkbenchRepresentationEditorConfigurationPatch[] | null | undefined;
}

export interface WorkbenchRepresentationEditorConfigurationPatch {
  representationId: string | null | undefined;
  isActive: boolean | null | undefined;
}

/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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

import { GQLProjectTemplate } from '../project-browser/create-projects-area/useProjectTemplates.types';

export interface NewProjectViewState {
  name: string;
  pristineName: boolean;
  availableTemplates: GQLProjectTemplate[] | null;
  templateSelectionOpen: boolean;
  librariesImportOpen: boolean;
  selectedTemplateId: string | null;
  selectedLibraries: string[];
}

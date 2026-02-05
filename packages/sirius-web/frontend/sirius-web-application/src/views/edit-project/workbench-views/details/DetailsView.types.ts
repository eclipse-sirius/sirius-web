/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import { WorkbenchViewConfiguration } from '@eclipse-sirius/sirius-components-core';
import { GQLForm } from '@eclipse-sirius/sirius-components-forms';

export interface DetailsViewState {
  form: GQLForm | null;
  canEdit: boolean;
  objectIds: string[];
  pinned: boolean;
}

export interface DetailsViewConfiguration extends WorkbenchViewConfiguration {
  selectedPageLabel: string;
}

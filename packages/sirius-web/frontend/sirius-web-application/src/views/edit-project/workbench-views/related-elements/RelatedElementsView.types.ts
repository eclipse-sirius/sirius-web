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

import {
  Selection,
  WorkbenchViewConfiguration,
  WorkbenchViewConfigurationSupplier,
} from '@eclipse-sirius/sirius-components-core';
import { GQLForm } from '@eclipse-sirius/sirius-components-forms';

export interface RelatedElementsViewState {
  currentSelection: Selection;
  form: GQLForm | null;
}
export interface RelatedElementsViewConfigurationSupplier extends WorkbenchViewConfigurationSupplier {
  getWorkbenchViewConfiguration: () => RelatedElementsViewConfiguration | null;
}

export interface RelatedElementsViewConfiguration extends WorkbenchViewConfiguration {}

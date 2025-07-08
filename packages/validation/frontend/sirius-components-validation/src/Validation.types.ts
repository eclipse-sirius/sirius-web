/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo and others.
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
import { WorkbenchViewConfiguration, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { GQLValidation } from './useValidationViewSubscription.types';
export interface ValidationRepresentationState {
  validationPayload: GQLValidation | null;
}
export interface ValidationViewHandle extends WorkbenchViewHandle {
  getWorkbenchViewConfiguration: () => ValidationViewConfiguration | null;
}

export interface ValidationViewConfiguration extends WorkbenchViewConfiguration {}

/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseUpdateProjectStyleCustomizationStateValue {
  updateProjectStyleCustomizationState: (
    projectId: string,
    styleCustomizationDescriptionId: string,
    enable: boolean
  ) => void;
  updateSuccess: boolean;
}

export interface GQLUpdateProjectStyleCustomizationStateVariables {
  input: GQLUpdateProjectStyleCustomizationStateInput;
}

export interface GQLUpdateProjectStyleCustomizationStateInput {
  id: string;
  projectId: string;
  styleCustomizationDescriptionId: string;
  enable: boolean;
}

export interface GQLUpdateProjectStyleCustomizationStateData {
  updateProjectStyleCustomizationState: GQLUpdateProjectStyleCustomizationStatePayload;
}

export type GQLUpdateProjectStyleCustomizationStatePayload = GQLErrorPayload | GQLSuccessPayload;

/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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

export interface ValidationWebSocketContainerProps {
  editingContextId: string;
}

export interface GQLValidationEventSubscription {
  validationEvent: GQLValidationEventPayload;
}

export interface GQLValidationEventPayload {
  __typename: string;
}

export interface GQLValidationRefreshedEventPayload extends GQLValidationEventPayload {
  id: string;
  validation: GQLValidation;
}

export interface GQLValidation {
  id: string;
  diagnostics: GQLDiagnostic[];
}

export interface GQLDiagnostic {
  id: string;
  kind: string;
  message: string;
}

export interface Validation {
  categories: Category[];
}
export interface Category {
  kind: string;
  diagnostics: Diagnostic[];
}

export interface Diagnostic {
  id: string;
  message: string;
}

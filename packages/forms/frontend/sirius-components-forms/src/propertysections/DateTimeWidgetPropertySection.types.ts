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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface DateTimeStyleProps {
  foregroundColor: string | null;
  backgroundColor: string | null;
  italic: boolean | null;
  bold: boolean | null;
}

export interface DataTimeWidgetPropertySectionState {
  editedValue: string;
}

export interface GQLEditDateTimeMutationData {
  editDateTime: GQLEditDataTimePayload;
}

export type GQLEditDataTimePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditDateTimeMutationVariables {
  input: GQLEditDateTimeInput;
}

export interface GQLEditDateTimeInput {
  id: string;
  editingContextId: string;
  representationId: string;
  widgetId: string;
  newValue: string;
}

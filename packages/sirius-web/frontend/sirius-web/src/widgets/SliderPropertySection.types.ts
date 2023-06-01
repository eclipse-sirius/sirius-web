/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { GQLMessage } from '@eclipse-sirius/sirius-components-forms/src';

export interface GQLEditSliderMutationData {
  editSlider: GQLEditSliderPayload;
}
export interface GQLEditSliderMutationVariables {
  input: GQLEditSliderInput;
}

export interface GQLEditSliderInput {
  id: string;
  editingContextId: string;
  representationId: string;
  sliderId: string;
  newValue: number;
}

export interface GQLEditSliderPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLEditSliderPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLEditSliderPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationVariables {
  input: GQLUpdateWidgetFocusInput;
}

export interface GQLUpdateWidgetFocusInput {
  id: string;
  editingContextId: string;
  representationId: string;
  widgetId: string;
  selected: boolean;
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

export interface GQLUpdateWidgetFocusSuccessPayload extends GQLUpdateWidgetFocusPayload {}

/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { GQLSelect, GQLSubscriber } from '../form/FormEventFragments.types';

export interface SelectPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLSelect;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditSelectMutationData {
  editSelect: GQLEditSelectPayload;
}

export interface GQLEditSelectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditSelectPayload, GQLUpdateWidgetFocusPayload {
  message: string;
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

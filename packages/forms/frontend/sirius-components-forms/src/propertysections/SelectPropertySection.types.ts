/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';
import { GQLWidgetGridLayout } from '../form/FormEventFragments.types';

export interface SelectStyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
  gridLayout: GQLWidgetGridLayout | null;
}

export interface GQLEditSelectMutationData {
  editSelect: GQLEditSelectPayload;
}

export interface GQLEditSelectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditSelectPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditSelectPayload {
  messages: GQLMessage[];
}

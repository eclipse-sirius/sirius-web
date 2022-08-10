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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLList, GQLSubscriber } from '../form/FormEventFragments.types';

export interface ListPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLList;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
  setSelection: (selection: Selection) => void;
}

export interface GQLDeleteListItemMutationData {
  deleteListItem: GQLDeleteListItemPayload;
}

export interface GQLDeleteListItemPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDeleteListItemPayload {
  message: string;
}

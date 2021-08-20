/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { List, Subscriber } from 'form/Form.types';
import { Selection } from 'workbench/Workbench.types';

export interface ListPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: List;
  subscribers: Subscriber[];
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

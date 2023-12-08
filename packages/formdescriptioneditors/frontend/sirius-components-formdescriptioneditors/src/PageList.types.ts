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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLFormDescriptionEditor } from './FormDescriptionEditorEventFragment.types';
import { GQLPage } from '@eclipse-sirius/sirius-components-forms/src';

export interface PageListProps {
  editingContextId: string;
  representationId: string;
  formDescriptionEditor: GQLFormDescriptionEditor;
  selection: Selection;
  setSelection: (selection: Selection) => void;
}

export interface PageListState {
  selectedPage: GQLPage | undefined;
  pages: GQLPage[];
}

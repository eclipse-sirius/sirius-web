/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
import { GQLForm } from '../form/FormEventFragments.types';

export interface FormBasedViewProps {
  editingContextId: string;
  form: GQLForm;
  initialSelectedPageLabel: string | null;
  readOnly: boolean;
  postProcessor?: (editingContextId: string, form: GQLForm, readOnly: boolean) => JSX.Element;
}

export interface FormConverter {
  convert(editingContextId: string, form: GQLForm): GQLForm;
}

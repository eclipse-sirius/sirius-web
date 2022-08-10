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
import { GQLPage, GQLWidgetSubscription } from '../form/FormEventFragments.types';

export interface PageProps {
  editingContextId: string;
  formId: string;
  page: GQLPage;
  widgetSubscriptions: GQLWidgetSubscription[];
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

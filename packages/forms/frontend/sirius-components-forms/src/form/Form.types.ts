/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { GQLForm, GQLPage, GQLSubscriber, GQLWidget, GQLWidgetSubscription } from './FormEventFragments.types';

export interface FormProps {
  editingContextId: string;
  form: GQLForm;
  widgetSubscriptions: GQLWidgetSubscription[];
  readOnly: boolean;
  setSelection: (selection: Selection) => void;
}

export interface FormState {
  selectedPage: GQLPage | null;
  pages: GQLPage[];
}

export type PropertySectionComponentProps<W extends GQLWidget> = {
  editingContextId: string;
  formId: string;
  widget: W;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
};

export type PropertySectionComponent<W extends GQLWidget> = (
  props: PropertySectionComponentProps<W>
) => JSX.Element | null;

export interface WidgetContribution {
  name: string;
  fields: string;
  icon: JSX.Element;
}

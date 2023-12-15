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
import { GQLLabelWidget, GQLSubscriber } from '../form/FormEventFragments.types';

export interface LabelStyleProps {
  color: string | undefined;
  fontSize: number | undefined;
  italic: boolean | undefined;
  bold: boolean | undefined;
  underline: boolean | undefined;
  strikeThrough: boolean | undefined;
}

export interface LabelWidgetPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLLabelWidget;
  subscribers: GQLSubscriber[];
}

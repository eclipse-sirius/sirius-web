/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import {
  GQLContainerBorderStyle,
  GQLFlexboxAlignItems,
  GQLFlexboxContainer,
  GQLFlexboxJustifyContent,
  GQLFlexDirection,
  GQLFlexWrap,
  GQLWidgetSubscription,
} from '../form/FormEventFragments.types';

export interface FlexboxContainerPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLFlexboxContainer;
  widgetSubscriptions: GQLWidgetSubscription[];
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

export interface FlexboxContainerPropertySectionStyleProps {
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  borderStyle: GQLContainerBorderStyle | null;
  justifyContent: GQLFlexboxJustifyContent;
  alignItems: GQLFlexboxAlignItems;
  margin: string | null;
  padding: string | null;
  gap: string | null;
}

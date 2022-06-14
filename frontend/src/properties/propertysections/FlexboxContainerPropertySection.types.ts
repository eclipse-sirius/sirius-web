/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { FlexboxContainer, FlexDirection, FlexWrap, WidgetSubscription } from 'form/Form.types';
import { Selection } from 'workbench/Workbench.types';

export interface FlexboxContainerPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: FlexboxContainer;
  widgetSubscriptions: WidgetSubscription[];
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

export interface FlexboxContainerPropertySectionStyleProps {
  flexDirection: FlexDirection;
  flexWrap: FlexWrap;
  flexGrow: number;
}

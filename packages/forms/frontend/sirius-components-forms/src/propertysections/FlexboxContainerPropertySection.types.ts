/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import {
  GQLContainerBorderStyle,
  GQLFlexboxContainer,
  GQLFlexDirection,
  GQLFlexWrap,
} from '../form/FormEventFragments.types';

export interface FlexboxContainerPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLFlexboxContainer;
  readOnly: boolean;
}

export interface FlexboxContainerPropertySectionStyleProps {
  flexDirection: GQLFlexDirection;
  flexWrap: GQLFlexWrap;
  flexGrow: number;
  borderStyle: GQLContainerBorderStyle | null;
}

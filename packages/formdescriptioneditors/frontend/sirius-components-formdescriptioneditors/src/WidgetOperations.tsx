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
import { GQLGroup, GQLWidget } from '@eclipse-sirius/sirius-components-forms';
import { Kind } from './FormDescriptionEditorRepresentation.types';

export const isKind = (value: string): value is Kind => {
  return (
    value === 'Textfield' ||
    value === 'TextArea' ||
    value === 'Checkbox' ||
    value === 'Radio' ||
    value === 'Select' ||
    value === 'MultiSelect' ||
    value === 'Button' ||
    value === 'Label' ||
    value === 'Link' ||
    value === 'List' ||
    value === 'Tree' ||
    value === 'BarChart' ||
    value === 'PieChart' ||
    value === 'FlexboxContainer' ||
    value === 'Image' ||
    value === 'RichText' ||
    value === 'FormElementFor' ||
    value === 'FormElementIf'
  );
};

export const isGroup = (element: GQLWidget | GQLGroup): boolean => {
  return element.__typename === 'Group';
};

export const isFlexboxContainer = (element: GQLWidget | GQLGroup): boolean => {
  return element.__typename === 'FlexboxContainer';
};

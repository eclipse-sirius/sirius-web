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
    value === 'BarChart' ||
    value === 'PieChart' ||
    value === 'FlexboxContainer'
  );
};

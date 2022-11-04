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
import { GQLAbstractFormDescriptionEditorWidget } from './FormDescriptionEditorEventFragment.types';

export const readStyleProperty = (widget: GQLAbstractFormDescriptionEditorWidget, name: string): string => {
  return widget.styleProperties.find((prop) => prop.name === name)?.value;
};

export const readBooleanStyleProperty = (widget: GQLAbstractFormDescriptionEditorWidget, name: string): boolean => {
  return readStyleProperty(widget, name) === 'true';
};

export const readNumericStyleProperty = (widget: GQLAbstractFormDescriptionEditorWidget, name: string): number => {
  const rawStyle = readStyleProperty(widget, name);
  return rawStyle ? Number(rawStyle) : undefined;
};

export const getTextDecorationLineValue = (underline: boolean | null, strikeThrough: boolean | null): string => {
  let value: string = 'inherit';
  if (underline) {
    if (strikeThrough) {
      value = 'underline line-through';
    } else {
      value = 'underline';
    }
  } else if (strikeThrough) {
    if (underline) {
      value = 'underline line-through';
    } else {
      value = 'line-through';
    }
  }
  return value;
};

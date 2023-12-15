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
export const getTextDecorationLineValue = (
  underline: boolean | null | undefined,
  strikeThrough: boolean | null | undefined
): string => {
  let value: string | undefined = null;
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

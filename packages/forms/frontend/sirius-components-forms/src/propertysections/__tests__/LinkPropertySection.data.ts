/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLLink } from '../../form/FormEventFragments.types';

export const link: GQLLink = {
  __typename: 'Label',
  id: 'labelId',
  label: 'Google',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  url: 'https://www.google.com',
  readOnly: false,
  style: {
    color: null,
    bold: null,
    fontSize: null,
    italic: null,
    strikeThrough: null,
    underline: null,
  },
};

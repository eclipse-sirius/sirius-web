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
import { GQLImage } from '../../form/FormEventFragments.types';

export const imageWithMaxWidth: GQLImage = {
  label: 'myImage',
  url: 'https://www.eclipse.org/sirius/common_assets/images/logos/logo_sirius.png',
  maxWidth: '42',
  iconURL: [],
  hasHelpText: false,
  __typename: 'Image',
  diagnostics: [],
  id: 'imageId',
  readOnly: false,
};

export const imageWithNoMaxWidth: GQLImage = {
  label: 'myImage',
  url: 'https://www.eclipse.org/sirius/common_assets/images/logos/logo_sirius.png',
  maxWidth: '',
  iconURL: [],
  hasHelpText: false,
  __typename: 'Image',
  diagnostics: [],
  id: 'imageId',
  readOnly: false,
};

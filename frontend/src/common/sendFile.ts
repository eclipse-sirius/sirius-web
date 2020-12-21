/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { httpOrigin } from './URL';

export const sendFile = async (query: string, variables: any, file: File) => {
  const operations = {
    query,
    variables,
  };

  const formData = new FormData();
  formData.append('operations', JSON.stringify(operations));
  formData.append('map', JSON.stringify({ '0': 'variables.file' }));
  formData.append('0', file);

  const response = await fetch(`${httpOrigin}/api/graphql/upload`, {
    method: 'POST',
    body: formData,
    credentials: 'include',
    mode: 'cors',
  });

  return await response.json();
};

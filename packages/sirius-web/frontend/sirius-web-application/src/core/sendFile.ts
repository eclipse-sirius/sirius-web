/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

export const sendFile = async (httpOrigin: string, query: string, variables: any, file: File): Promise<any> => {
  const operations = {
    query,
    variables,
  };

  const formData = new FormData();
  formData.append('operations', JSON.stringify(operations));
  formData.append('map', JSON.stringify({ '0': 'variables.file' }));
  formData.append('0', file);

  const csrfToken = getCookie('XSRF-TOKEN');

  const response = await fetch(`${httpOrigin}/api/graphql/upload`, {
    method: 'POST',
    body: formData,
    credentials: 'include',
    mode: 'cors',
    headers: {
      'X-XSRF-TOKEN': csrfToken,
    },
  });

  return await response.json();
};

const getCookie = (name: string): string => {
  let cookieValue: string = null;
  if (document.cookie && document.cookie !== '') {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
      const cookie = cookies[i].trim();
      // Does this cookie string begin with the name we want?
      if (cookie.substring(0, name.length + 1) === name + '=') {
        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
        break;
      }
    }
  }
  return cookieValue;
};

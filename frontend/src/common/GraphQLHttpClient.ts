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

export class GraphQLHttpClient {
  private url;
  constructor(url) {
    this.url = url;
  }

  async send(query, variables, operationName) {
    const request = { query, variables, operationName };
    const requestBody = JSON.stringify(request);
    const response = await fetch(this.url, {
      method: 'POST',
      body: requestBody,
      credentials: 'include',
      mode: 'cors',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
    if (response.ok) {
      return {
        ok: true,
        body: await response.json(),
      };
    } else if (response.status === 401) {
      return {
        ok: false,
        error: response.status,
      };
    } else {
      return {
        ok: false,
        error: response.statusText,
      };
    }
  }

  async sendFile(query, variables, file) {
    const operations = {
      query,
      variables,
    };

    const formData = new FormData();
    formData.append('operations', JSON.stringify(operations));
    formData.append('map', JSON.stringify({ '0': 'variables.file' }));
    formData.append('0', file);

    const response = await fetch(`${this.url}/upload`, {
      method: 'POST',
      body: formData,
      credentials: 'include',
      mode: 'cors',
    });

    const responseBody = await response.json();
    return responseBody;
  }
}

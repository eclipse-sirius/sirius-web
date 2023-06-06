/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import http, { RequestListener } from 'http';

export const startHttpServer = () => {
  const requestListener: RequestListener = (req, res) => {
    res.setHeader('Access-Control-Allow-Origin', '*');

    switch (req.url) {
      case '/api/graphql':
        graphQLRequestListener(req, res);
        break;
      default:
        res.writeHead(404);
        res.end(JSON.stringify({ error: 'Resource not found' }));
        break;
    }
  };

  const server = http.createServer(requestListener);
  server.listen(8081);
  console.info('The HTTP server is up and running on 8081');
};

const graphQLRequestListener: RequestListener = (req, res) => {
  const body = {
    nodes: [
      {
        id: 'A',
        type: 'group',
        position: { x: 0, y: 0 },
        style: {
          width: 170,
          height: 140,
        },
      },
      {
        id: 'A-1',
        type: 'input',
        data: { label: 'Child Node 1' },
        position: { x: 10, y: 10 },
        parentNode: 'A',
        extent: 'parent',
      },
      {
        id: 'A-2',
        data: { label: 'Child Node 2' },
        position: { x: 10, y: 90 },
        parentNode: 'A',
        extent: 'parent',
      },
      {
        id: 'B',
        type: 'output',
        position: { x: -100, y: 200 },
        data: { label: 'Node B' },
      },
      {
        id: 'C',
        type: 'output',
        position: { x: 100, y: 200 },
        data: { label: 'Node C' },
      },
    ],
    edges: [
      { id: 'a1-a2', source: 'A-1', target: 'A-2' },
      { id: 'a2-b', source: 'A-2', target: 'B' },
      { id: 'a2-c', source: 'A-2', target: 'C' },
    ],
  };

  res.end(JSON.stringify(body));
};

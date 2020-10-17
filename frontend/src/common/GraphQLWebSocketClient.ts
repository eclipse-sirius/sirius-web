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
export class GraphQLWebSocketClient {
  private id;
  private url;
  private operationIdToCallback;
  private waitingSendRequests;
  private webSocket;

  constructor(url) {
    this.id = 0;
    this.url = url;
    this.operationIdToCallback = new Map();
    this.waitingSendRequests = [];
  }

  generateOperationId() {
    this.id = this.id + 1;
    return this.id.toString();
  }

  start(operationId, query, variables, operationName) {
    const sendRequest = () => {
      const request = JSON.stringify({
        type: 'start',
        id: operationId,
        payload: {
          query,
          variables,
          operationName,
        },
      });
      this.webSocket.send(request);
    };
    const sendWaitingRequests = () => {
      while (this.waitingSendRequests.length > 0) {
        this.waitingSendRequests.pop()();
      }
    };
    sendWaitingRequests.bind(this);

    if (!this.webSocket) {
      this.webSocket = new WebSocket(this.url, 'graphql-ws');
      this.webSocket.onmessage = (message) => {
        const data = JSON.parse(message.data);
        const { id } = data;
        const callback = this.operationIdToCallback.get(id);
        if (callback) {
          callback(data);
        }
      };

      this.webSocket.onopen = sendWaitingRequests;
      this.waitingSendRequests = [sendRequest];
    } else {
      if (this.webSocket.readyState !== 1) {
        // The connection is not already. Add the request in
        this.waitingSendRequests.push(sendRequest);
      } else {
        sendRequest();
      }
    }
  }

  stop(id) {
    const request = JSON.stringify({
      type: 'stop',
      id,
    });
    this.webSocket.send(request);
  }

  on(id, callback) {
    this.operationIdToCallback.set(id, callback);
  }

  remove(id) {
    this.operationIdToCallback.delete(id);
  }
}

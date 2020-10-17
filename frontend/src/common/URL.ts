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

let httpURL = '';
let httpServerPORT = process.env.REACT_APP_HTTP_SERVER_PORT;
if (!httpServerPORT || httpServerPORT.length === 0) {
  httpURL = `${window.location.protocol}//${window.location.host}`;
} else {
  httpURL = `${window.location.protocol}//${window.location.hostname}:${httpServerPORT}`;
}
export const httpOrigin = httpURL;

let wsURL = '';
let wsServerPORT = process.env.REACT_APP_WS_SERVER_PORT;
if (!wsServerPORT || wsServerPORT.length === 0) {
  let wsProtocol = 'ws:';
  if ('https:' === window.location.protocol) {
    wsProtocol = 'wss:';
  }
  wsURL = `${wsProtocol}//${window.location.host}`;
} else {
  wsURL = `ws://${window.location.hostname}:${wsServerPORT}`;
}
export const wsOrigin = wsURL;

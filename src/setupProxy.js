/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

/*
  This file is used in the development environment in order to configure the proxy.
  It is not in any way, shape or form release along the application. The content of
  this file will be executed on the node server started by CRA. This file does not
  have access to the browser or the DOM.
*/

const proxy = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(proxy('/api', { target: 'http://localhost:8080/' }));
};

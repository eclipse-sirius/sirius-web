/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
const { defineConfig } = require('cypress');

module.exports = defineConfig({
  screenshotsFolder: 'target/screenshots',
  video: false,
  reporter: 'junit',
  reporterOptions: {
    mochaFile: 'target/result-[hash].xml',
    toConsole: true,
  },
  viewportWidth: 1920,
  viewportHeight: 1080,
  env: {
    baseAPIUrl: 'http://localhost:8080',
  },
  e2e: {
    setupNodeEvents(on, config) {
      return require('./cypress/plugins/index.js')(on, config);
    },
    baseUrl: 'http://localhost:5173',
    experimentalRunAllSpecs: true,
  },
});

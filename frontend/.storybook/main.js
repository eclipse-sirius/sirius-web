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
const path = require('path');

module.exports = {
  stories: ['../src/**/*.stories.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: ['@storybook/addon-actions/register', '@storybook/addon-links', '@storybook/addon-essentials'],

  webpackFinal: async (config, { configType }) => {
    // get index of css rule
    const ruleCssIndex = config.module.rules.findIndex((rule) => rule.test.toString() === '/\\.css$/');

    // map over the 'use' array of the css rule and set the 'module' option to true
    config.module.rules[ruleCssIndex].use.map((item) => {
      if (item.loader && item.loader.includes('/css-loader/')) {
        item.options.modules = {
          mode: 'local',
          localIdentName: configType === 'PRODUCTION' ? '[local]--[hash:base64:5]' : '[name]__[local]--[hash:base64:5]',
        };
      }
      return item;
    });
    const ruleFileIndex = config.module.rules.findIndex((rule) => rule.test.toString().includes('svg'));
    const rule = config.module.rules[ruleFileIndex];
    rule.loader = 'svg-url-loader';
    // Return the altered config
    return config;
  },
};

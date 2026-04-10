/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { createRequire } from "module";
import { dirname, join, resolve } from "path";
import { fileURLToPath } from "url";
import { mergeConfig } from "vite";

const __dirname = dirname(fileURLToPath(import.meta.url));
const require = createRequire(import.meta.url);

function getAbsolutePath(value) {
  return dirname(require.resolve(join(value, "package.json")));
}

const config = {
  stories: ["../src/**/*.mdx", "../src/**/*.stories.@(js|jsx|mjs|ts|tsx)"],
  addons: [
    getAbsolutePath("@storybook/addon-vitest"),
    getAbsolutePath("@storybook/addon-a11y"),
    getAbsolutePath("@storybook/addon-docs"),
  ],
  framework: {
    name: getAbsolutePath("@storybook/react-vite"),
    options: {},
  },
  typescript: {
    reactDocgen: "react-docgen-native",
  },
  async viteFinal(config) {
    return mergeConfig(config, {
      server: {
        fs: {
          allow: [resolve(__dirname, "../../../")],
        },
      },
      optimizeDeps: {
        include: [
          "@eclipse-sirius/sirius-web-application",
          "@eclipse-sirius/sirius-components-core",
          "@eclipse-sirius/sirius-components-diagrams",
        ],
      },
      define: {
        "process.env": {},
      },
    });
  },
};

export default config;

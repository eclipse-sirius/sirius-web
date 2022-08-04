/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import commonjs from '@rollup/plugin-commonjs';
import image from '@rollup/plugin-image';
import resolve from '@rollup/plugin-node-resolve';
import { eslint } from 'rollup-plugin-eslint';
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import postcss from 'rollup-plugin-postcss';
import typescript from 'rollup-plugin-typescript2';

const packageJson = require('./package.json');

const cjs = {
  file: packageJson.main,
  format: 'cjs',
  sourcemap: true,
};

const esm = {
  file: packageJson.module,
  format: 'esm',
  sourcemap: true,
};

export default {
  input: 'src/index.ts',
  output: [cjs, esm],
  plugins: [
    peerDepsExternal(),
    resolve(),
    commonjs(),
    eslint(),
    typescript({ useTsconfigDeclarationDir: true }),
    postcss({
      modules: true,
      minimize: true,
    }),
    image(),
  ],
};

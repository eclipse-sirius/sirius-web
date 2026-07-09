import react from '@vitejs/plugin-react';
import { createRequire } from 'node:module';
import path from 'node:path';
import { defineConfig } from 'vite';

const require = createRequire(import.meta.url);
const { peerDependencies = {} } = require('./package.json');
const isExternal = (id) => Object.keys(peerDependencies).some((dependency) => id === dependency || id.startsWith(`${dependency}/`));

export default defineConfig(() => {
  const configuration = {
    plugins: [react()],
    build: {
      minify: false,
      lib: {
        name: 'sirius-components-validation',
        entry: path.resolve(__dirname, 'src/index.ts'),
        formats: ['es', 'cjs'],
        fileName: (format) => `sirius-components-validation.${format}.js`,
      },
      rollupOptions: {
        external: isExternal,
      },
    },
  };
  return configuration;
});

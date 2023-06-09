import react from '@vitejs/plugin-react';
import path from 'node:path';
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import { defineConfig } from 'vite';

export default defineConfig(() => {
  const configuration = {
    plugins: [peerDepsExternal(), react()],
    build: {
      minify: false,
      lib: {
        name: 'sirius-components-validation',
        entry: path.resolve(__dirname, 'src/index.ts'),
        formats: ['es', 'umd'],
        fileName: (format) => `sirius-components-validation.${format}.js`,
      },
    },
  };
  return configuration;
});

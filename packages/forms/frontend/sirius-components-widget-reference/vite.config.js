import react from '@vitejs/plugin-react';
import path from 'node:path';
import peerDepsExternal from 'rollup-plugin-peer-deps-external';
import { defineConfig } from 'vite';

export default defineConfig({
  plugins: [peerDepsExternal(), react()],
  build: {
    minify: false,
    lib: {
      name: 'sirius-components-widget-reference',
      entry: path.resolve(__dirname, 'src/index.ts'),
      formats: ['es', 'umd'],
      fileName: (format) => `sirius-components-widget-reference.${format}.js`,
    },
  },
  test: {
    environment: 'jsdom',
    coverage: {
      reporter: ['text', 'html'],
    },
  },
});

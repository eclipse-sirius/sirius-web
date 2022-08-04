import react from '@vitejs/plugin-react';
import path from 'node:path';
import { defineConfig } from 'vite';

export default defineConfig({
  plugins: [react()],
  build: {
    lib: {
      name: 'sirius-components-validation',
      entry: path.resolve(__dirname, 'src/index.ts'),
      formats: ['es', 'umd'],
      fileName: (format) => `sirius-components-validation.${format}.js`,
    },
    rollupOptions: {
      external: [
        '@apollo/client',
        '@eclipse-sirius/sirius-components-core',
        '@material-ui/core',
        '@material-ui/icons',
        '@material-ui/styles',
        '@xstate/react',
        'graphql',
        'react',
        'uuid',
        'xstate',
      ],
    },
  },
});

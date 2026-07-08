import path from "node:path";
import peerDepsExternal from "rollup-plugin-peer-deps-external";
import { defineConfig } from "vite";

export default defineConfig(() => {
  const configuration = {
    plugins: [peerDepsExternal()],
    build: {
      minify: false,
      lib: {
        name: "sirius-components-markdown",
        entry: path.resolve(__dirname, "src/index.ts"),
        formats: ["es", "umd"],
        fileName: (format) => `sirius-components-markdown.${format}.js`,
      },
    },
  };
  return configuration;
});

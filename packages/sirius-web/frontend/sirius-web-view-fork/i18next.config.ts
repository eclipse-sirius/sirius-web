import { defineConfig } from 'i18next-cli';
import { extract, locale } from '../../../../i18next.config.js';

export default defineConfig({
  ...locale,
  extract: {
    ...extract,
    defaultNS: 'siriusWebViewFork',
    input: 'src/**/*.{ts,tsx}',
    output: '../../backend/sirius-web-view-fork/src/main/resources/i18n/{{language}}/{{namespace}}.json',
  },
});

import { defineConfig } from 'i18next-cli';
import { extract, locale } from '../../../../i18next.config.js';

export default defineConfig({
  ...locale,
  extract: {
    ...extract,
    defaultNS: 'siriusWeb',
    input: 'src/**/*.{ts,tsx}',
    output: '../../backend/sirius-web/src/main/resources/i18n/{{language}}/{{namespace}}.json',
  },
});

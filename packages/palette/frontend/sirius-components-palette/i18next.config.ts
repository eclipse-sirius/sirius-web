import { defineConfig } from 'i18next-cli';
import { extract, locale } from '../../../../i18next.config.js';

export default defineConfig({
  ...locale,
  extract: {
    ...extract,
    defaultNS: 'siriusComponentsPalette',
    input: 'src/**/*.{ts,tsx}',
    output: '../../backend/sirius-components-collaborative/src/main/resources/i18n/{{language}}/{{namespace}}.json',
  },
});

import { defineConfig } from 'i18next-cli';
import { extract, locale } from '../../../../i18next.config.js';

export default defineConfig({
  ...locale,
  extract: {
    ...extract,
    defaultNS: 'siriusComponentsWidgetReference',
    input: 'src/**/*.{ts,tsx}',
    output:
      '../../backend/sirius-components-collaborative-widget-reference/src/main/resources/i18n/{{language}}/{{namespace}}.json',
  },
});

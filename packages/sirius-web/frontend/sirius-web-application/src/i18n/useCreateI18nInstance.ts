/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import i18next, { i18n, InitOptions } from 'i18next';
import HttpBackend, { HttpBackendOptions } from 'i18next-http-backend';
import { useMemo } from 'react';
import { initReactI18next } from 'react-i18next';

export const useCreateI18nInstance = (
  language: string | null,
  namespaces: string[],
  httpOrigin: string
): i18n | null => {
  const i18nextOptions: InitOptions<HttpBackendOptions> = {
    lng: language,
    ns: namespaces,
    fallbackLng: 'en',
    preload: [language],
    interpolation: {
      escapeValue: false,
    },
    react: {
      useSuspense: false,
    },
    backend: {
      loadPath: `${httpOrigin}/api/locales/{{lng}}/{{ns}}.json`,
      parse: (data: string) => JSON.parse(data),
    },
  };

  const i18nInstance: i18n | null = useMemo(() => {
    if (!language) {
      return null;
    }
    const instance = i18next.createInstance();
    instance.use(HttpBackend).use(initReactI18next).init(i18nextOptions);
    return instance;
  }, [httpOrigin, language, namespaces.join(',')]);

  return i18nInstance;
};

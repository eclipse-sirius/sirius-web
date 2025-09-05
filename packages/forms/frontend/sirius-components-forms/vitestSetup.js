/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import { vi } from 'vitest';
import { default as en } from './src/locales/en.json';

Object.defineProperty(globalThis, 'crypto', {
  value: {
    randomUUID: vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1'),
  },
});

i18n.use(initReactI18next).init({
  lng: 'en',
  interpolation: {
    escapeValue: false,
  },
  resources: { en },
});

export default i18n;

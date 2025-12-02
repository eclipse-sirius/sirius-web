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
import React from 'react';
import { I18nextProvider } from 'react-i18next';
import { useCreateI18nInstance } from './useCreateI18nInstance';
import { useLocale } from './useLocale';

export interface I18NContextProviderProps {
  children?: React.ReactNode;
  httpOrigin: string;
}

export const I18nContextProvider = ({ children, httpOrigin }: I18NContextProviderProps) => {
  const { language, namespaces } = useLocale();
  const { data: i18nInstance, loading } = useCreateI18nInstance(language, namespaces, httpOrigin);

  if (!i18nInstance || loading) {
    return null;
  }

  return <I18nextProvider i18n={i18nInstance}>{children}</I18nextProvider>;
};

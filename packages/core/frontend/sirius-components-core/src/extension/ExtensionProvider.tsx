/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { ExtensionContextValue, ExtensionProviderProps } from './ExtensionProvider.types';
import { ExtensionRegistry } from './ExtensionRegistry';

export const ExtensionContext = React.createContext<ExtensionContextValue>({
  registry: new ExtensionRegistry(),
});

export const ExtensionProvider = ({ children, registry }: ExtensionProviderProps) => {
  return <ExtensionContext.Provider value={{ registry }}>{children}</ExtensionContext.Provider>;
};

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

import { useContext } from 'react';
import { ExtensionContext } from './ExtensionProvider';
import { ExtensionContextValue } from './ExtensionProvider.types';
import { DataExtension, DataExtensionPoint } from './ExtensionRegistry.types';

export function useData<P>(extensionPoint: DataExtensionPoint<P>): DataExtension<P> {
  const { registry } = useContext<ExtensionContextValue>(ExtensionContext);

  const dataExtension = registry.getData(extensionPoint);
  if (dataExtension) {
    return dataExtension as DataExtension<P>;
  }
  return {
    identifier: 'fallback_' + extensionPoint.identifier,
    data: extensionPoint.fallback,
  };
}

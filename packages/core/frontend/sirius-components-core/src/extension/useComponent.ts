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
import { ComponentExtension, ComponentExtensionPoint } from './ExtensionRegistry.types';

export function useComponent<P>(extensionPoint: ComponentExtensionPoint<P>): ComponentExtension<P> {
  const { registry } = useContext<ExtensionContextValue>(ExtensionContext);
  const componentExtension = registry.findComponentById(extensionPoint);

  if (!componentExtension) {
    const componentExtension: ComponentExtension<P> = {
      identifier: 'fallback_' + extensionPoint.identifier,
      Component: extensionPoint.FallbackComponent,
    };
    return componentExtension;
  }
  return componentExtension as ComponentExtension<P>;
}

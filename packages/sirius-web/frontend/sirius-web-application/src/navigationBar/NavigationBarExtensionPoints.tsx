/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import { ComponentExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { SiriusIcon } from '../core/SiriusIcon';
import { Help } from './Help';
import { NavigationBarIconProps, NavigationBarMenuProps } from './NavigationBar.types';

const NavigationBarIcon = ({}: NavigationBarIconProps) => <SiriusIcon fontSize="large" />;
const NavigationBarMenu = ({}: NavigationBarMenuProps) => <Help />;

export const navigationBarIconExtensionPoint: ComponentExtensionPoint<NavigationBarIconProps> = {
  identifier: 'navigationBar#icon',
  FallbackComponent: NavigationBarIcon,
};

export const navigationBarMenuExtensionPoint: ComponentExtensionPoint<NavigationBarMenuProps> = {
  identifier: 'navigationBar#menu',
  FallbackComponent: NavigationBarMenu,
};

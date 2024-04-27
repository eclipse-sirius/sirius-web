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
import { ComponentExtensionPoint, DataExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { MenuItemProps } from '@mui/material/MenuItem';
import { Help } from './Help';
import { NavigationBarMenuIconProps } from './NavigationBarMenu.types';

export const navigationBarMenuIconExtensionPoint: ComponentExtensionPoint<NavigationBarMenuIconProps> = {
  identifier: 'navigationBarMenu#icon',
  FallbackComponent: Help,
};

export const navigationBarMenuHelpURLExtensionPoint: DataExtensionPoint<string> = {
  identifier: 'navigationBarMenu#helpURL',
  fallback: 'https://eclipse.dev/sirius/sirius-web.html',
};

export const navigationBarMenuEntryExtensionPoint: ComponentExtensionPoint<MenuItemProps> = {
  identifier: 'navigationBarMenu#entry',
  FallbackComponent: () => null,
};

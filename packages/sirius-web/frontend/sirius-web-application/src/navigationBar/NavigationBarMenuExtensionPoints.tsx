/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Help } from './Help';
import {
  NavigationBarMenuContainerProps,
  NavigationBarMenuIconProps,
  NavigationBarMenuItemProps,
} from './NavigationBarMenu.types';

const FallbackNavigationBarMenuContainer = ({ children }: NavigationBarMenuContainerProps) => {
  return <div>{children}</div>;
};

/**
 * Extension point for the navigation bar menu container.
 *
 * This extension point allows the contribution of a custom container for the navigation bar menu.
 * A contribution can thus define how the menu should be structured and rendered. It can be used to
 * create a custom context for the entire menu.
 *
 * @since v2024.9.0
 */
export const navigationBarMenuContainerExtensionPoint: ComponentExtensionPoint<NavigationBarMenuContainerProps> = {
  identifier: 'navigationBarMenu#container',
  FallbackComponent: FallbackNavigationBarMenuContainer,
};

/**
 * Extension point for the navigation bar menu header.
 *
 * This extension point allows the contribution of a custom header for the navigation bar menu.
 * A contribution can thus define how the header should be rendered and behave.
 * A contribution to this extension point will be rendered at the top of the menu, above all other entries.
 *
 * @since v2025.10.0
 */
export const navigationBarMenuHeaderExtensionPoint: ComponentExtensionPoint<NavigationBarMenuContainerProps> = {
  identifier: 'navigationBarMenu#header',
  FallbackComponent: () => null,
};

/**
 * Extension point for the navigation bar menu icon.
 *
 * This extension point allows the contribution of a custom icon for the navigation bar menu.
 * A contribution can thus define how the icon should be rendered and behave.
 *
 * @since v2024.3.0
 */
export const navigationBarMenuIconExtensionPoint: ComponentExtensionPoint<NavigationBarMenuIconProps> = {
  identifier: 'navigationBarMenu#icon',
  FallbackComponent: Help,
};

/**
 * Extension point for the help URL in the navigation bar menu.
 *
 * This extension point allows the contribution of a custom help URL.
 * A contribution can thus define where the help link in the navigation bar menu should point to.
 *
 * @since v2024.9.0
 */
export const navigationBarMenuHelpURLExtensionPoint: DataExtensionPoint<string> = {
  identifier: 'navigationBarMenu#helpURL',
  fallback: 'https://eclipse.dev/sirius/sirius-web.html',
};

/**
 * Extension point for navigation bar menu entry contributions.
 *
 * This extension point allows the contribution of custom menu entries in the navigation bar menu.
 * Each contribution can define how a menu entry should be rendered and behave.
 *
 * @since v2024.9.0
 */
export const navigationBarMenuEntryExtensionPoint: ComponentExtensionPoint<NavigationBarMenuItemProps> = {
  identifier: 'navigationBarMenu#entry',
  FallbackComponent: () => null,
};

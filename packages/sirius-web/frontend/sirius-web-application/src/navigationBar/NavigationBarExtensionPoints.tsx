/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import {
  NavigationBarIconProps,
  NavigationBarLeftContributionProps,
  NavigationBarProps,
  NavigationBarRightContributionProps,
} from './NavigationBar.types';
import { NavigationBarIcon } from './NavigationBarIcon';

/**
 * Extension point for the navigation bar icon.
 *
 * This extension point allows the contribution of a custom icon for the navigation bar.
 * A contribution can thus define how the icon should be rendered and behave.
 *
 * @since v2024.3.0
 */
export const navigationBarIconExtensionPoint: ComponentExtensionPoint<NavigationBarIconProps> = {
  identifier: 'navigationBar#icon',
  FallbackComponent: NavigationBarIcon,
};

/**
 * Extension point for the left contribution in the navigation bar.
 *
 * This extension point allows the contribution of a custom component to be displayed on the left side of the navigation bar.
 * A contribution can thus define how the left side of the navigation bar should be structured and rendered.
 *
 * @since v2024.7.0
 */
export const navigationBarLeftContributionExtensionPoint: ComponentExtensionPoint<NavigationBarLeftContributionProps> =
  {
    identifier: 'navigationBar#leftContribution',
    FallbackComponent: () => null,
  };

/**
 * Extension point for the right contribution in the navigation bar.
 *
 * This extension point allows the contribution of a custom component to be displayed on the right side of the navigation bar.
 * A contribution can thus define how the right side of the navigation bar should be structured and rendered.
 *
 * @since v2024.7.0
 */
export const navigationBarRightContributionExtensionPoint: ComponentExtensionPoint<NavigationBarRightContributionProps> =
  {
    identifier: 'navigationBar#rightContribution',
    FallbackComponent: () => null,
  };

/**
 * Extension point for the center contribution in the navigation bar.
 *
 * This extension point allows the contribution of a custom component to be displayed in the center of the navigation bar.
 * A contribution can thus define how the center of the navigation bar should be structured and rendered.
 *
 * @since v2025.2.0
 */
export const navigationBarCenterContributionExtensionPoint: ComponentExtensionPoint<NavigationBarProps> = {
  identifier: 'navigationBar#centerContribution',
  FallbackComponent: ({ children }: NavigationBarProps) => {
    return <div>{children}</div>;
  },
};

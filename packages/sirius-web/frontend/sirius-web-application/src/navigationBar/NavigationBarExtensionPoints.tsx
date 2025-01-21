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

export const navigationBarIconExtensionPoint: ComponentExtensionPoint<NavigationBarIconProps> = {
  identifier: 'navigationBar#icon',
  FallbackComponent: NavigationBarIcon,
};

export const navigationBarLeftContributionExtensionPoint: ComponentExtensionPoint<NavigationBarLeftContributionProps> =
  {
    identifier: 'navigationBar#leftContribution',
    FallbackComponent: () => null,
  };

export const navigationBarRightContributionExtensionPoint: ComponentExtensionPoint<NavigationBarRightContributionProps> =
  {
    identifier: 'navigationBar#rightContribution',
    FallbackComponent: () => null,
  };

export const navigationBarCenterContributionExtensionPoint: ComponentExtensionPoint<NavigationBarProps> = {
  identifier: 'navigationBar#centerContribution',
  FallbackComponent: ({ children }: NavigationBarProps) => {
    return <div>{children}</div>;
  },
};

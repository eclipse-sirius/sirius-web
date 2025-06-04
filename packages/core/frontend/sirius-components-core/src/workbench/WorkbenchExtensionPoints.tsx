/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo and others.
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

import { ComponentExtensionPoint, DataExtensionPoint } from '../extension/ExtensionRegistry.types';
import { MainAreaComponentProps, RepresentationComponentFactory, WorkbenchViewContribution } from './Workbench.types';

/**
 * Extension point for the main area of the workbench.
 *
 * This extension point allows the contribution of a custom component as the main area of the workbench.
 *
 * @since v2024.3.0
 */
export const workbenchMainAreaExtensionPoint: ComponentExtensionPoint<MainAreaComponentProps> = {
  identifier: 'workbench#mainArea',
  FallbackComponent: () => null,
};

/**
 * Extension point for workbench view contributions.
 *
 * This extension point allows the addition of custom workbench views.
 * Each contribution can define how a view should be rendered or behave.
 *
 * @since v2024.3.0
 */
export const workbenchViewContributionExtensionPoint: DataExtensionPoint<Array<WorkbenchViewContribution>> = {
  identifier: 'workbench#viewContribution',
  fallback: [],
};

/**
 * Extension point for representation component factories.
 *
 * This extension point allows the addition of custom representation components to the workbench.
 * Each contribution can define how a representation should be rendered.
 *
 * @since v2024.7.0
 */
export const representationFactoryExtensionPoint: DataExtensionPoint<Array<RepresentationComponentFactory>> = {
  identifier: 'workbench#representationFactory',
  fallback: [],
};

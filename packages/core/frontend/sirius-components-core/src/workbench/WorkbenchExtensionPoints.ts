/*******************************************************************************
 * Copyright (c) 2024 Obeo and others.
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

export const workbenchMainAreaExtensionPoint: ComponentExtensionPoint<MainAreaComponentProps> = {
  identifier: 'workbench#mainArea',
  FallbackComponent: () => null,
};

export const workbenchViewContributionExtensionPoint: DataExtensionPoint<Array<WorkbenchViewContribution>> = {
  identifier: 'workbench#viewContribution',
  fallback: [],
};

export const representationFactoryExtensionPoint: DataExtensionPoint<Array<RepresentationComponentFactory>> = {
  identifier: 'workbench#representationFactory',
  fallback: [],
};

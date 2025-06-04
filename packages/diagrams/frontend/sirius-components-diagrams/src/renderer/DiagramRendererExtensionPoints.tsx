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

import { DataExtensionPoint } from '@eclipse-sirius/sirius-components-core';
import { ReactFlowPropsCustomizer } from './DiagramRenderer.types';

/**
 * Extension point for customizing React Flow properties in the diagram renderer.
 *
 * This extension point allows contributions to modify the properties passed to React Flow,
 * enabling customization of the diagram rendering behavior.
 *
 * Each contribution can define how React Flow should be configured, allowing for tailored
 * rendering experiences based on specific requirements.
 *
 * @since v2024.7.0
 */
export const diagramRendererReactFlowPropsCustomizerExtensionPoint: DataExtensionPoint<
  Array<ReactFlowPropsCustomizer>
> = {
  identifier: 'diagramRenderer#reactFlowPropsCustomizer',
  fallback: [],
};

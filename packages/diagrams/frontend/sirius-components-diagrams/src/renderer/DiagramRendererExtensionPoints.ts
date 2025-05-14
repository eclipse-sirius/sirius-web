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

export const diagramRendererReactFlowPropsCustomizerExtensionPoint: DataExtensionPoint<
  Array<ReactFlowPropsCustomizer>
> = {
  identifier: 'diagramRenderer#reactFlowPropsCustomizer',
  fallback: [],
};

export const diagramRendererDefaultSnapToGridExtensionPoint: DataExtensionPoint<(readOnly: boolean) => boolean> = {
  identifier: 'diagramRenderer#defaultSnapToGrid',
  fallback: () => false,
};

export const diagramRendererCanUserSnapToGridExtensionPoint: DataExtensionPoint<(readOnly: boolean) => boolean> = {
  identifier: 'diagramRenderer#canUserSnapToGrid',
  fallback: (readOnly) => !readOnly,
};

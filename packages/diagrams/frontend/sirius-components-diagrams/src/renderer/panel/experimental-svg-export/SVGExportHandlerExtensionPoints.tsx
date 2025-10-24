/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { IElementSVGExportHandler } from './SVGExportEngine.types';

/**
 * Extension point for SVG export handlers.
 *
 * This extension point allows the addition of custom SVG export handlers.
 * Each contribution can define how a specific element should be exported to SVG.
 *
 * @since v2025.4.0
 */
export const svgExportIElementSVGExportHandlerExtensionPoint: DataExtensionPoint<Array<IElementSVGExportHandler>> = {
  identifier: 'svgExport#IElementSVGExportHandler',
  fallback: [],
};

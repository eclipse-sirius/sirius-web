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
import { DiagramDialogContribution } from './DialogContextExtensionPoints.types';

/**
 * Extension point for diagram dialog contributions.
 *
 * This extension point allows the addition of custom dialogs to the diagram context.
 * Each contribution can define how a dialog required by a tool should be rendered and behave.
 *
 * @since v2024.9.0
 */
export const diagramDialogContributionExtensionPoint: DataExtensionPoint<Array<DiagramDialogContribution>> = {
  identifier: 'diagram#diagramDialogContribution',
  fallback: [],
};

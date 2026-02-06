/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { QueryResultButtonContribution } from './QueryViewExtensionPoints.types';

/**
 * Extension point for query view result button.
 *
 * This extension point allows the addition of custom actions in the query view result split button.
 * Each contribution can define how an action should be renderer or behave.
 *
 * @since v2026.3.0
 */
export const queryViewResultButtonExtensionPoint: DataExtensionPoint<Array<QueryResultButtonContribution>> = {
  identifier: 'queryView#resultButton',
  fallback: [],
};

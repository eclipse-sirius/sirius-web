/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { ComponentExtension, DataExtension } from './ExtensionRegistry.types';

export interface ExtensionRegistryMergeStrategy {
  mergeComponentExtensions(
    identifier: string,
    existingValues: ComponentExtension<any>[],
    newValues: ComponentExtension<any>[]
  ): ComponentExtension<any>[];
  mergeDataExtensions(
    identifier: string,
    existingValue: DataExtension<any>,
    newValue: DataExtension<any>
  ): DataExtension<any>;
}

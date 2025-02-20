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

import {
  ComponentExtension,
  DataExtension,
  ExtensionRegistryMergeStrategy,
} from '@eclipse-sirius/sirius-components-core';

export class DefaultExtensionRegistryMergeStrategy implements ExtensionRegistryMergeStrategy {
  public mergeComponentExtensions(
    _identifier: string,
    existingValues: ComponentExtension<any>[],
    newValues: ComponentExtension<any>[]
  ): ComponentExtension<any>[] {
    return [...existingValues, ...newValues];
  }

  public mergeDataExtensions(
    identifier: string,
    existingValue: DataExtension<any>,
    newValue: DataExtension<any>
  ): DataExtension<any> {
    if (identifier === 'omnibox#commandOverrideContribution') {
      return this.mergeOmniboxCommandOverrideContributions(existingValue, newValue);
    } else {
      console.debug(
        `The extension with identifier ${existingValue.identifier} has been overwritten by ${newValue.identifier}`
      );
      return newValue;
    }
  }

  private mergeOmniboxCommandOverrideContributions(
    existingValue: DataExtension<any>,
    newValue: DataExtension<any>
  ): DataExtension<any> {
    return {
      identifier: 'siriusweb_omnibox#commandOverrideContribution',
      data: [...existingValue.data, ...newValue.data],
    };
  }
}

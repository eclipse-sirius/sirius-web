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

import {
  ComponentExtension,
  ComponentExtensionPoint,
  DataExtension,
  DataExtensionPoint,
} from './ExtensionRegistry.types';
import { ExtensionRegistryMergeStrategy } from './ExtensionRegistryMergeStrategy';

export class ExtensionRegistry {
  private components: Record<string, ComponentExtension<any>[]> = {};
  private data: Record<string, DataExtension<any>> = {};

  public addComponent<P>(extensionPoint: ComponentExtensionPoint<P>, extension: ComponentExtension<P>): void {
    if (!this.components[extensionPoint.identifier]) {
      this.components[extensionPoint.identifier] = [];
    }

    const componentExtensions = this.components[extensionPoint.identifier];
    if (componentExtensions) {
      componentExtensions.push(extension);
    }
  }

  public findComponentById<P>(extensionPoint: ComponentExtensionPoint<P>): ComponentExtension<P> | null {
    const componentExtensions = this.components[extensionPoint.identifier];
    if (componentExtensions && componentExtensions[0]) {
      return componentExtensions[0];
    }
    return null;
  }

  public findAllComponents<P>(extensionPoint: ComponentExtensionPoint<P>): ComponentExtension<P>[] {
    return this.components[extensionPoint.identifier] || [];
  }

  public putData<P>(extensionPoint: DataExtensionPoint<P>, extension: DataExtension<P>): void {
    this.data[extensionPoint.identifier] = extension;
  }

  public getData<P>(extensionPoint: DataExtensionPoint<P>): DataExtension<P> | null {
    return this.data[extensionPoint.identifier] ?? null;
  }

  public addAll(
    extensionRegistry: ExtensionRegistry,
    extensionRegistryMergeStrategy: ExtensionRegistryMergeStrategy
  ): void {
    Object.entries(extensionRegistry.components).forEach((entry) => {
      const extensionPointIdentifier = entry[0];
      const existingValues = this.components[extensionPointIdentifier];
      const newValues = entry[1];
      if (existingValues) {
        this.components[extensionPointIdentifier] = extensionRegistryMergeStrategy.mergeComponentExtensions(
          extensionPointIdentifier,
          existingValues,
          newValues
        );
      } else {
        this.components[extensionPointIdentifier] = newValues;
      }
    });
    Object.entries(extensionRegistry.data).forEach((entry) => {
      const extensionPointIdentifier = entry[0];
      const existingValues = this.data[extensionPointIdentifier];
      const newValues = entry[1];
      if (existingValues) {
        this.data[extensionPointIdentifier] = extensionRegistryMergeStrategy.mergeDataExtensions(
          extensionPointIdentifier,
          existingValues,
          newValues
        );
      } else {
        this.data[extensionPointIdentifier] = newValues;
      }
    });
  }
}

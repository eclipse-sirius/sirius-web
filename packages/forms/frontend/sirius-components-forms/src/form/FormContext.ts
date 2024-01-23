/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import React from 'react';
import { PropertySectionComponentRegistry, PropertySectionContextValue } from './FormContext.types';
import { GQLWidget } from './FormEventFragments.types';

const propertySectionsRegistry: PropertySectionComponentRegistry = {
  getComponent: () => {
    return null;
  },
  getPreviewComponent: (_widget: GQLWidget) => {
    return null;
  },
  getWidgetContributions: () => [],
};

const value: PropertySectionContextValue = {
  propertySectionsRegistry,
};
export const PropertySectionContext = React.createContext<PropertySectionContextValue>(value);

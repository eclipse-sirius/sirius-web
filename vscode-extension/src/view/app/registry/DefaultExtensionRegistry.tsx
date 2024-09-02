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
import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import {
  GQLWidget,
  PropertySectionComponent,
  widgetContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-forms';
import {
  GQLReferenceWidget,
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import React from 'react';

const defaultExtensionRegistry = new ExtensionRegistry();
/*******************************************************************************
 *
 * Custom widget
 *
 * Used to register new custom widget in form
 *
 *******************************************************************************/

const isReferenceWidget = (widget: GQLWidget): widget is GQLReferenceWidget => widget.__typename === 'ReferenceWidget';

defaultExtensionRegistry.putData(widgetContributionExtensionPoint, {
  identifier: `siriusWeb_${widgetContributionExtensionPoint.identifier}_referenceWidget`,
  data: [
    {
      name: 'ReferenceWidget',
      icon: <ReferenceIcon />,
      previewComponent: ReferencePreview,
      component: (widget: GQLWidget): PropertySectionComponent<GQLWidget> | null => {
        let propertySectionComponent: PropertySectionComponent<GQLWidget> | null = null;

        if (isReferenceWidget(widget)) {
          propertySectionComponent = ReferencePropertySection;
        }
        return propertySectionComponent;
      },
    },
  ],
});

export { defaultExtensionRegistry };

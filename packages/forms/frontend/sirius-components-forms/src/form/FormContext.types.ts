/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { PropertySectionComponent, WidgetContribution } from './Form.types';
import { GQLWidget } from './FormEventFragments.types';

interface PreviewWidgetProps {
  widget: GQLWidget;
  selection: Selection;
  setSelection: (newSelection: Selection) => void;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: GQLWidget) => void;
}

type PreviewWidgetComponent = (props: PreviewWidgetProps) => JSX.Element | null;

export type PropertySectionComponentRegistry = {
  getComponent: (widget: GQLWidget) => PropertySectionComponent<GQLWidget> | null;
  getPreviewComponent: (widget: GQLWidget) => PreviewWidgetComponent | null;
  getWidgetContributions: () => WidgetContribution[];
};

export interface PropertySectionContextValue {
  propertySectionsRegistry: PropertySectionComponentRegistry;
}

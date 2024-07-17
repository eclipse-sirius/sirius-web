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

import { PropertySectionComponent } from './Form.types';
import { GQLWidget } from './FormEventFragments.types';

export interface PreviewWidgetProps {
  widget: GQLWidget;
  onDropBefore: (event: React.DragEvent<HTMLDivElement>, widget: GQLWidget) => void;
}

export interface WidgetContribution {
  name: string;
  previewComponent: (props: PreviewWidgetProps) => JSX.Element | null;
  icon: React.ReactElement;
  component: (widget: GQLWidget) => PropertySectionComponent<GQLWidget> | null;
}

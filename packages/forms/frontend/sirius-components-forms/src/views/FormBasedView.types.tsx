/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
  WorkbenchViewComponentProps,
  WorkbenchViewConfiguration,
  WorkbenchViewConfigurationSupplier,
} from '@eclipse-sirius/sirius-components-core';
import { GQLForm } from '../form/FormEventFragments.types';

export interface FormBasedViewProps extends WorkbenchViewComponentProps {
  editingContextId: string;
  form: GQLForm;
  readOnly: boolean;
  postProcessor?: (props: WorkbenchViewComponentProps, form: GQLForm) => JSX.Element;
}

export interface FormConverter {
  convert(editingContextId: string, form: GQLForm): GQLForm;
}

export interface FormBasedViewConfigurationSupplier extends WorkbenchViewConfigurationSupplier {
  getWorkbenchViewConfiguration: () => FormBasedViewConfiguration | null;
}

export interface FormBasedViewConfiguration extends WorkbenchViewConfiguration {
  selectedPageId: string;
}

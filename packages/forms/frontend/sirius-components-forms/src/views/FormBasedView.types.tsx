/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { GQLForm } from '../form/FormEventFragments.types';
import { FormConverter } from './FormConverter.types';

export interface FormBasedViewProps extends WorkbenchViewComponentProps {
  editingContextId: string;
  readOnly: boolean;
  subscriptionName: string;
  converter?: FormConverter;
  postProcessor?: (props: WorkbenchViewComponentProps, form: GQLForm) => JSX.Element;
}

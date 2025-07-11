/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, forwardRef, memo } from 'react';
import { Form } from '../form/Form';
import { GQLForm } from '../form/FormEventFragments.types';
import {
  FormBasedViewConfiguration,
  FormBasedViewConfigurationSupplier,
  FormBasedViewProps,
  FormConverter,
} from './FormBasedView.types';
import { formBasedViewFormConverterExtensionPoint } from './FormBasedViewExtensionPoints';

/**
 * Used to define workbench views based on a form.
 */
export const FormBasedView = memo(
  forwardRef<FormBasedViewConfigurationSupplier | null, FormBasedViewProps>(
    (
      { editingContextId, form, readOnly, postProcessor, initialConfiguration }: FormBasedViewProps,
      workbenchViewConfigurationSupplierRef: ForwardedRef<FormBasedViewConfigurationSupplier | null>
    ) => {
      const initialFormBasedViewConfiguration: FormBasedViewConfiguration | null =
        initialConfiguration as FormBasedViewConfiguration;
      const { data: formConverters }: DataExtension<FormConverter[]> = useData(
        formBasedViewFormConverterExtensionPoint
      );

      let convertedForm: GQLForm = form;
      formConverters.forEach((formConverter) => {
        convertedForm = formConverter.convert(editingContextId, convertedForm);
      });

      if (postProcessor) {
        return postProcessor({ editingContextId, readOnly, initialConfiguration: null }, convertedForm);
      }
      return (
        <Form
          editingContextId={editingContextId}
          form={convertedForm}
          readOnly={readOnly}
          initiallySelectedPageId={initialFormBasedViewConfiguration?.selectedPageId}
          ref={workbenchViewConfigurationSupplierRef}
        />
      );
    }
  )
);

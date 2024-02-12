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

import React from 'react';
import {
  FormDescriptionEditorContextProviderProps,
  FormDescriptionEditorContextValue,
} from './useFormDescriptionEditor.types';

const defaultValue: FormDescriptionEditorContextValue = {
  editingContextId: '',
  representationId: '',
  readOnly: false,
  formDescriptionEditor: { id: '', pages: [] },
};

export const FormDescriptionEditorContext = React.createContext<FormDescriptionEditorContextValue>(defaultValue);

export const FormDescriptionEditorContextProvider = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  readOnly,
  children,
}: FormDescriptionEditorContextProviderProps) => {
  const value: FormDescriptionEditorContextValue = {
    editingContextId,
    representationId,
    formDescriptionEditor,
    readOnly,
  };
  return <FormDescriptionEditorContext.Provider value={value}>{children}</FormDescriptionEditorContext.Provider>;
};

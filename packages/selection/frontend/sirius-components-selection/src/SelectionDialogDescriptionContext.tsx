/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { SelectionContextProvider } from '@eclipse-sirius/sirius-components-core';
import React, { useState } from 'react';
import {
  SelectionDialogContextProviderProps,
  SelectionDialogContextProviderState,
  SelectionDialogContextValue,
} from './SelectionDialogDescriptionContext.types';

const defaultValue: SelectionDialogContextValue = {
  dialog: {
    titles: {
      defaultTitle: '',
      noSelectionTitle: '',
      withSelectionTitle: '',
    },
    description: '',
    noSelectionAction: {
      label: '',
      description: '',
    },
    withSelectionAction: {
      label: '',
      description: '',
    },
    statusMessages: {
      noSelectionActionStatusMessage: '',
      selectionRequiredWithoutSelectionStatusMessage: '',
    },
    confirmButtonLabels: {
      noSelectionConfirmButtonLabel: '',
      selectionRequiredWithoutSelectionConfirmButtonLabel: '',
      selectionRequiredWithSelectionConfirmButtonLabel: '',
    },
  },
  selectionOptionSelected: false,
  noSelectionOptionSelected: false,
  treeDescriptionId: null,
  multiple: false,
  optional: false,
  updateSelectionOptions: () => {},
};

export const SelectionDialogDescriptionContext = React.createContext<SelectionDialogContextValue>(defaultValue);

export const SelectionDialogDescriptionContextProvider = ({
  children,
  selectionDescription,
}: SelectionDialogContextProviderProps) => {
  const [state, setState] = useState<SelectionDialogContextProviderState>({
    noSelectionOptionSelected: false,
    selectionOptionSelected: false,
  });

  const updateSelectionOptions = (noSelectionOptionSelected: boolean, selectionOptionSelected: boolean) => {
    setState((prevState) => ({
      ...prevState,
      noSelectionOptionSelected,
      selectionOptionSelected,
    }));
  };

  return (
    <SelectionContextProvider initialSelection={{ entries: [] }}>
      <SelectionDialogDescriptionContext.Provider
        value={{
          dialog: selectionDescription.dialog,
          ...state,
          optional: selectionDescription.optional,
          multiple: selectionDescription.multiple,
          treeDescriptionId: selectionDescription.treeDescription.id,
          updateSelectionOptions,
        }}>
        {children}
      </SelectionDialogDescriptionContext.Provider>
    </SelectionContextProvider>
  );
};

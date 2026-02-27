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
  selectionDialogDescription: {
    titles: {
      defaultTitle: 'Element Selection',
      noSelectionTitle: 'Element Selection',
      selectionTitle: 'Element Selection',
    },
    description: '',
    noSelectionAction: {
      label: 'Proceed without selection',
      description: 'Proceed without selecting an existing element',
    },
    selectionAction: {
      label: 'Use an existing element',
      description: 'Select one or more elements',
    },
    statusMessages: {
      noSelectionStatusMessage: 'The tool execution will continue without any element selected',
      selectionStatusMessageWithoutSelection: 'Select at least one element to continue the tool execution',
    },
    confirmButtonLabels: {
      noSelectionConfirmButtonLabel: 'Confirm',
      selectionConfirmButtonLabelWithoutSelection: 'Select an element',
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
          selectionDialogDescription: {
            ...defaultValue.selectionDialogDescription,
            description: selectionDescription.message,
            noSelectionAction: {
              label: selectionDescription.noSelectionLabel,
              description: defaultValue.selectionDialogDescription.noSelectionAction.description,
            },
          },
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

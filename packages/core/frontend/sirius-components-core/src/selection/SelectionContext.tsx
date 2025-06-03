/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import React, { useCallback, useState } from 'react';
import {
  Selection,
  SelectionContextProviderProps,
  SelectionContextProviderState,
  SelectionContextValue,
  SelectionEntry,
} from './SelectionContext.types';

const defaultValue: SelectionContextValue = {
  selection: { entries: [] },
  setSelection: () => {},
  toggleSelected: () => {},
};

export const SelectionContext = React.createContext<SelectionContextValue>(defaultValue);

export const SelectionContextProvider = ({ initialSelection, children }: SelectionContextProviderProps) => {
  const [state, setState] = useState<SelectionContextProviderState>({
    selection: initialSelection ?? { entries: [] },
  });

  const setSelection = useCallback((selection: Selection) => {
    setState((prevState) => ({ ...prevState, selection }));
  }, []);

  const toggleSelected = useCallback((entryToToggle: SelectionEntry) => {
    setState((prevState) => {
      const alreadySelected = prevState.selection.entries.some((entry) => entry.id === entryToToggle.id);
      if (!alreadySelected) {
        const newSelection: Selection = { entries: [...prevState.selection.entries, entryToToggle] };
        return { ...prevState, selection: newSelection };
      } else {
        const newSelection: Selection = {
          entries: prevState.selection.entries.filter((entry) => entry.id !== entryToToggle.id),
        };
        return { ...prevState, newSelection };
      }
    });
  }, []);

  return (
    <SelectionContext.Provider
      value={{
        selection: state.selection,
        setSelection: setSelection,
        toggleSelected,
      }}>
      {children}
    </SelectionContext.Provider>
  );
};

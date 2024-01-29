/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
  addToSelection: () => {},
  removeFromSelection: () => {},
};

export const SelectionContext = React.createContext<SelectionContextValue>(defaultValue);

export const SelectionContextProvider = ({ initialSelection, children }: SelectionContextProviderProps) => {
  const [state, setState] = useState<SelectionContextProviderState>({
    selection: initialSelection ?? { entries: [] },
  });

  const setSelection = useCallback((selection: Selection) => {
    debugger;
    setState((prevState) => ({ ...prevState, selection }));
  }, []);

  const addToSelection = useCallback((selectionEntry: SelectionEntry) => {
    setState((prevState) => ({
      ...prevState,
      selection: { entries: [...prevState.selection.entries, selectionEntry] },
    }));
  }, []);

  const removeFromSelection = useCallback((selectionEntry: SelectionEntry) => {
    setState((prevState) => ({
      selection: { entries: prevState.selection.entries.filter((entry) => entry.id !== selectionEntry.id) },
    }));
  }, []);

  return (
    <SelectionContext.Provider
      value={{ selection: state.selection, setSelection, addToSelection, removeFromSelection }}>
      {children}
    </SelectionContext.Provider>
  );
};

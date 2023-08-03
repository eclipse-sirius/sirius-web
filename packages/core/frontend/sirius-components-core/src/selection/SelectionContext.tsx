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

import React, { useCallback, useState } from 'react';
import { Representation } from '../workbench/Workbench.types';
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
};

export const SelectionContext = React.createContext<SelectionContextValue>(defaultValue);

const isRepresentation = (selectionEntry: SelectionEntry): selectionEntry is Representation =>
  selectionEntry.kind.startsWith('siriusComponents://representation');

export const SelectionContextProvider = ({ initialSelection, children }: SelectionContextProviderProps) => {
  const [state, setState] = useState<SelectionContextProviderState>({
    selection: initialSelection ?? { entries: [] },
  });

  const setSelection = useCallback((selection: Selection) => {
    const selectedRepresentation = selection.entries.filter(isRepresentation);
    setState((prevState) => ({ ...prevState, selection, selectedRepresentation }));
  }, []);

  return (
    <SelectionContext.Provider value={{ selection: state.selection, setSelection }}>
      {children}
    </SelectionContext.Provider>
  );
};

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
  DiagramDirectEditContextProviderProps,
  DiagramDirectEditContextProviderState,
  DiagramDirectEditContextValue,
  DirectEditTrigger,
} from './DiagramDirectEditContext.types';

const defaultValue: DiagramDirectEditContextValue = {
  currentlyEditedLabelId: null,
  directEditTrigger: null,
  editingInput: null,
  setCurrentlyEditedLabelId: () => {},
  resetDirectEdit: () => {},
};

export const DiagramDirectEditContext = React.createContext<DiagramDirectEditContextValue>(defaultValue);

export const DiagramDirectEditContextProvider = ({ children }: DiagramDirectEditContextProviderProps) => {
  const [state, setState] = useState<DiagramDirectEditContextProviderState>({
    currentlyEditedLabelId: null,
    directEditTrigger: null,
    editingInput: null,
  });

  const setCurrentlyEditedLabelId = useCallback(
    (directEditTrigger: DirectEditTrigger, currentlyEditedLabelId: string, editingInput: string | null) => {
      setState((prevState) => ({ ...prevState, currentlyEditedLabelId, directEditTrigger, editingInput }));
    },
    []
  );

  const resetDirectEdit = useCallback(
    () =>
      setState((prevState) => ({
        ...prevState,
        currentlyEditedLabelId: null,
        directEditTrigger: null,
        editingInput: null,
      })),
    []
  );

  return (
    <DiagramDirectEditContext.Provider
      value={{
        currentlyEditedLabelId: state.currentlyEditedLabelId,
        directEditTrigger: state.directEditTrigger,
        editingInput: state.editingInput,
        setCurrentlyEditedLabelId,
        resetDirectEdit,
      }}>
      {children}
    </DiagramDirectEditContext.Provider>
  );
};

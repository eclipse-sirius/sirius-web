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
import {
  DiagramDirectEditContextProviderProps,
  DiagramDirectEditContextProviderState,
  DiagramDirectEditContextValue,
  DirectEditTrigger,
} from './DiagramDirectEditContext.types';

const defaultValue: DiagramDirectEditContextValue = {
  currentlyEditedLabelId: null,
  directEditTrigger: null,
  editingKey: null,
  setCurrentlyEditedLabelId: () => {},
  resetDirectEdit: () => {},
};

export const DiagramDirectEditContext = React.createContext<DiagramDirectEditContextValue>(defaultValue);

export const DiagramDirectEditContextProvider = ({ children }: DiagramDirectEditContextProviderProps) => {
  const [state, setState] = useState<DiagramDirectEditContextProviderState>({
    currentlyEditedLabelId: null,
    directEditTrigger: null,
    editingKey: null,
  });

  const setCurrentlyEditedLabelId = useCallback(
    (directEditTrigger: DirectEditTrigger, currentlyEditedLabelId: string, editingKey: string | null) => {
      setState((prevState) => ({ ...prevState, currentlyEditedLabelId, directEditTrigger, editingKey }));
    },
    []
  );

  const resetDirectEdit = useCallback(
    () =>
      setState((prevState) => ({
        ...prevState,
        currentlyEditedLabelId: null,
        directEditTrigger: null,
        editingKey: null,
      })),
    []
  );

  return (
    <DiagramDirectEditContext.Provider
      value={{
        currentlyEditedLabelId: state.currentlyEditedLabelId,
        directEditTrigger: state.directEditTrigger,
        editingKey: state.editingKey,
        setCurrentlyEditedLabelId,
        resetDirectEdit,
      }}>
      {children}
    </DiagramDirectEditContext.Provider>
  );
};

/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import React, { useState } from 'react';
import {
  ImpactAnalysisDialogContextValue,
  ImpactAnalysisDialogContextProviderState,
} from './ImpactAnalysisDialogContext.types';
import { ImpactAnalysisDialog } from './ImpactAnalysisDialog';
import { GQLToolVariable } from '../../graphql/GQLTypes.types';

const defaultValue: ImpactAnalysisDialogContextValue = {
  showImpactAnalysisDialog: () => {},
};

export const ImpactAnalysisDialogContext = React.createContext<ImpactAnalysisDialogContextValue>(defaultValue);

export const ImpactAnalysisDialogContextProvider = ({ children }) => {
  const [state, setState] = useState<ImpactAnalysisDialogContextProviderState>({
    open: false,
    onConfirm: () => {},
    editingContextId: null,
    representationId: null,
    toolId: null,
    targetObjectId: null,
    variables: [],
  });

  const showImpactAnalysisDialog = (
    editingContextId: string,
    representationId: string,
    toolId: string,
    targetObjectId: string,
    variables: GQLToolVariable[],
    onConfirm: () => void
  ) => {
    setState({ open: true, onConfirm, editingContextId, representationId, toolId, targetObjectId, variables });
  };

  const handleConfirm = () => {
    state.onConfirm();
    handleClose();
  };

  const handleClose = () => {
    setState((prevState) => ({ ...prevState, open: false }));
  };

  return (
    <ImpactAnalysisDialogContext.Provider value={{ showImpactAnalysisDialog }}>
      {children}
      {state.open && state.editingContextId && state.representationId && state.toolId && (
        <ImpactAnalysisDialog
          open={state.open}
          editingContextId={state.editingContextId}
          representationId={state.representationId}
          toolId={state.toolId}
          targetObjectId={state.targetObjectId!}
          variables={state.variables}
          onCancel={handleClose}
          onConfirm={handleConfirm}
        />
      )}
    </ImpactAnalysisDialogContext.Provider>
  );
};

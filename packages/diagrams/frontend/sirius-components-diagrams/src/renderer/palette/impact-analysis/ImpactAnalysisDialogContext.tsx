/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { ImpactAnalysisDialog } from '@eclipse-sirius/sirius-components-core';
import React, { useState } from 'react';
import { GQLToolVariable } from '../Palette.types';
import {
  ImpactAnalysisDialogContextValue,
  ImpactAnalysisDialogContextProviderState,
} from './ImpactAnalysisDialogContext.types';
import { useInvokeImpactAnalysis } from './useImpactAnalysis';

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
    toolLabel: null,
    diagramElementId: null,
    variables: [],
  });

  const showImpactAnalysisDialog = (
    editingContextId: string,
    representationId: string,
    toolId: string,
    toolLabel: string,
    diagramElementId: string,
    variables: GQLToolVariable[],
    onConfirm: () => void
  ) => {
    setState({
      open: true,
      onConfirm,
      editingContextId,
      representationId,
      toolId,
      toolLabel,
      diagramElementId,
      variables,
    });
  };

  const { impactAnalysisReport, loading } = useInvokeImpactAnalysis(
    state.editingContextId,
    state.representationId,
    state.toolId,
    state.diagramElementId,
    state.variables
  );

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
      {state.open && (
        <ImpactAnalysisDialog
          open={state.open}
          label={state.toolLabel ?? ''}
          impactAnalysisReport={impactAnalysisReport}
          loading={loading}
          onCancel={handleClose}
          onConfirm={handleConfirm}
        />
      )}
    </ImpactAnalysisDialogContext.Provider>
  );
};

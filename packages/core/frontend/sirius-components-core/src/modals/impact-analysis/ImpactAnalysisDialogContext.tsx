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

import { ImpactAnalysisDialog } from './ImpactAnalysisDialog';
import { GQLImpactAnalysisReport } from './ImpactAnalysisDialog.types';
import React, { useState } from 'react';
import {
  ImpactAnalysisDialogContextValue,
  ImpactAnalysisDialogContextProviderState,
} from './ImpactAnalysisDialogContext.types';

const defaultValue: ImpactAnalysisDialogContextValue = {
  showImpactAnalysisDialog: () => {},
};

export const ImpactAnalysisDialogContext = React.createContext<ImpactAnalysisDialogContextValue>(defaultValue);

export const ImpactAnalysisDialogContextProvider = ({ children }) => {
  const [state, setState] = useState<ImpactAnalysisDialogContextProviderState>({
    open: false,
    impactAnalysisReport: null,
    loading: false,
    toolLabel: null,
    onConfirm: () => {},
  });

  const showImpactAnalysisDialog = (
    impactAnalysisReport: GQLImpactAnalysisReport | null,
    loading: boolean,
    toolLabel: string,
    onConfirm: () => void
  ) => {
    setState({
      open: true,
      impactAnalysisReport,
      loading,
      toolLabel,
      onConfirm,
    });
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
      {state.open && (
        <ImpactAnalysisDialog
          open={state.open}
          label={state.toolLabel ?? ''}
          impactAnalysisReport={state.impactAnalysisReport}
          loading={state.loading}
          onCancel={handleClose}
          onConfirm={handleConfirm}
        />
      )}
    </ImpactAnalysisDialogContext.Provider>
  );
};

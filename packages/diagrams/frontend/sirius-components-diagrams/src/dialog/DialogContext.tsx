/*******************************************************************************
 * Copyright (c) 2024 Obeo and others.
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

import { useData } from '@eclipse-sirius/sirius-components-core';
import React, { useContext, useState } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import { DialogContextProviderState, DialogContextValue, ToolVariable } from './DialogContext.types';
import { diagramDialogContributionExtensionPoint } from './DialogContextExtensionPoints';
import {
  DiagramDialogComponentProps,
  DiagramDialogContribution,
  DiagramDialogVariable,
} from './DialogContextExtensionPoints.types';

const defaultValue: DialogContextValue = {
  showDialog: () => {},
  isOpened: false,
};

export const DialogContext = React.createContext<DialogContextValue>(defaultValue);

export const DialogContextProvider = ({ children }) => {
  const [state, setState] = useState<DialogContextProviderState>({
    dialogDescriptionId: null,
    variables: [],
    onConfirm: () => {},
    onClose: () => {},
    open: false,
  });

  const { data: dialogContributions } = useData<DiagramDialogContribution[]>(diagramDialogContributionExtensionPoint);

  const showDialog = (
    dialogDescriptionId: string,
    variables: DiagramDialogVariable[],
    onConfirm: (variables: ToolVariable[]) => void,
    onClose: () => void
  ) => {
    if (!state.open) {
      setState({ open: true, dialogDescriptionId, variables, onConfirm, onClose });
    }
  };

  const onFinish = (toolVariables: ToolVariable[]) => {
    state.onConfirm(toolVariables);
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  const internalClose = () => {
    state.onClose();
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  const { editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  let DialogComponent: React.ComponentType<DiagramDialogComponentProps> | null = null;
  const dialogComponentProps: DiagramDialogComponentProps = {
    editingContextId,
    dialogDescriptionId: state.dialogDescriptionId ?? '',
    variables: state.variables ?? '',
    onFinish,
    onClose: internalClose,
  };
  if (state.open && state.dialogDescriptionId) {
    const dialogDescriptionId: string = state.dialogDescriptionId;
    const dialogContribution: DiagramDialogContribution | undefined = dialogContributions.find((dialogContribution) =>
      dialogContribution.canHandle(dialogDescriptionId)
    );
    if (dialogContribution) {
      DialogComponent = dialogContribution.component;
    }
  }
  return (
    <DialogContext.Provider value={{ isOpened: state.open, showDialog }}>
      {children}
      {state.open && DialogComponent && <DialogComponent {...dialogComponentProps} />}
    </DialogContext.Provider>
  );
};

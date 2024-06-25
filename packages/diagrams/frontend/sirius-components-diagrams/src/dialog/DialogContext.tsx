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
import React, { useState } from 'react';
import { GQLToolVariable } from '../renderer/palette/Palette.types';
import { DialogContextProviderState, DialogContextValue } from './DialogContext.types';
import { diagramDialogContributionExtensionPoint } from './diagramDialogExtensionPoint';
import { DiagramDialogContribution, DialogComponentProps } from './diagramDialogExtensionPoint.types';

const defaultValue: DialogContextValue = {
  showDialog: () => {},
};

export const DialogContext = React.createContext<DialogContextValue>(defaultValue);

export const DialogContextProvider = ({ children }) => {
  const [state, setState] = useState<DialogContextProviderState>({
    dialogDescriptionId: undefined,
    dialogTypeId: undefined,
    editingContextId: undefined,
    targetObjectId: undefined,
    onConfirm: () => {},
    open: false,
  });

  const { data: dialogContributions } = useData<DiagramDialogContribution[]>(diagramDialogContributionExtensionPoint);
  const showDialog = (
    dialogTypeId: string,
    editingContextId: string,
    dialogDescriptionId,
    targetObjectId,
    onConfirm: (variables: GQLToolVariable[]) => void
  ) => {
    setState({ open: true, dialogTypeId, editingContextId, dialogDescriptionId, targetObjectId, onConfirm });
  };

  const onFinish = (toolVariables: GQLToolVariable[]) => {
    state.onConfirm(toolVariables);
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  const onClose = () => {
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  let DialogComponent: React.ComponentType<DialogComponentProps> | undefined;
  const dialogComponentProps: DialogComponentProps = {
    dialogDescriptionId: state.dialogDescriptionId ?? '',
    editingContextId: state.editingContextId ?? '',
    targetObjectId: state.targetObjectId ?? '',
    onFinish,
    onClose,
  };
  if (state.open && state.dialogTypeId) {
    const dialogContribution: DiagramDialogContribution | undefined = dialogContributions.find((dialogContribution) =>
      dialogContribution.canHandle(state.dialogTypeId as string)
    );
    if (dialogContribution) {
      DialogComponent = dialogContribution.component;
    }
  }
  return (
    <DialogContext.Provider value={{ showDialog }}>
      {children}
      {state.open && DialogComponent && <DialogComponent {...dialogComponentProps} />}
    </DialogContext.Provider>
  );
};

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
import { GQLToolVariable } from '../renderer/palette/usePalette.types';
import { DialogContextProviderState, DialogContextValue } from './DialogContext.types';
import { diagramDialogContributionExtensionPoint } from './DialogContextExtensionPoints';
import { DiagramDialogComponentProps, DiagramDialogContribution } from './DialogContextExtensionPoints.types';

const defaultValue: DialogContextValue = {
  showDialog: () => {},
};

export const DialogContext = React.createContext<DialogContextValue>(defaultValue);

export const DialogContextProvider = ({ children }) => {
  const [state, setState] = useState<DialogContextProviderState>({
    dialogDescriptionId: null,
    targetObjectId: null,
    onConfirm: () => {},
    open: false,
  });

  const { data: dialogContributions } = useData<DiagramDialogContribution[]>(diagramDialogContributionExtensionPoint);

  const showDialog = (
    dialogDescriptionId: string,
    targetObjectId: string,
    onConfirm: (variables: GQLToolVariable[]) => void
  ) => {
    setState({ open: true, dialogDescriptionId, targetObjectId, onConfirm });
  };

  const onFinish = (toolVariables: GQLToolVariable[]) => {
    state.onConfirm(toolVariables);
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  const onClose = () => {
    setState((prevState) => ({ ...prevState, open: false, dialogTypeId: undefined }));
  };

  const { editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  let DialogComponent: React.ComponentType<DiagramDialogComponentProps> | null = null;
  const dialogComponentProps: DiagramDialogComponentProps = {
    editingContextId,
    dialogDescriptionId: state.dialogDescriptionId ?? '',
    targetObjectId: state.targetObjectId ?? '',
    onFinish,
    onClose,
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
    <DialogContext.Provider value={{ showDialog }}>
      {children}
      {state.open && DialogComponent && <DialogComponent {...dialogComponentProps} />}
    </DialogContext.Provider>
  );
};

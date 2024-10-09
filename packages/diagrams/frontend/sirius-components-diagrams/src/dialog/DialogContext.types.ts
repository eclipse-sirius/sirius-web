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

import { DiagramDialogVariable } from './DialogContextExtensionPoints.types';

export interface DialogContextValue {
  showDialog: (
    dialogDescriptionId: string,
    variables: DiagramDialogVariable[],
    onConfirm: (variables: ToolVariable[]) => void,
    onClose: () => void
  ) => void;
  isOpened: boolean;
}

export interface DialogContextProviderState {
  open: boolean;
  dialogDescriptionId: string | null;
  variables: DiagramDialogVariable[];
  onConfirm: (variables: ToolVariable[]) => void;
  onClose: () => void;
}

export interface ToolVariable {
  name: string;
  value: string;
  type: ToolVariableType;
}

export type ToolVariableType = 'STRING' | 'OBJECT_ID' | 'OBJECT_ID_ARRAY';

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

import { GQLToolVariable } from '../renderer/palette/Palette.types';

export interface DialogContextValue {
  showDialog: (
    dialogTypeId: string,
    editingContextId: string,
    dialogDescriptionId: string,
    targetObjectId: string,
    onConfirm: (variables: GQLToolVariable[]) => void
  ) => void;
}

export interface DialogContextProviderState {
  open: boolean;
  dialogTypeId: string | undefined;
  editingContextId: string | undefined;
  dialogDescriptionId: string | undefined;
  targetObjectId: string | undefined;
  onConfirm: (variables: GQLToolVariable[]) => void;
}

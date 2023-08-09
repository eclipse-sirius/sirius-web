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

export interface DiagramDirectEditContextValue {
  currentlyEditedLabelId: string | null;
  editingKey: string | null;
  directEditTrigger: DirectEditTrigger | null;
  setCurrentlyEditedLabelId: (
    directEditTrigger: DirectEditTrigger,
    currentlyEditedLabelId: string,
    editingKey: string | null
  ) => void;
  resetDirectEdit: () => void;
}

export type DirectEditTrigger = 'keyDown' | 'F2' | 'palette' | 'doubleClick';

export interface DiagramDirectEditContextProviderProps {
  children?: React.ReactNode;
}

export interface DiagramDirectEditContextProviderState {
  currentlyEditedLabelId: string | null;
  editingKey: string | null;
  directEditTrigger: DirectEditTrigger | null;
}

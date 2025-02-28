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

export interface ImportLibraryDialogProps {
  open: boolean;
  title: string;
  onClose: () => void;
}

export interface ImportLibraryDialogState {
  selectedLibraries: string[];
  actions: ImportLibraryAction[];
}

export interface ImportLibraryAction {
  id: string;
  label: string;
}

export interface ImportStudioSplitButtonProps {
  actions: ImportLibraryAction[];
  onClick: (action: ImportLibraryAction) => void;
}

export interface ImportStudioSplitButtonState {
  actions: ImportLibraryAction[];
  selected: boolean;
  open: boolean;
  selectedIndex: number;
  message: string;
}

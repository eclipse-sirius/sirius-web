/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
export interface EditProjectNavbarState {
  modalDisplayed: ModalName;
  projectName: string;
  projectMenuAnchor: HTMLElement | null;
}

export type ModalName = 'RenameProject' | 'DeleteProject' | null;

export interface EditProjectNavbarProps {
  readOnly: boolean;
}

export interface EditProjectNavbarSubtitleProps {}

export interface EditProjectNavbarMenuContainerProps {
  children?: React.ReactNode;
}

export interface EditProjectNavbarMenuEntryProps {
  projectId: string;
  onCloseContextMenu: () => void;
}

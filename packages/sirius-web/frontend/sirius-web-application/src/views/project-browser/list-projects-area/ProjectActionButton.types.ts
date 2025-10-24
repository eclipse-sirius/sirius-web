/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { GQLProject } from './useProjects.types';

export interface ProjectActionButtonProps {
  project: GQLProject;
  onChange: () => void;
}

export interface ProjectActionButtonState {
  contextMenuAnchorElement: HTMLElement | null;
}

export interface ProjectContextMenuContainerProps {
  children?: React.ReactNode;
}

export interface ProjectContextMenuProps {
  menuAnchor: HTMLElement;
  project: GQLProject;
  onChange: () => void;
  onClose: () => void;
}

export interface ProjectContextMenuEntryProps {
  project: GQLProject;
  onChange: () => void;
  onClose: () => void;
}

export type ProjectContextMenuModal = 'RENAME_PROJECT_DIALOG' | 'DELETE_PROJECT_DIALOG';

export interface ProjectContextMenuModalProps {
  project: GQLProject;
  onSuccess: () => void;
  onCancel: () => void;
}
